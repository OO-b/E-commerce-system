package kr.hhplus.be.server.interfaces.point;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.domain.point.UserPoint;
import kr.hhplus.be.server.domain.point.PointService;
import kr.hhplus.be.server.interfaces.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Point Controller", description = "Point Controller")
@RestController
@RequestMapping("/api/v1/point")
@RequiredArgsConstructor
public class PointController implements PointInterface {

    private final PointService pointService;

    /**
     * 사용자 잔액 충전
     * */
    @Override
    @PostMapping("/{userId}")
    public BaseResponse<?> chargeUserPoint(@PathVariable int userId,
                                           @RequestBody @Valid PointRequest pointRequest) {

        UserPoint userPoint = pointService.charge(PointRequest.of(userId, pointRequest.getPoint()));
        return BaseResponse.of("0","정상적으로 충전되었습니다.", new PointResponse(pointRequest.getPoint(), userPoint.getPoint()));
    }

    /**
     * 사용자 잔액 조회
     * */
    @Override
    @GetMapping("/{userId}")
    public BaseResponse<Integer> getUserPoint(@PathVariable int userId) {
        return BaseResponse.of("0","정상적으로 조회하였습니다.", 100);
    }


}
