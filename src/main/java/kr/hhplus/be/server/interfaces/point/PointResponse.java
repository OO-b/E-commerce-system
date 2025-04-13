package kr.hhplus.be.server.interfaces.point;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PointResponse {

    @Schema(description = "충전된 포인트")
    private int chargedPoint;
    @Schema(description = "사용자 총 포인트")
    private int totalPoint;

}
