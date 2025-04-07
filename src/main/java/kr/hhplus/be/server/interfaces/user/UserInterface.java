package kr.hhplus.be.server.interfaces.user;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.interfaces.common.BaseResponse;

public interface UserInterface {

    @Operation(summary = "사용자 잔액충전 API", method = "POST")
    BaseResponse<?> chargeUserPoint(int userId, PointChargeRequest pointChargeRequest);

    @Operation(summary = "사용자 잔액조회 API", method = "GET")
    BaseResponse<?> getUserPoint(int userId);

    @Operation(summary = "사용자 보유 쿠폰 목록 조회 API", method = "GET")
    BaseResponse<?> getUserCoupons(int userId);

    @Operation(summary = "사용자 주문 조회 API", method = "GET")
    BaseResponse<?> orderProduct(int userId, OrderRequest orderRequest);


}
