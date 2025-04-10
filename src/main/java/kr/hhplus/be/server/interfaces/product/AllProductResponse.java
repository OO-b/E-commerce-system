package kr.hhplus.be.server.interfaces.product;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "전체 상품조회 Response")
public record AllProductResponse (
        @Schema(description = "상품 일련번호")
        int productId,
        @Schema(description = "상품명")
        String name,
        @Schema(description = "상품옵션")
        List<Option> option
) {
    public record Option(
            @Schema(description = "상품옵션 일련번호")
            String productOptionId,
            @Schema(description = "옵션명")
            String optionNm,
            @Schema(description = "가격")
            String price,
            @Schema(description = "재고량")
            String remaining
    ) {}
}