package kr.hhplus.be.server.domain.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Coupon {

    private int couponId;
    private String couponNm;
    private int discountRate;
    private int issuedCount;
    private int remainingCount;
    private LocalDateTime issueDate;
    private LocalDateTime expirationDate;

    public boolean isExpired(LocalDateTime now) {
        return expirationDate.isBefore(now);
    }

}
