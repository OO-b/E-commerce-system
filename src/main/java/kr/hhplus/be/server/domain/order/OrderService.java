package kr.hhplus.be.server.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderInfo order(OrderCommand.Detail command) {

        OrderEntity order = new OrderEntity(command.getUserId(),
                                            OrderStatus.ORDERED,
                                            LocalDateTime.now());

        OrderEntity savedOrder = orderRepository.save(order);


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
        OrderEntity order = orderRepository.findById(orderInfo.getOrderId())
                .orElseThrow(() -> new IllegalStateException("주문 정보를 찾지 못했습니다."));

        order.markAsFailed();
    }
}
