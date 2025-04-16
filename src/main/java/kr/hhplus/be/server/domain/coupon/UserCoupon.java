package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_coupon")
public class UserCoupon {
    private int couponId;
    private int userId;
    private CouponStatus status; // AVAILABLE, USED
    private LocalDateTime issuedAt;

    public boolean isUsable() {
        return CouponStatus.AVAILABLE.equals(status);
    }
}
