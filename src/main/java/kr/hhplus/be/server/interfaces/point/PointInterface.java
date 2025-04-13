package kr.hhplus.be.server.interfaces.point;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.interfaces.common.BaseResponse;

public interface PointInterface {

    @Operation(summary = "사용자 잔액충전 API", method = "POST")
    BaseResponse<?> chargeUserPoint(int userId, PointRequest pointRequest);

    @Operation(summary = "사용자 잔액조회 API", method = "GET")
    BaseResponse<?> getUserPoint(int userId);

}
