package kr.hhplus.be.server.interfaces.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.order.OrderFacade;
import kr.hhplus.be.server.application.order.OrderResult;
import kr.hhplus.be.server.interfaces.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order Controller", description = "Order Controller")
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController implements OrderInterface {

    private final OrderFacade orderFacade;

    @Override
    @PostMapping
    public BaseResponse<OrderResponse> orderProduct(@RequestBody OrderRequest orderRequest) {

        OrderResult orderResult = orderFacade.order(OrderRequest.of(orderRequest));

        return BaseResponse.of("0","주문이 정상적으로 처리되었습니다.",
                new OrderResponse(orderResult.getOrderId(),
                                    orderResult.getTotalOrderCnt(),
                                    orderResult.getTotalPrice()));
    }

}
