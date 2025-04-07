package kr.hhplus.be.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "상위 상품조회 Response")
public class PointResponse {

    @Schema(description = "포인트")
    private int point;

}
