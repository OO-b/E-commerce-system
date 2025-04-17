package kr.hhplus.be.server.domain.order;

import java.util.Optional;

public interface OrderRepository {
    UserOrder save(UserOrder order);

    Optional<UserOrder> findById(int orderId);
}
