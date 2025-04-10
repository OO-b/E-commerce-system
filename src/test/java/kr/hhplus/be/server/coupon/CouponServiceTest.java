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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        UseCouponCommand command = new UseCouponCommand(userId, couponId);

        Coupon coupon = new Coupon(couponId, "10% 할인", 10, 100, 100, LocalDateTime.now(), LocalDateTime.now().plusDays(1)); // 직접 생성
        UserCoupon userCoupon = new UserCoupon(couponId, userId, CouponStatus.AVAILABLE, LocalDateTime.now()); // 직접 생성

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.findByUserIdAndCouponId(userId, couponId)).thenReturn(Optional.of(userCoupon));

        // when
        Optional<Coupon> result = couponService.validateAndGetDiscount(command);

        // then
        assertTrue(result.isPresent());
        assertEquals(coupon, result.get());
    }

    @Test
    @DisplayName("[실패] 사용자가 이미 사용한 쿠폰인 경우 coupon은 null값 반환")
    void givenAlreadyUseUserCoupon_whenUseCoupon_thenFailure() {
        // given
        int couponId = 100;
        int userId = 1;
        UseCouponCommand command = new UseCouponCommand(userId, couponId);

        Coupon coupon = new Coupon(couponId, "10% 할인", 10, 100, 100, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1)); // 직접 생성
        UserCoupon userCoupon = new UserCoupon(couponId, userId, CouponStatus.USED, LocalDateTime.now().minusDays(1));

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.findByUserIdAndCouponId(userId, couponId)).thenReturn(Optional.of(userCoupon));

        // when
        Optional<Coupon> result = couponService.validateAndGetDiscount(command);

        // then
        assertFalse(result.isPresent());
    }



}
