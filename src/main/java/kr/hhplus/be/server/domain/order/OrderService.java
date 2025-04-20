package kr.hhplus.be.server.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderInfo order(OrderCommand.Detail command) {

        UserOrder order = new UserOrder(command.getUserId(),
                                            OrderStatus.ORDERED,
                                            LocalDateTime.now());

        UserOrder savedOrder = orderRepository.save(order);


        List<OrderItem> orderItems = command.getOrderInfo().stream()
                .map(productCommand -> new OrderItem(
                        savedOrder.getOrderId(),
                        productCommand.getProductId(),
                        productCommand.getProductName(),
                        productCommand.getOptionName(),
                        productCommand.getProductPrice(),
                        productCommand.getQuantity()
                ))
                .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);

        int totalAmount = command.getOrderInfo().stream()
                .mapToInt(item -> item.getProductPrice() * item.getQuantity())
                .sum();

        if (command.getCoupon() != null) {
            int discountRate = command.getCoupon().getDiscountRate();

            int discountAmount = (int) Math.round(totalAmount * (discountRate / 100.0));
            totalAmount = Math.max(0, totalAmount - discountAmount);
        }

        return new OrderInfo(savedOrder.getOrderId(), totalAmount);

    }

    public void failOrder(OrderInfo orderInfo) {
        UserOrder order = orderRepository.findById(orderInfo.getOrderId())
                .orElseThrow(() -> new IllegalStateException("주문 정보를 찾지 못했습니다."));

        order.markAsFailed();
    }

    public List<OrderTopInfo> getTopPopularProducts() {

        LocalDate startDate = LocalDate.now().minusDays(3);
        LocalDate endDate = LocalDate.now().minusDays(1);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        Pageable pageable = PageRequest.of(0, 5); // 상위 5개

        return orderItemRepository.findTopPopularProducts(startDateTime, endDateTime, pageable);

    }
}
