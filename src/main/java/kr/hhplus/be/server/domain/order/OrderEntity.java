package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_order")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private int customerId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime orderDate;

    public OrderEntity(int customerId,  OrderStatus status, LocalDateTime orderDate) {
        this.customerId = customerId;
        this.status = status;
        this.orderDate = orderDate;
    }

    public void markAsFailed() {
        if (this.status == OrderStatus.PAID) {
            throw new IllegalStateException("이미 결제완료된 주문은 변경할 수 없습니다.");
        }
        this.status = OrderStatus.PAYMENT_FAILED;
    }
}