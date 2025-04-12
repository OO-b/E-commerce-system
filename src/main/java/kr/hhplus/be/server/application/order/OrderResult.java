package kr.hhplus.be.server.application.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderResult {

    private int orderId;
    private int totalOrderCnt;
    private int totalPrice;
}
