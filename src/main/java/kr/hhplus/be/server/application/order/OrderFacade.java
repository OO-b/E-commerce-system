package kr.hhplus.be.server.application.order;


import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.UseCouponCommand;
import kr.hhplus.be.server.domain.order.*;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.payment.RecordPaymentCommand;
import kr.hhplus.be.server.domain.point.PointService;
import kr.hhplus.be.server.domain.point.PointUsageCommand;
import kr.hhplus.be.server.domain.product.OrderOptionCommand;
import kr.hhplus.be.server.domain.product.ProductOptionInfo;
import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final CouponService couponService;
    private final PointService pointService;

    @Transactional
    public OrderResult order(OrderCriteria criteria) {

        List<OrderOptionCommand> orderOptionCommand = criteria.toCommandList();

        // 재고차감
        productService.decreaseProduct(orderOptionCommand);

        //유효한 쿠폰인지 확인
        Optional<Coupon> coupon = Optional.empty();
        if(criteria.getCouponId() != null) {
            coupon = couponService.validateAndGetDiscount(new UseCouponCommand(criteria.getUserId(), criteria.getCouponId()));
        }

        // 주문
        List<OrderProductCommand> orderItems = criteria.getItems().stream()
                .flatMap(orderProduct -> {
                    int productId = orderProduct.getProductId();
                    String productName = productService.getProductNameById(productId);

                    return orderProduct.getOptions().stream().map(option -> {
                        ProductOptionInfo optionInfo = productService.getOptionInfoById(option.getOptionId());

                        return new OrderProductCommand(
                                productId,
                                productName,
                                optionInfo.getOptionName(),
                                optionInfo.getPrice(),
                                option.getQuantity()
                        );
                    });
                })
                .collect(Collectors.toList());

        OrderInfo orderInfo = orderService.order(new OrderCommand(criteria.getUserId(), orderItems, coupon));

        // 포인트 차감
        pointService.usePoints(new PointUsageCommand(criteria.getUserId(), orderInfo.getTotalAmount()));

        // 결제
        paymentService.savePayment(new RecordPaymentCommand(orderInfo.getOrderId(), orderInfo.getTotalAmount()));

        return new OrderResult(
                orderInfo.getOrderId(),
                orderItems.size(),
                orderInfo.getTotalAmount()
                );
    }
}
