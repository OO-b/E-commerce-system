package kr.hhplus.be.server.interfaces.coupon;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.BaseResponse;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Coupon Controller", description = "Coupon Controller")
@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController implements CouponInterface {

    /**
     * 선착순 쿠폰 발급
     * */
    @Override
    @PostMapping("/first-come")
    public BaseResponse<CouponResponse> issueFirstComeCoupon(@RequestBody CouponRequest couponRequest) {
        return BaseResponse.of("0","Success", new CouponResponse("봄이온다 쿠폰", 30,"2025-04-05 23:59:59"));
    }
}
