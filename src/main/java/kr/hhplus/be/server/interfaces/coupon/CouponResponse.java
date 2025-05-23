package kr.hhplus.be.server.interfaces.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description ="선착순 쿠폰발급 Response")
public class CouponResponse {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class IssuedCoupon {
        @Schema(description ="쿠폰아이디")
        private int couponId;
        @Schema(description ="쿠폰명")
        private String couponName;
        @Schema(description ="할인율")
        private int discountRate;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @Schema(description = "사용자 보유 쿠폰")
    public static class UserCoupon {
        @Schema(description = "쿠폰ID")
        private int couponId;
        @Schema(description = "쿠폰명")
        private String couponName;
        @Schema(description = "쿠폰발급일")
        private String issueDate;
        @Schema(description = "쿠폰만료일")
        private String expiredDate;
    }

}
