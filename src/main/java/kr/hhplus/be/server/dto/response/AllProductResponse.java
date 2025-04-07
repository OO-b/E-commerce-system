package kr.hhplus.be.server.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "전체 상품조회 Response")
public class AllProductResponse {
    @Schema(description = "상품 일련번호")
    private int productId;
    @Schema(description = "상품명")
    private String productName;
    @Schema(description = "상품금액")
    private int price;
    @Schema(description = "잔여수량")
    private int remaining;

}
