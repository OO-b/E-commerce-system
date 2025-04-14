package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "userOrder")
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
}