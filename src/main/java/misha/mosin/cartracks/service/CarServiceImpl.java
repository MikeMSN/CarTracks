package misha.mosin.cartracks.service;

import misha.mosin.cartracks.model.Car;
import misha.mosin.cartracks.model.Track;
import misha.mosin.cartracks.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    CarRepository carRepository;

    @Autowired
    TrackService trackService;

    @Override
    public void add(Integer trackId, Car car) {
        Optional<Track> trackOp = trackService.getById(trackId);
        if(trackOp.isPresent()){
            Track track = trackOp.get();
            track.addCar(car);
            trackService.add(track);
        }

    }

    @Override
    public boolean isExist(Integer trackId, Car car) {
        Optional<Track> trackOp = trackService.getById(trackId);
        if(trackOp.isPresent()){
            Track track = trackOp.get();
            return track.getCars().stream().anyMatch(car::equals);
        }
        return false;
    }
}