package kr.hhplus.be.server.domain.order;

import java.util.List;

public interface OrderItemRepository {
    List<OrderItemEntity> saveAll(List<OrderItemEntity> orderItems);
}
