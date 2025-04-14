package kr.hhplus.be.server.infrastructure.coupon;

import kr.hhplus.be.server.domain.coupon.UserCoupon;
import kr.hhplus.be.server.domain.coupon.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCouponRepositoryImpl implements UserCouponRepository {

    @Override
    public Optional<UserCoupon> findByUserIdAndCouponId(int userId, int couponId) {
        return Optional.empty();
    }

    @Override
    public void save(UserCoupon userCoupon) {

    }
}
