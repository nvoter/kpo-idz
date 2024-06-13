package org.example.orderservice.Repositories;

import org.example.orderservice.Entities.Order;
import org.example.orderservice.Entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAllByStatus(Status status);

    Optional<Order> findFirstById(@Param(value = "id") Integer id);
}
