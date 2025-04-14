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
    private int productId;
    @Column(nullable = false, unique = true)
    private int productOptionId;
    @Column(nullable = false, unique = true)
    private String optionNm;
    @Column(nullable = false, unique = true)
    private int price;
    @Column(nullable = false, unique = true)
    private int remaining;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductOption(int productId, int productOptionId, String optionNm, int price, int remaining) {
        this.productId = productId;
        this.productOptionId = productOptionId;
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
