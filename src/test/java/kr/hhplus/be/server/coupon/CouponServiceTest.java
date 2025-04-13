package kr.hhplus.be.server.coupon;

import kr.hhplus.be.server.domain.coupon.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UserCouponRepository userCouponRepository;

    @InjectMocks
    private CouponService couponService;

    @Test
    @DisplayName("[성공] 유효한 쿠폰한 쿠폰 제공후, 해당 쿠폰 Validate 체크시 쿠폰정보를 반환하는 성공케이스")
    void givenValidCoupon_whenUseCoupon_thenSuccess() {
        // given
        int userId = 1;
        int couponId = 100;
        CouponCommand.Usage command = new CouponCommand.Usage(userId, couponId);

        Coupon coupon = new Coupon(couponId, "10% 할인", 10, 100, 100, LocalDateTime.now(), LocalDateTime.now().plusDays(1)); // 직접 생성
        UserCoupon userCoupon = new UserCoupon(couponId, userId, CouponStatus.AVAILABLE, LocalDateTime.now()); // 직접 생성

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.findByUserIdAndCouponId(userId, couponId)).thenReturn(Optional.of(userCoupon));

        // when
        Coupon result = couponService.validateAndGetDiscount(command);

        // then
        assertNotNull(result);
        assertEquals(coupon, result);
    }

    @Test
    @DisplayName("[실패] 사용자가 이미 사용한 쿠폰인 경우 coupon은 null값 반환")
    void givenAlreadyUseUserCoupon_whenUseCoupon_thenFailure() {
        // given
        int couponId = 100;
        int userId = 1;
        CouponCommand.Usage command = new CouponCommand.Usage(userId, couponId);

        Coupon coupon = new Coupon(couponId, "10% 할인", 10, 100, 100, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1)); // 직접 생성
        UserCoupon userCoupon = new UserCoupon(couponId, userId, CouponStatus.USED, LocalDateTime.now().minusDays(1));

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.findByUserIdAndCouponId(userId, couponId)).thenReturn(Optional.of(userCoupon));

        // when
        Coupon result = couponService.validateAndGetDiscount(command);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("[성공] 정상적으로 쿠폰을 발급 받는 케이스")
    void givenExistingCoupon_whenGetCoupon_thenSuccess() {
        // given
        int couponId = 1;
        int userId = 123;
        CouponCommand.Issue command = new CouponCommand.Issue(userId, couponId);

        Coupon coupon = new Coupon(couponId, "10% 할인", 10, 100, 10,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.findByUserIdAndCouponId(userId, couponId)).thenReturn(Optional.empty());

        // when
        couponService.issueCoupon(command);

        // then
        verify(couponRepository).save(any(Coupon.class));
        verify(userCouponRepository).save(any(UserCoupon.class));
    }

    @Test
    @DisplayName("[실패] 쿠폰이 만료되었으면 IllegalStateException 예외 발생하는 케이스")
    void whenExpired_thenFailure() {
        // given
        CouponCommand.Issue command = new CouponCommand.Issue(123, 1);
        Coupon expiredCoupon = new Coupon(1, "expired", 10, 100, 10,
                LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(1));

        when(couponRepository.findById(1)).thenReturn(Optional.of(expiredCoupon));

        // expect
        assertThrows(IllegalStateException.class, () -> couponService.issueCoupon(command));
    }

    @Test
    @DisplayName("[실패] 쿠폰 재고가 부족한 경우 IllegalStateException 예외 발생하는 케이스")
    void issueCoupon_noStock() {
        // given
        CouponCommand.Issue command = new CouponCommand.Issue(123, 1);

        Coupon coupon = spy(new Coupon(1, "10% 할인", 10, 100, 0,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        doThrow(new IllegalStateException("쿠폰 재고가 부족합니다.")).when(coupon).issueOne();

        when(couponRepository.findById(1)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.findByUserIdAndCouponId(123, 1)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> couponService.issueCoupon(command));
    }


    @Test
    @DisplayName("동시 쿠폰 발급 요청 시 재고 초과 발급이 되지 않는다")
    // 현재는 딱맞게 발급이 될때도있고, 초과해서 발급되기도 함
    void issueCoupon_concurrentRequest_shouldNotOverIssue() throws InterruptedException {
        // given
        int threadCount = 100;
        int couponId = 1;
        int stock = 50;  // 재고 50개로 설정

        Coupon coupon = spy(new Coupon(couponId, "10% 할인", 10, 100, stock,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1)));

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.findByUserIdAndCouponId(anyInt(), eq(couponId)))
                .thenReturn(Optional.empty());

        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            int userId = i + 1;
            executor.execute(() -> {
                try {
                    couponService.issueCoupon(new CouponCommand.Issue(userId, couponId));
                    successCount.incrementAndGet();
                } catch (Exception ignored) {
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        System.out.println(successCount.get());
        System.out.println(stock);

        // then
        assertTrue(successCount.get() <= stock, "재고보다 많이 발급되면 안 됨");
    }



}
