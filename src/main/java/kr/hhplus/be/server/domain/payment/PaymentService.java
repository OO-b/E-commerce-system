package kr.hhplus.be.server.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public void savePayment(PaymentCommand.Record command) {
            Payment payment = Payment.of(
                    command.getOrderId(),
                    command.getTotalPayment()
            );

            paymentRepository.save(payment);
    }
}
