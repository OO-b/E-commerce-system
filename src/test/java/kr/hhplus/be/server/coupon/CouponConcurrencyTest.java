package kr.hhplus.be.server.coupon;

import kr.hhplus.be.server.domain.coupon.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
        Coupon coupon = new Coupon(1, "테스트 쿠폰", 20, 10, 10, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        couponRepository.save(coupon);
        couponId = coupon.getCouponId();
    }

    @Test
    @DisplayName("선착순쿠폰 여러명 동시 발급 시도 - 동시성 테스트")
    void givenAvailableCoupon_whenIssuingCouponAtTheSameTime_thenFailure() throws InterruptedException {

        int threadCount = 10; // 10명이 동시에 발급 시도
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int userId = i + 1;
            executorService.submit(() -> {
                try {
                    CouponCommand.Issue command = new CouponCommand.Issue(userId, couponId);
                    couponService.issueCoupon(command);
                } catch (Exception e) {
                    System.out.println("실패: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown(); // 스레드 풀 종료

        // 실제로 발급된 쿠폰 수가 최대 재고 수보다 크지 않음을 확인
//        long issuedCount = userCouponRepository.countByCouponId(couponId);
//        assertThat(issuedCount).isLessThanOrEqualTo(5);
    }
}