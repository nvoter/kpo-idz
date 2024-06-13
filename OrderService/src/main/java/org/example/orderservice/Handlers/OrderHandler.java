package org.example.orderservice.Handlers;

import org.example.orderservice.Entities.Order;
import org.example.orderservice.Entities.Status;
import org.example.orderservice.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class OrderHandler {
    private final OrderService orderService;

    @Autowired
    public OrderHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 10L)
    public void updateOrderStatus() {
        List<Order> orders = orderService.getAllOrdersWithCheckStatus();

        for (Order order : orders) {
            Status newStatus = getNewStatus();
            order.setStatus(newStatus);
            orderService.save(order);
        }
    }

    private Status getNewStatus() {
        Status[] statuses = Status.values();
        int index = ThreadLocalRandom.current().nextInt(1, statuses.length);
        return statuses[index];
    }
}
