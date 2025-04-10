package kr.hhplus.be.server.interfaces.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description ="선착순 쿠폰발급 Request")
public class CouponRequest {
    @Schema(description ="사용자 일련번호")
    private int userId;
}
