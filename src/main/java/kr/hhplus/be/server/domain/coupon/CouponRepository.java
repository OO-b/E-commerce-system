package kr.hhplus.be.server.domain.coupon;

import java.util.Optional;

public interface CouponRepository {
    Optional<Coupon> findById(int couponId);
    void save(Coupon coupon);
}
