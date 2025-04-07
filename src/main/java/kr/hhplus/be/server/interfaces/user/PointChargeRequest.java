package kr.hhplus.be.server.interfaces.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "잔액 충전 Request")
public class PointChargeRequest {

    @Positive
    @Schema(description = "포인트")
    private int point;

}
