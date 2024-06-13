package org.example.orderservice.Repositories;

import org.example.orderservice.Entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StationRepo extends JpaRepository<Station, Long> {
    Optional<Station> findFirstByStation(@Param(value = "station") String city);
}
