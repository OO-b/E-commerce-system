package kr.hhplus.be.server.interfaces.coupon;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.interfaces.common.BaseResponse;

public interface CouponInterface {

    @Operation(summary = "선착순 쿠폰발급 API", method = "GET")
    BaseResponse<CouponResponse> issueFirstComeCoupon(CouponRequest couponRequest);
}
