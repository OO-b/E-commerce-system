package kr.hhplus.be.server.interfaces.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order Controller", description = "Order Controller")
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController implements OrderInterface {


    /**
     * 사용자 주문 (+결제)
     * */
    @Override
    @PostMapping("/{userId}/orders")
    public BaseResponse<OrderResponse> orderProduct(@PathVariable int userId, @RequestBody OrderRequest orderRequest) {
        return BaseResponse.of("0","주문이 정상적으로 처리되었습니다.", new OrderResponse(1, 3, 300000));
    }

}
