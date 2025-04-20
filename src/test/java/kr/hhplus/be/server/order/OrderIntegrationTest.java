package kr.hhplus.be.server.order;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.order.*;
import kr.hhplus.be.server.domain.product.ProductInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class OrderIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @DisplayName("[성공] 사용자가 쿠폰을 사용하여 상품을 주문하는 케이스 ")
    void givenUserAndProduct_whenOrderWithCoupon_thenSuccess() {
        // given
        int userId = 1;

        List<ProductInfo.OrderProduct> products = List.of(
                new ProductInfo.OrderProduct(1, "상품 A", "옵션1", 10000, 2),  // 20000
                new ProductInfo.OrderProduct(1, "상품 B", "옵션2", 15000, 1)   // 15000
        );

        Coupon coupon = new Coupon(0, "봄맞이 할인쿠폰", 10, 100, 100, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(5));
        OrderCommand.Detail command = new OrderCommand.Detail(userId, products, coupon);

        // when
        OrderInfo result = orderService.order(command);

        // then
        Optional<UserOrder> savedOrder = orderRepository.findById(result.getOrderId());
        List<OrderItem> savedItems = orderItemRepository.findByOrderId(result.getOrderId());

        assertThat(savedOrder).isPresent();
        assertThat(savedItems).hasSize(2);
        assertThat(result.getTotalAmount()).isEqualTo(31500); // (20000 + 15000) - 10%
    }

    @Test
    @DisplayName("[성공] 사용자가 쿠폰이 없을때 상품을 주문하는 케이스 ")
    void givenUserAndProduct_whenOrderWithoutCoupon_thenSuccess() {
        // given
        int userId = 2;

        List<ProductInfo.OrderProduct> products = List.of(
                new ProductInfo.OrderProduct(201, "상품 C", "옵션3", 8000, 1),   // 8000
                new ProductInfo.OrderProduct(202, "상품 D", "옵션4", 12000, 2)  // 24000
        );

        // 쿠폰 없이 주문
        OrderCommand.Detail command = new OrderCommand.Detail(userId, products, null);

        // when
        OrderInfo result = orderService.order(command);

        // then
        Optional<UserOrder> savedOrder = orderRepository.findById(result.getOrderId());
        List<OrderItem> savedItems = orderItemRepository.findByOrderId(result.getOrderId());

        assertThat(savedOrder).isPresent();
        assertThat(savedItems).hasSize(2);

        int expectedTotal = (8000 * 1 + 12000 * 2); // 8000 + 24000 = 32000
        assertThat(result.getTotalAmount()).isEqualTo(expectedTotal);
    }


    @Test
    @DisplayName("[성공] 인기상품 조회시 성공적으로 조회되는 케이스")
    void givenOrder_whenGetPopularOrderProduct_thenSuccess() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Random random = new Random();

        for (int i = 1; i <= 6; i++) {  // 6개 품목
            UserOrder order = new UserOrder(
                    i,
                    OrderStatus.PAID, // 결제완료된 제품으로만
                    now.minusDays(random.nextInt(3))
            );

            // 주문 저장
            orderRepository.save(order);

            int orderCount = random.nextInt(10) + 1;
            for (int j = 0; j < orderCount; j++) {
                OrderItem item = new OrderItem(
                        order.getOrderId(),
                        i,
                        "테스트상품" + i,
                        "옵션",
                        1000,
                        1
                );

                // 저장
                orderItemRepository.save(item);
            }
        }

        // when
        List<OrderTopInfo> popularProducts = orderService.getTopPopularProducts();

        // then
        assertThat(popularProducts).isNotNull();
        assertThat(popularProducts).hasSizeLessThanOrEqualTo(5);

        // 예시 출력
        popularProducts.forEach(p -> System.out.printf("상품: %s, 수량: %d\n", p.getProductName(), p.getTotalAmount()));
    }

}
