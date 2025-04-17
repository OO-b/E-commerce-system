package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_coupon")
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCouponId;
    @Column(nullable = false)
    private int couponId;
    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    private CouponStatus status; // AVAILABLE, USED
    @Column(nullable = false)
    private LocalDateTime issuedAt;

    public UserCoupon(int couponId, int userId, CouponStatus status) {
        this.couponId = couponId;
        this.userId = userId;
        this.status = status;
        this.issuedAt = LocalDateTime.now();
    }

    public UserCoupon(int couponId, int userId, CouponStatus status, LocalDateTime localDateTime) {
        this.couponId = couponId;
        this.userId = userId;
        this.status = status;
        this.issuedAt = localDateTime;
    }


    public boolean isUsable() {
        return CouponStatus.AVAILABLE.equals(status);
    }
}
