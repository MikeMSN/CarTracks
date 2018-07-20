package misha.mosin.cartracks;


import misha.mosin.cartracks.model.Car;
import misha.mosin.cartracks.model.Track;
import misha.mosin.cartracks.repository.CarRepository;
import misha.mosin.cartracks.repository.TrackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {
    

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
