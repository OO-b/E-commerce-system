package kr.hhplus.be.server.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.dto.request.CouponRequest;
import kr.hhplus.be.server.dto.response.CouponResponse;
import kr.hhplus.be.server.dto.common.BaseResponse;

public interface CouponInterface {

    @Operation(summary = "선착순 쿠폰발급 API", method = "GET")
    BaseResponse<CouponResponse> issueFirstComeCoupon(CouponRequest couponRequest);
}
