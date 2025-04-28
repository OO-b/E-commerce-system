package kr.hhplus.be.server.coupon;

import kr.hhplus.be.server.domain.coupon.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class CouponConcurrencyTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    private int couponId;

    @BeforeEach
    void setUp() {
        // 테스트용 쿠폰 생성
        Coupon coupon = new Coupon("테스트 쿠폰", 20, 5, 5, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        Coupon saveCoupon = couponRepository.save(coupon);
        couponId = saveCoupon.getCouponId();
    }

    @Test
    @DisplayName("선착순 쿠폰 여러명 동시 발급 시도 - 동시성 테스트")
    void givenAvailableCoupon_whenIssuingCouponAtTheSameTime_thenFailure() throws InterruptedException {

        int threadCount = 5; // 10명이 동시에 발급 시도
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int userId = i + 1;
            executorService.submit(() -> {
                try {
                    CouponCommand.Issue command = new CouponCommand.Issue(userId, couponId);
                    CouponInfo.issuedCoupon userCoupon = couponService.issueCoupon(command);
                } catch (Exception e) {
                     e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown(); // 스레드 풀 종료

        Optional<Coupon> issuedCoupon = couponRepository.findById(couponId);
        if (issuedCoupon.isPresent()) {
            int remainingCnt = issuedCoupon.get().getRemainingCount();
            assertThat(remainingCnt).isEqualTo(0);
        }

    }
}