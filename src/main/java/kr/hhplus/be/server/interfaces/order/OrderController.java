package kr.hhplus.be.server.interfaces.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.order.OrderFacade;
import kr.hhplus.be.server.application.order.OrderResult;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.order.OrderTopInfo;
import kr.hhplus.be.server.interfaces.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Order Controller", description = "Order Controller")
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController implements OrderInterface {

    private final OrderFacade orderFacade;
    private final OrderService orderService;

    @Override
    @PostMapping
    public BaseResponse<OrderResponse.Order> orderProduct(@RequestBody OrderRequest orderRequest) {

        OrderResult orderResult = orderFacade.order(OrderRequest.of(orderRequest));

        return BaseResponse.of("0","주문이 정상적으로 처리되었습니다.",
                new OrderResponse.Order(orderResult.getOrderId(),
                                    orderResult.getTotalOrderCnt(),
                                    orderResult.getTotalPrice()));
    }


    /**
     * 상위 상품 조회
     * */
    @Override
    @GetMapping("/top")
    public BaseResponse<List<OrderResponse.Popular>> getTopPopularProducts() {

        List<OrderTopInfo> orderTopInfos = orderService.getTopPopularProducts();

        return BaseResponse.of("0",
                "Success",
                orderTopInfos.stream()
                        .map(p -> new OrderResponse.Popular(
                                p.getOroductId(),
                                p.getProductName(),
                                p.getTotalAmount()
                        ))
                        .collect(Collectors.toList())
        );
    }

}
