package kr.hhplus.be.server.domain.coupon;

import java.util.Optional;

public interface UserCouponRepository {
    Optional<UserCoupon> findByUserIdAndCouponId(int userId, int couponId);
    void save(UserCoupon userCoupon);
}
