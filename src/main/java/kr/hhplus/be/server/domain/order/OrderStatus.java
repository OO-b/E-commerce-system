package kr.hhplus.be.server.domain.order;

public enum OrderStatus {
    ORDERED, // 주문완료
    PAID,    // 결제완료
    PAYMENT_FAILED // 결제실패
}
