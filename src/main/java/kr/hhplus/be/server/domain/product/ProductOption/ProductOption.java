package kr.hhplus.be.server.domain.product.ProductOption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductOption {

    private int productOptionId;
    private String optionNm;
    private int price;
    private int remaining;
}
