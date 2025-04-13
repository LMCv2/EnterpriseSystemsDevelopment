package com.aib.websystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.LocationType;
import com.aib.websystem.entity.Stock;
import com.aib.websystem.repository.FruitRepository;
import com.aib.websystem.repository.LocationRepository;
import com.aib.websystem.repository.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private FruitRepository fruitRepository;

    @Autowired
    private LocationRepository locationRepository;

    public void addAllFruitToLocation(Location location) {
        if (location.getType() != LocationType.SOURCE_WAREHOUSE) {
            for (Fruit fruit : fruitRepository.findAll()) {
                stockRepository.save(new Stock(fruit, location, 0));
            }
        }
    }

    public void addFruitToAllLocation(Fruit fruit) {
        for (Location location : locationRepository.findAll()) {
            if (location.getType() != LocationType.SOURCE_WAREHOUSE) {
                stockRepository.save(new Stock(fruit, location, 0));
            }
        }
    }

    public boolean FruitExist(Fruit fruit, Location location) {
        if (stockRepository.findByFruitAndLocationType(fruit, location.getType(), null).isEmpty()) {
            return false;
        }else{
            return true;
        }

    }
}
