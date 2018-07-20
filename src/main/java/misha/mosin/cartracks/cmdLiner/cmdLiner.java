package misha.mosin.cartracks.cmdLiner;

import misha.mosin.cartracks.model.Car;
import misha.mosin.cartracks.model.Track;
import misha.mosin.cartracks.repository.CarRepository;
import misha.mosin.cartracks.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.Arrays;


@Component
public class cmdLiner implements CommandLineRunner{

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private TrackRepository trackRepository;

    @Override
    public void run(String... strings) throws Exception {


        Car c1 = new Car("rdb1", Car.Transmission.automatic, Car.AI.enabled, new Car.MaxSpeed(110.12121212, Car.MaxSpeed.UnitVelocity.mps));
        Car c2 = new Car("rdb2", Car.Transmission.automatic, Car.AI.disabled, new Car.MaxSpeed(120.967, Car.MaxSpeed.UnitVelocity.mps));



        this.trackRepository.save(
                new Track("Millbrook", "Millbrook city course race track",
                        new Track.Length(7.4, Track.Length.UnitLen.km),
                        Arrays.asList(c1,c2)));
    }
}

