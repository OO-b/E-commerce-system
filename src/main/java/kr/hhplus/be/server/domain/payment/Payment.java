package kr.hhplus.be.server.domain.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    private int orderId;
    private int totalPayment;
    private LocalDateTime paymentDate;

    public Payment(int orderId, int totalPayment) {
        this.orderId = orderId;
        this.totalPayment = totalPayment;
        this.paymentDate = LocalDateTime.now();
    }

    public static Payment of(int orderId, int totalPayment) {
        if (totalPayment <= 0) {
            throw new IllegalArgumentException("결제 금액은 0보다 커야 합니다.");
        }
        return new Payment(orderId, totalPayment);
    }
}
