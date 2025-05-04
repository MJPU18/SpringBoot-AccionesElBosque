package co.edu.unbosque.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.request.OrderRequest;
import co.edu.unbosque.service.OrderService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alpaca/orders")
public class OrderController {

    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{accountId}")
    public Mono<String> placeOrder(@PathVariable String accountId, @RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(accountId, orderRequest);
    }
}