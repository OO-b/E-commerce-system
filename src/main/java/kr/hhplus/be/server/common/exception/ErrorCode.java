package kr.hhplus.be.server.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    PAYMENT_FAILED(HttpStatus.BAD_REQUEST, "결제에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
