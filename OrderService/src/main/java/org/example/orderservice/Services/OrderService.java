package org.example.orderservice.Services;

import com.sun.istack.NotNull;
import org.example.orderservice.Entities.Station;
import org.example.orderservice.Exceptions.OrderNotFoundException;
import org.example.orderservice.Exceptions.AuthFailedException;
import org.example.orderservice.Data.OrderData;
import org.example.orderservice.Entities.Order;
import org.example.orderservice.Entities.Status;
import org.example.orderservice.Entities.User;
import org.example.orderservice.Repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final StationService stationService;
    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public OrderService(
            OrderRepo orderRepo,
            StationService stationService,
            RestTemplate restTemplate,
            @Value("${baseUrl}") String baseUrl
    ) {
        this.orderRepo = orderRepo;
        this.stationService = stationService;
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @NotNull
    public Order createOrder(@NotNull OrderData orderData, @NotNull String userToken) {
        Station fromStation = stationService.getStationByName(orderData.getFromStation());
        Station toStation = stationService.getStationByName(orderData.getToStation());

        User user = fetch(userToken);

        Order order = new Order();
        order.setStatus(Status.Check);
        order.setToStation(toStation);
        order.setFromStation(fromStation);
        order.setUserId(Math.toIntExact(Objects.requireNonNull(user.getId())));

        return orderRepo.save(order);
    }

    private User fetch(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<User> response = restTemplate.exchange(baseUrl + "/auth/user", HttpMethod.GET, entity, User.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new AuthFailedException("Auth failed");
        }

        return response.getBody();
    }

    public List<Order> getAllOrdersWithCheckStatus() {
        return orderRepo.findAllByStatus(Status.Check);
    }

    public Order save(Order order) {
        return orderRepo.save(order);
    }

    public Order getOneById(Integer id) {
        return orderRepo.findFirstById(id).orElseThrow(OrderNotFoundException::new);
    }
}
