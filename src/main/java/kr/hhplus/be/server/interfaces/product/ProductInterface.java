package kr.hhplus.be.server.interfaces.product;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.interfaces.common.BaseResponse;

public interface ProductInterface {

    @Operation(summary = "전체 상품 조회 API", method = "GET")
    BaseResponse<?> getAllProducts();

    @Operation(summary = "상위 상품 조회 API", method = "GET")
    BaseResponse<?> getTopSellingProducts();

}
