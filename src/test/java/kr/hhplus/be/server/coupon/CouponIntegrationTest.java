package kr.hhplus.be.server.coupon;

import kr.hhplus.be.server.domain.coupon.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class CouponIntegrationTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Test
    @DisplayName("[성공] 선착순 쿠폰 요청시, 선착순 쿠폰 발급 성공")
    @Sql("/couponIntegrationTest.sql")
    void givenEnougCoupon_whenRequestingCoupon() {

        CouponCommand.Issue command = new CouponCommand.Issue(1, 1);

        // when
        couponService.issueCoupon(command);

        // then
        Optional<UserCoupon> userCoupon = userCouponRepository.findByUserIdAndCouponId(1, 1);
        assertThat(userCoupon.get().getCouponId()).isEqualTo(userCoupon.get().getCouponId());

        Coupon updatedCoupon = couponRepository.findById(userCoupon.get().getCouponId()).get();
        assertThat(updatedCoupon.getCouponId()).isEqualTo(1);
    }
}
