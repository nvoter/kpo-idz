package org.example.orderservice.Controllers;

import org.example.orderservice.Data.OrderData;
import org.example.orderservice.Entities.Order;
import org.example.orderservice.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestHeader String token, @RequestBody OrderData orderData){
        Order order = orderService.createOrder(orderData, token);

        return ResponseEntity.ok(order);
    }

    @GetMapping("{id}")
    public ResponseEntity getOrderInfo(@PathVariable(required = true) Integer id){
        Order order = orderService.getOneById(id);

        return ResponseEntity.ok(order);
    }
}
