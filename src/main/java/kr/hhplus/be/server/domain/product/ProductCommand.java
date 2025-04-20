package kr.hhplus.be.server.domain.product;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductCommand {

    @Setter
    @Getter
    @AllArgsConstructor
    public static class OrderOption {
        private int productOptionId;
        private int quantity;
    }

}
