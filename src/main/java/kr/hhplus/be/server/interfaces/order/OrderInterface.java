package kr.hhplus.be.server.interfaces.order;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.interfaces.common.BaseResponse;

public interface OrderInterface {

    @Operation(summary = "사용자 주문 조회 API", method = "GET")
    BaseResponse<?> orderProduct(int userId, OrderRequest orderRequest);

}
