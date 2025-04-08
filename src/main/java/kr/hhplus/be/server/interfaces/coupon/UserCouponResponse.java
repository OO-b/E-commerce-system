package kr.hhplus.be.server.interfaces.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "사용자 보유 쿠폰 Response")
public class UserCouponResponse {
    @Schema(description = "쿠폰ID")
    private int couponId;
    @Schema(description = "쿠폰명")
    private String couponNm;
    @Schema(description = "쿠폰발급일")
    private String issueDate;
    @Schema(description = "쿠폰만료일")
    private String expiredDate;
}
