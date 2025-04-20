package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;

public class PaymentFailedException extends BusinessException {
    public PaymentFailedException() {
        super(ErrorCode.PAYMENT_FAILED.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.PAYMENT_FAILED;
    }
}