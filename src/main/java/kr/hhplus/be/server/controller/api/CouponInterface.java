package kr.hhplus.be.server.controller.api;

import io.swagger.v3.oas.annotations.Operation;

public interface CouponInterface {

    @Operation(summary = "선착순 쿠폰발급 API", method = "GET")
    public void issueFirstComeCoupon();
}
