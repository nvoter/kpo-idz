package org.example.orderservice.Entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "from_station_id", nullable = false)
    private Station fromStation;

    @ManyToOne
    @JoinColumn(name = "to_station_id", nullable = false)
    private Station toStation;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp created;
}