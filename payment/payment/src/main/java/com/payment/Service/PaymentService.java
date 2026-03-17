package com.payment.Service;

import com.payment.Entity.Payment;
import com.payment.Repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment doPayment(Payment payment) {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public String paymentProcessing() {

        //API should be 3rd party PaymentGateway (PayPal, RazorPay, Phonepe)

        return new Random().nextBoolean() ? "SUCCESS" : "FAILURE";
    }

    public Payment findPaymentHistoryByOrderId(Integer orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for orderId: " + orderId));
    }
}
