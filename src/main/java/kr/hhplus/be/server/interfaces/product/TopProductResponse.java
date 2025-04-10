package kr.hhplus.be.server.interfaces.product;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "상위 상품조회 Response")
public class TopProductResponse {

    @Schema(description = "순서")
    private int rank;
    @Schema(description = "상품 일련번호")
    private int productId;
    @Schema(description = "상품명")
    private String productName;
    @Schema(description = "총 구매금액")
    private int totalPrice;
    @Schema(description = "총 주문량")
    private int totalSales;

}
