package misha.mosin.cartracks.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRACK_ID")
    private Long id;

    private String name;

    private String description;

    @JsonProperty
    @Embedded
    private Length length;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "race", cascade = CascadeType.ALL)
    List<Car> cars;


    @Embeddable
    public static class Length {
        @JsonProperty
        @Column(precision=10, scale=3)
        double value;

        @JsonProperty("unit")
        @Enumerated(EnumType.STRING)
        UnitLen unitLen;

        public Length() {}

        public Length(Double v, UnitLen km) {
            this.value = v;
            this.unitLen = km;
        }

        public enum UnitLen {
            mps, km, kms
        }
    }




    public Track() {
    }

    public Track(String name, String description, Length length, List<Car> cars) {
        this.name = name;
        this.description = description;
        this.length = length;
        cars.forEach(car -> car.setRace(this));
        this.cars = cars;
    }
    public Long getId() {
        return id;
    }
    public void addCar(Car car) {
        if(car != null) {
            car.setRace(this);
            cars.add(car);
        }
    }
    public List<Car> getCars() {
        return cars;
    }
}
