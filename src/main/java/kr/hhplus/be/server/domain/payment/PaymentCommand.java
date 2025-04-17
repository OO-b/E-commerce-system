package kr.hhplus.be.server.domain.payment;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentCommand {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Record {
        private int orderId;
        private int totalPayment;
    }
}