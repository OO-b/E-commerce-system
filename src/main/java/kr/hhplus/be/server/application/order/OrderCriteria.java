package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.product.OrderOptionCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class OrderCriteria {

    private int userId;
    private Integer couponId;
    private List<OrderProduct> items;

    @Getter
    @AllArgsConstructor
    public static class OrderProduct {
        private int productId;
        private List<OrderOption> options;
    }

    @Getter
    @AllArgsConstructor
    public static class OrderOption {
        private int optionId;
        private int quantity;
    }


    public List<OrderOptionCommand> toCommandList() {
        return items.stream()
                .flatMap(orderProduct -> orderProduct.getOptions().stream())
                .map(orderOption -> new OrderOptionCommand(
                        orderOption.getOptionId(),
                        orderOption.getQuantity()
                ))
                .collect(Collectors.toList());
    }


}
