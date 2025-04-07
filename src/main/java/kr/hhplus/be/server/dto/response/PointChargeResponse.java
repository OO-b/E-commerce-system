package kr.hhplus.be.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PointChargeResponse {

    @Schema(description = "충전된 포인트")
    private int chargedPoint;
    @Schema(description = "사용자 총 포인트")
    private int totalPoint;

}
