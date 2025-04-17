package kr.hhplus.be.server.infrastructure.order;

import kr.hhplus.be.server.domain.order.OrderItem;
import kr.hhplus.be.server.domain.order.OrderTopInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderId(int orderId);

    @Query("""
        SELECT oi.productId AS productId, oi.productName AS productName, SUM(oi.productAmount) AS totalAmount
        FROM OrderItem oi
        JOIN UserOrder uo ON oi.orderId = uo.orderId
        WHERE uo.orderDate >= :period
        GROUP BY oi.productId, oi.productName
        ORDER BY totalAmount DESC
    """)
    List<OrderTopInfo> findTopPopularProducts(@Param("period") LocalDateTime period, Pageable pageable);
}
