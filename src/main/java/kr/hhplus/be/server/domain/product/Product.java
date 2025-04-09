package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.domain.product.ProductOption.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Product {

    private int productId;
    private String name;
    private List<ProductOption> options;

}
