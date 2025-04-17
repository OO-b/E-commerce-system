package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.product.ProductInfo;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderCommand {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Detail {
        private int userId;
        private List<ProductInfo.OrderProduct> orderInfo;
        private Coupon coupon;
    }

}