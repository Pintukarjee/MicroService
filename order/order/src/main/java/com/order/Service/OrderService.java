package com.order.Service;

import com.order.Common.Payment;
import com.order.Common.TransactionRequest;
import com.order.Common.TransactionResponse;
import com.order.Entity.Order;
import com.order.Repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public TransactionResponse bookOrder(TransactionRequest transactionRequest) {

        String response = " ";

        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();

        Order savedOrder = orderRepository.save(order);
        payment.setOrderId(savedOrder.getOrderId());
        payment.setAmount(savedOrder.getPrice());

        //REST API CALL
        Payment paymentResponse = restTemplate.postForObject(
                "http://PAYMENT-SERVICE/v1/payment/doPayment",
                payment,
                Payment.class
        );

        /*  currently we write this in string later will add Hystrix.  */

        response = paymentResponse.getPaymentStatus().equals("SUCCESS")
                ? "Payment processing successfully and Order placed.!"
                : "There is a failure in PAYMENT API , Order added to cart.";

        return new TransactionResponse(
                savedOrder,
                paymentResponse.getAmount(),
                paymentResponse.getTransactionId(),
                response
        );
    }
/*
    public String bookOrder(Order order) {

        orderRepository.save(order);
        return "Order Booked";
    }
*/

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Integer orderId) {
        return orderRepository.findById(orderId);
    }
}
