package kr.hhplus.be.server.interfaces.point;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import kr.hhplus.be.server.domain.point.PointChargeCommand;
import lombok.Getter;

@Getter
@Schema(description = "잔액 충전 Request")
public class PointChargeRequest {

    @Positive(message = "포인트는 0보다 커야 합니다.")
    @Schema(description = "포인트")
    private int point;

    public static PointChargeCommand of(int userId, int point) {
        return  new PointChargeCommand(userId, point);
    }

}
