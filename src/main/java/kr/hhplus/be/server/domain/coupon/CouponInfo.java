package kr.hhplus.be.server.domain.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CouponInfo {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class issuedCoupon {
        private int couponId;
        private String couponName;
        private int discountRate;
    }
}
