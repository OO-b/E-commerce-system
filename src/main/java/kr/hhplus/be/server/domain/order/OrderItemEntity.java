package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderItem")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;
    private int orderId;
    private int productId;
    private String productName;
    private String productOptionName;
    private int productPrice;
    private int productAmount;

    public OrderItemEntity(int orderId, int productId, String productName, String productOptionName, int productPrice, int productAmount) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productOptionName = productOptionName;
        this.productPrice = productPrice;
        this.productAmount = productAmount;
    }
}