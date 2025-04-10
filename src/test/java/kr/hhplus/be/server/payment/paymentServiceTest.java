package kr.hhplus.be.server.payment;

import kr.hhplus.be.server.domain.payment.Payment;
import kr.hhplus.be.server.domain.payment.PaymentRepository;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.payment.RecordPaymentCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    @DisplayName("[성공] 사용자 결제 등록시 성공하는 케이스")
    void givenPaymentAmount_whenPay_thenSuccess() {
        // given
        int orderId = 123;
        int totalPayment = 5000;

        RecordPaymentCommand command = new RecordPaymentCommand(orderId, totalPayment);

        // when
        paymentService.savePayment(command);

        // then
        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(captor.capture());

        Payment saved = captor.getValue();
        assertEquals(orderId, saved.getOrderId());
        assertEquals(totalPayment, saved.getTotalPayment());
        assertNotNull(saved.getPaymentDate());
    }

    @Test
    @DisplayName("[성공] 결제 금액이 음수인경우 IllegalArgumentException 에러발생")
    void givenNegativePaymentAmount_whenPay_thenFailure() {
        // given
        RecordPaymentCommand command = new RecordPaymentCommand(1, -1000);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> paymentService.savePayment(command));
    }
}
