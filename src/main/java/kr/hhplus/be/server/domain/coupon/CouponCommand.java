package kr.hhplus.be.server.domain.coupon;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CouponCommand {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Usage {
        private int userId;
        private int couponId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Issue {
        private int userId;
        private int couponId;
    }

}
