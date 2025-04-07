package kr.hhplus.be.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description ="선착순 쿠폰발급 Response")
public class CouponResponse {
    @Schema(description ="쿠폰명")
    private String couponNm;
    @Schema(description ="할인율")
    private int discountRate;
    @Schema(description ="쿠폰만료일")
    private String expiredDate;

}
