package kr.hhplus.be.server.interfaces.coupon;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coupon Controller", description = "Coupon Controller")
@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController implements CouponInterface {

    /**
     * 사용자 보유 쿠폰 목록 조회
     * */
    @Override
    @GetMapping("/{userId}/coupons")
    public BaseResponse<List<UserCouponResponse>> getUserCoupons(@PathVariable int userId) {

        List<UserCouponResponse> userCouponResponses = List.of(
                new UserCouponResponse(1, "새해맞이쿠폰", "2025-01-01 08:00:00", "2025-03-30 23:59:59"),
                new UserCouponResponse(2, "새학기 쿠폰", "2025-03-01 08:00:00", "2025-04-30 23:59:59"),
                new UserCouponResponse(3, "이커머스가 쏜다", "2025-04-04 08:00:00", "2025-04-07 23:59:59")
        );

        return BaseResponse.of("0","정상적으로 조회하였습니다.", userCouponResponses);
    }


    /**
     * 선착순 쿠폰 발급
     * */
    @Override
    @PostMapping("/first-come")
    public BaseResponse<CouponResponse> issueFirstComeCoupon(@RequestBody CouponRequest couponRequest) {
        return BaseResponse.of("0","Success", new CouponResponse("봄이온다 쿠폰", 30,"2025-04-05 23:59:59"));
    }
}
