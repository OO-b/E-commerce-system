package kr.hhplus.be.server.controller.api;

import io.swagger.v3.oas.annotations.Operation;

public interface ProductInterface {

    @Operation(summary = "전체 상품 조회 API", method = "GET")
    public void getAllProducts();

    @Operation(summary = "상위 상품 조회 API", method = "GET")
    public void getTopSellingProducts();

}
