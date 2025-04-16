package kr.hhplus.be.server.domain.order;

import java.util.Optional;

public interface OrderRepository {
    OrderEntity save(OrderEntity order);

    Optional<OrderEntity> findById(int orderId);
}
