package kr.hhplus.be.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.controller.api.CouponInterface;
import kr.hhplus.be.server.dto.request.CouponRequest;
import kr.hhplus.be.server.dto.response.CouponResponse;
import kr.hhplus.be.server.dto.common.BaseResponse;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Coupon Controller", description = "Coupon Controller")
@RestController
@RequestMapping("/coupon")
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
