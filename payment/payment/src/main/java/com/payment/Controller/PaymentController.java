package com.payment.Controller;

import com.payment.Entity.Payment;
import com.payment.Service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/doPayment")
    public ResponseEntity<Payment> doPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.doPayment(payment);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> findPaymentHistoryByOrderId(@PathVariable Integer orderId) {
        Payment payment = paymentService.findPaymentHistoryByOrderId(orderId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
