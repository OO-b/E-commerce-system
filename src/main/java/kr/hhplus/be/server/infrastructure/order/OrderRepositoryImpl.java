package kr.hhplus.be.server.infrastructure.order;

import kr.hhplus.be.server.domain.order.UserOrder;
import kr.hhplus.be.server.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public UserOrder save(UserOrder order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Optional<UserOrder> findById(int orderId) {
        return orderJpaRepository.findById(orderId);
    }
}
