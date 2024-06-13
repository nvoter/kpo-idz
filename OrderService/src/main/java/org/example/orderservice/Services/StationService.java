package org.example.orderservice.Services;

import org.example.orderservice.Exceptions.StationNotFoundException;
import org.example.orderservice.Entities.Station;
import org.example.orderservice.Repositories.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {
    private final StationRepo stationRepo;

    @Autowired
    public StationService(StationRepo stationRepo) {
        this.stationRepo = stationRepo;
    }

    public Station getStationByName(String name) {
        return stationRepo.findFirstByStation(name).orElseThrow(StationNotFoundException::new);
    }
}