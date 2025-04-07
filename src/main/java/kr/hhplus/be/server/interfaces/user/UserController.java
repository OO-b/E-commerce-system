package kr.hhplus.be.server.interfaces.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.interfaces.common.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Controller", description = "User Controller")
@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserInterface {

    /**
     * 사용자 잔액 충전
     * */
    @Override
    @PostMapping("/{userId}/points")
    public BaseResponse<?> chargeUserPoint(@PathVariable int userId,
                                                 @RequestBody @Valid PointChargeRequest pointChargeRequest) {

        return BaseResponse.of("0","정상적으로 충전되었습니다.", new PointChargeResponse(pointChargeRequest.getPoint(), pointChargeRequest.getPoint()+30000));
    }

    /**
     * 사용자 잔액 조회
     * */
    @Override
    @GetMapping("/{userId}/points")
    public BaseResponse<Integer> getUserPoint(@PathVariable int userId) {
        return BaseResponse.of("0","정상적으로 조회하였습니다.", 100);
    }

    /**
     * 사용자 보유 쿠폰 목록 조회
     * */
    @Override
    @GetMapping("/{userId}/coupons")
    public BaseResponse<List<UserCouponResponse>> getUserCoupons(@PathVariable int userId) {

        List<UserCouponResponse> userCouponResponses = List.of(
                new UserCouponResponse(1, "새해맞이쿠폰", "2025-01-01 08:00:00", "2025-03-30 23:59:59"),
                new UserCouponResponse(2, "새학기 쿠폰", "2025-03-01 08:00:00", "2025-04-30 23:59:59"),
                new UserCouponResponse(3, "이커머스가 쏜다", "2025-04-04 08:00:00", "2025-04-07 23:59:59")
        );

        return BaseResponse.of("0","정상적으로 조회하였습니다.", userCouponResponses);
    }

    /**
     * 사용자 주문 (+결제)
     * */
    @Override
    @PostMapping("/{userId}/orders")
    public BaseResponse<OrderResponse> orderProduct(@PathVariable int userId, @RequestBody OrderRequest orderRequest) {
        return BaseResponse.of("0","주문이 정상적으로 처리되었습니다.", new OrderResponse(1, 3, 300000));
    }

}
