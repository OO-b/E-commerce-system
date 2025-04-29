package kr.hhplus.be.server.interfaces.coupon;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.coupon.CouponCommand;
import kr.hhplus.be.server.domain.coupon.CouponInfo;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.interfaces.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coupon Controller", description = "Coupon Controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController implements CouponInterface {

    private final CouponService couponService;


    /**
     * 사용자 보유 쿠폰 목록 조회
     * */
    @Override
    @GetMapping("/{userId}/coupons")
    public BaseResponse<List<CouponResponse.UserCoupon>> getUserCoupons(@PathVariable int userId) {

        List<CouponResponse.UserCoupon> userCoupons = List.of(
                new CouponResponse.UserCoupon(1, "새해맞이쿠폰", "2025-01-01 08:00:00", "2025-03-30 23:59:59"),
                new CouponResponse.UserCoupon(2, "새학기 쿠폰", "2025-03-01 08:00:00", "2025-04-30 23:59:59"),
                new CouponResponse.UserCoupon(3, "이커머스가 쏜다", "2025-04-04 08:00:00", "2025-04-07 23:59:59")
        );

        return BaseResponse.of("0","정상적으로 조회하였습니다.", userCoupons);
    }


    /**
     * 선착순 쿠폰 발급
     * */
    @Override
    @PostMapping("/first-come")
    public BaseResponse<CouponResponse.IssuedCoupon> issueFirstComeCoupon(@RequestBody CouponRequest couponRequest) {

        CouponInfo.issuedCoupon issuedCoupon = couponService.issueCoupon(new CouponCommand.Issue(couponRequest.getUserId(), couponRequest.getCouponId()));

        return BaseResponse.of("0","Success", new CouponResponse.IssuedCoupon(issuedCoupon.getCouponId(), issuedCoupon.getCouponName(), issuedCoupon.getDiscountRate()));
    }
}
