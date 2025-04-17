package kr.hhplus.be.server.domain.coupon;

import java.util.Optional;

public interface CouponRepository {
    Optional<Coupon> findById(int couponId);
    Coupon save(Coupon coupon);
}
