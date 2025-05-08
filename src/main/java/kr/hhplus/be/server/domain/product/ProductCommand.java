package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.application.order.OrderCriteria;
import lombok.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductCommand {

    @AllArgsConstructor
    @Getter
    public static class DecreaseStock{
        private List<OrderOption> orderOptions;

        public static DecreaseStock of(List<ProductCommand.OrderOption> orderOptions) {
            List<OrderOption> sortedOptions = orderOptions.stream()
                    .sorted(Comparator.comparing(OrderOption::getProductOptionId))
                    .toList();
            return new DecreaseStock(sortedOptions);
        }

        public String toOptionIds() {
            return orderOptions.stream()
                    .map(OrderOption::getProductOptionId)
                    .sorted()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class OrderOption {
        private int productOptionId;
        private int quantity;
    }


}
