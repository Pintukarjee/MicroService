package com.order.Controller;

import com.order.Common.TransactionRequest;
import com.order.Common.TransactionResponse;
import com.order.Entity.Order;
import com.order.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/bookOrder")
    public ResponseEntity<TransactionResponse> createOrder(@RequestBody TransactionRequest transactionRequest) {
        return new ResponseEntity<>(
                orderService.bookOrder(transactionRequest),
                HttpStatus.CREATED
        );
    }

    /*
        @PostMapping("/bookOrder")
        public ResponseEntity<String> createOrder(@RequestBody Order order) {
            return new ResponseEntity<>(
                    orderService.bookOrder(order),
                    HttpStatus.CREATED
            );
        }
    */
    @GetMapping("/{orderId}")
    public ResponseEntity<Optional<Order>> getOrderById(@PathVariable Integer orderId) {
        return new ResponseEntity<>(
                orderService.getOrderById(orderId),
                HttpStatus.OK
        );
    }

}
