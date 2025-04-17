package kr.hhplus.be.server.domain.order;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository {
    OrderItem save(OrderItem orderItems);
    List<OrderItem> saveAll(List<OrderItem> orderItems);
    List<OrderItem> findByOrderId(int orderId);
    List<OrderTopInfo> findTopPopularProducts(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
