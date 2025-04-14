package kr.hhplus.be.server.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    private int productId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(int productId, String name) {
        this.productId = productId;
        this.name = name;
    }

}
