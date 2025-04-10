package kr.hhplus.be.server.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    public Optional<Coupon> validateAndGetDiscount(UseCouponCommand command) {

        return couponRepository.findById(command.getCouponId())
                .filter(coupon -> !coupon.isExpired(LocalDateTime.now()))
                .flatMap(coupon ->
                        userCouponRepository.findByUserIdAndCouponId(command.getUserId(), command.getCouponId())
                                .filter(UserCoupon::isUsable)
                                .map(userCoupon -> coupon) // userCoupon이 조건 만족하면 coupon 리턴
                );

    }
}
