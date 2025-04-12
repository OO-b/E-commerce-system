package kr.hhplus.be.server.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductOption {

    private int productId;
    private int productOptionId;
    private String optionNm;
    private int price;
    private int remaining;

    public void decrease(int quantity) {
        if(quantity <=0) throw new IllegalArgumentException("수량은 0보다 커야합니다.");
        if (remaining < quantity) throw new IllegalStateException("재고가 부족합니다.");

        this.remaining -= quantity;
    }
}
