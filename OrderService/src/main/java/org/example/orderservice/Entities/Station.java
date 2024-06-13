package org.example.orderservice.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "station", nullable = false, length = 100)
    private String station;
}