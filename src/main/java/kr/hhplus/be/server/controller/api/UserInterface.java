package kr.hhplus.be.server.controller.api;

import io.swagger.v3.oas.annotations.Operation;

public interface UserInterface {

    @Operation(summary = "사용자 잔액충전 API", method = "POST")
    public void chargeUserPoint();

    @Operation(summary = "사용자 잔액조회 API", method = "GET")
    public void getUserPoint();

    @Operation(summary = "사용자 보유 쿠폰 목록 조회 API", method = "GET")
    public void getUserCoupons();

    @Operation(summary = "사용자 주문 조회 API", method = "GET")
    public void orderProduct();


}
