package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productOptionId;
    @Column(nullable = false)
    private int productId;
    @Column(nullable = false)
    private String optionNm;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int remaining;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductOption(int productOptionId, int productId, String optionNm, int price, int remaining) {
        this.productOptionId = productOptionId;
        this.productId = productId;
        this.optionNm = optionNm;
        this.price = price;
        this.remaining = remaining;
        this.createdAt = LocalDateTime.now();
    }

    public void decrease(int quantity) {
        if(quantity <=0) throw new IllegalArgumentException("수량은 0보다 커야합니다.");
        if (remaining < quantity) throw new IllegalStateException("재고가 부족합니다.");
        this.remaining -= quantity;
    }
}
