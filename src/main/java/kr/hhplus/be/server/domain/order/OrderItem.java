package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;
    @Column(nullable = false)
    private int orderId;
    @Column(nullable = false)
    private int productId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String productOptionName;
    @Column(nullable = false)
    private int productPrice;
    @Column(nullable = false)
    private int productAmount;

    public OrderItem(int orderId, int productId, String productName, String productOptionName, int productPrice, int productAmount) {

        if (productPrice < 0 || productAmount <= 0) {
            throw new IllegalArgumentException("상품 가격이나 수량이 유효하지 않습니다.");
        }

        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productOptionName = productOptionName;
        this.productPrice = productPrice;
        this.productAmount = productAmount;
    }


}