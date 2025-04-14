package kr.hhplus.be.server.domain.product;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public final class ProductInfo {

    @Getter
    @AllArgsConstructor
    public static class OrderProduct {
        private int productId;
        private String productName;
        private String optionName;
        private int productPrice;
        private int quantity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ProductOptionResult {
        private String optionNm;
        private int price;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ProductResult {
        private int productId;
        private String name;
        List<ProductOptionResult> options;
    }
}