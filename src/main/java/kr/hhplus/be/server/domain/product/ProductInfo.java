package kr.hhplus.be.server.domain.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}