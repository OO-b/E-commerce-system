package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponId;
    @Column(nullable = false)
    private String couponNm;
    @Column(nullable = false)
    private int discountRate;
    @Column(nullable = false)
    private int issuedCount;
    @Column(nullable = false)
    private int remainingCount;
    @Column(nullable = false)
    private LocalDateTime issueDate;
    @Column(nullable = false)
    private LocalDateTime expirationDate;

    public boolean isExpired(LocalDateTime now) {
        return expirationDate.isBefore(now);
    }

    public void issueOne() {
        if (remainingCount <= 0) {
            throw new IllegalStateException("쿠폰 재고가 부족합니다.");
        }
        this.remainingCount -= 1;
    }

}
