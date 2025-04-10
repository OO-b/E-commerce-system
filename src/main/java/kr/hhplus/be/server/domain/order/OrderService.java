package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.domain.coupon.Coupon;
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

    public OrderInfo order(OrderCommand command) {

        OrderEntity order = new OrderEntity(command.getUserId(),
                                            OrderStatus.ORDERED,
                                            LocalDateTime.now());

        OrderEntity savedOrder = orderRepository.save(order);


        List<OrderItemEntity> orderItems = command.getOrderInfo().stream()
                .map(productCommand -> new OrderItemEntity(
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

        if (command.getCoupon().isPresent()) {
            Coupon coupon = command.getCoupon().get();
            int discountRate = coupon.getDiscountRate();

            int discountAmount = (int) Math.round(totalAmount * (discountRate / 100.0));
            totalAmount = Math.max(0, totalAmount - discountAmount);
        }

        return new OrderInfo(savedOrder.getOrderId(),totalAmount);

    }

}
