package misha.mosin.cartracks.service;

import misha.mosin.cartracks.model.Car;

public interface CarService {
    void add(Integer trackId, Car car);

    boolean isExist(Integer trackId, Car car);
}
