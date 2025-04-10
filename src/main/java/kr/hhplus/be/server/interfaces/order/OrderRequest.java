package kr.hhplus.be.server.interfaces.order;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.application.order.OrderCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "상품 주문 Request")
public class OrderRequest {
    @Schema(description = "사용자 ID")
    private int userId;
    @Schema(description = "사용할 쿠폰 ID")
    private Integer couponId;
    @Schema(description = "주문 상품 목록")
    private List<OrderProduct> items;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class OrderProduct {

        @Schema(description = "상품 ID")
        private int productId;
        @Schema(description = "옵션 목록")
        private List<OrderOption> options;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class OrderOption {

        @Schema(description = "옵션 ID")
        private int optionId;
        @Schema(description = "주문 수량")
        private int quantity;
    }

    public static OrderCriteria of(OrderRequest request) {
        List<OrderCriteria.OrderProduct> products = request.getItems().stream()
                .map(item -> new OrderCriteria.OrderProduct(
                        item.getProductId(),
                        item.getOptions().stream()
                                .map(opt -> new OrderCriteria.OrderOption(opt.getOptionId(), opt.getQuantity()))
                                .toList()
                ))
                .toList();

        return new OrderCriteria(request.getUserId(), request.getCouponId(), products);
    }
}
