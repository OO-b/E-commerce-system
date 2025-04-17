package kr.hhplus.be.server.order;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.order.*;
import kr.hhplus.be.server.domain.product.ProductInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("[성공] 사용자가 주문시 쿠폰이 없을때 주문금액 그대로 일치하는 케이스")
    void givenUserNoCoupon_whenOrder_thenSuccess() {
        // given
        int userId = 1;

        ProductInfo.OrderProduct product1 = new ProductInfo.OrderProduct(101, "나이키", "에어맥스 230", 1000, 2); // 2천원
        ProductInfo.OrderProduct product2 = new ProductInfo.OrderProduct(102, "컨버스", "레드 235", 3000, 1); // 2천원
        List<ProductInfo.OrderProduct> products = List.of(product1, product2);

        OrderCommand.Detail command = new OrderCommand.Detail(userId, products, null);

        UserOrder savedOrder = new UserOrder(userId, OrderStatus.ORDERED, LocalDateTime.now());
        savedOrder.setOrderId(999); // 저장된 주문 ID

        when(orderRepository.save(any(UserOrder.class))).thenReturn(savedOrder);

        // when
        OrderInfo result = orderService.order(command);

        // then
        assertEquals(999, result.getOrderId());
        assertEquals(5000, result.getTotalAmount());

        verify(orderRepository).save(any(UserOrder.class));
        verify(orderItemRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("[성공] 사용자가 주문시 쿠폰이 있을때 주문시 쿠폰적용이 성공하는 케이스")
    void givenUserHasCoupon_whenOrder_thenSuccess() {
        // given
        int userId = 1;
        ProductInfo.OrderProduct product = new ProductInfo.OrderProduct(101, "상품1", "옵션", 10000, 1); // 만원

        Coupon coupon = new Coupon(1, "봄이좋냐 10% 쿠폰🌼", 10, 100, 100, LocalDateTime.now(), LocalDateTime.now().plusDays(1)); // 10% 할인

        OrderCommand.Detail command = new OrderCommand.Detail(userId, List.of(product), coupon);

        UserOrder savedOrder = new UserOrder(userId, OrderStatus.ORDERED, LocalDateTime.now());
        savedOrder.setOrderId(123);

        when(orderRepository.save(any(UserOrder.class))).thenReturn(savedOrder);

        // when
        OrderInfo result = orderService.order(command);

        // then
        assertEquals(123, result.getOrderId());
        assertEquals(9000, result.getTotalAmount()); // 10% 할인

        verify(orderRepository).save(any(UserOrder.class));
        verify(orderItemRepository).saveAll(anyList());
    }
}
