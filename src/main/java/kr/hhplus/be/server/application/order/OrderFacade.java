package kr.hhplus.be.server.application.order;


import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponCommand;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.*;
import kr.hhplus.be.server.domain.payment.PaymentCommand;
import kr.hhplus.be.server.domain.payment.PaymentFailedException;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.point.PointCommand;
import kr.hhplus.be.server.domain.point.PointService;
import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductInfo;
import kr.hhplus.be.server.domain.product.ProductOptionInfo;
import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final CouponService couponService;
    private final PointService pointService;

    public OrderResult order(OrderCriteria criteria) {

        // 재고차감
        List<ProductCommand.OrderOption> orderOptionCommand = criteria.toCommandList();
        productService.decreaseProduct(orderOptionCommand);

        //쿠폰 유효성 체크
        Coupon coupon = null;
        if (criteria.getCouponId() != null) {
            coupon = couponService.validateAndGetDiscount(new CouponCommand.Usage(criteria.getUserId(), criteria.getCouponId()));
        }

        // 주문저장을 위한 데이터 생성
        List<ProductInfo.OrderProduct> orderItems = criteria.getItems().stream()
                .flatMap(orderProduct -> {
                    int productId = orderProduct.getProductId();
                    String productName = productService.getProductNameById(productId);

                    return orderProduct.getOptions().stream().map(option -> {
                        ProductOptionInfo optionInfo = productService.getOptionInfoById(option.getOptionId());

                        return new ProductInfo.OrderProduct(
                                productId,
                                productName,
                                optionInfo.getOptionName(),
                                optionInfo.getPrice(),
                                option.getQuantity()
                        );
                    });
                })
                .collect(Collectors.toList());
        // 주문저장
        OrderInfo orderInfo = orderService.order(new OrderCommand.Detail(criteria.getUserId(), orderItems, coupon));

        try {
            // 포인트 차감
            pointService.usePoints(new PointCommand.Usage(criteria.getUserId(), orderInfo.getTotalAmount()));
            // 결제
            paymentService.savePayment(new PaymentCommand.Record(orderInfo.getOrderId(), orderInfo.getTotalAmount()));
            
        } catch(Exception e) { // 결제 실패
             // 결제실패 상태처리
            orderService.failOrder(orderInfo);
             throw new PaymentFailedException();
        }

        return new OrderResult(orderInfo.getOrderId(), orderItems.size(), orderInfo.getTotalAmount());
    }

}
