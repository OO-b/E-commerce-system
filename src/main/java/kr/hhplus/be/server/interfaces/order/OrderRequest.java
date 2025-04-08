package kr.hhplus.be.server.interfaces.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "상품 주문 Request")
public class OrderRequest {
    @Schema(description = "상품 일련번호")
    private int productId;
    @Schema(description = "사용할 쿠폰 일련번호")
    private int couponId;
    @Schema(description = "주문 상품 정보")
    private List<OrderProductInfo> item;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class OrderProductInfo {
        @Schema(description = "상품 일련번호")
        private int productId;
        @Schema(description = "상품 주문개수")
        private int quantity;
        @Schema(description = "상품 가격")
        private int productPrice;
    }

}
