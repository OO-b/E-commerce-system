package kr.hhplus.be.server.interfaces.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "상품 주문 Response")
public class OrderResponse {


    @Getter
    @Setter
    @AllArgsConstructor
    @Schema(description = "주문 Response")
    public static class Order{
        @Schema(description = "주문 일련번호")
        private int orderId;
        @Schema(description = "전체 주문 갯수")
        private int totalOrderCnt;
        @Schema(description = "전체 주문 가격")
        private int totalPrice;
    }

    @Getter
    @AllArgsConstructor
    @Schema(description = "인기상품주문 Response")

    public static class Popular{
        private Integer productId;
        private String productName;
        private Long totalAmount;
    }
    
}
