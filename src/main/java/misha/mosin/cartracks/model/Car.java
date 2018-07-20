package misha.mosin.cartracks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;


@Data
@Entity
public class Car {

    @Id
    @GeneratedValue
    private  Long id;
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TRACK_ID", nullable = false, updatable = false, insertable = true)
    private Track race;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    public enum Transmission {
        automatic, non_automatic
    }

    @Enumerated(EnumType.STRING)
    public AI ai;

    @Embedded
    @JsonProperty("max-speed")
    public MaxSpeed maxSpeed;


    public enum AI {
        enabled, disabled
    }

    @Embeddable
    public static class MaxSpeed {
        @JsonProperty
        @Column(precision=10, scale=3)
        Double value;

        @JsonProperty
        @Enumerated(EnumType.STRING)
        UnitVelocity unit;

        public MaxSpeed() {}

        public MaxSpeed(Double v, UnitVelocity mps) {
            this.value = v;
            this.unit = mps;
        }
        public enum UnitVelocity {
            mps, kms
        }
    }

    public Car() {
    }

    public Car(String name, Transmission transmission, AI ai, MaxSpeed maxSpeed) {
        this.name = name;
        this.transmission = transmission;
        this.ai = ai;
        this.maxSpeed = maxSpeed;
    }

    public void setRace(Track race) {
        this.race = race;
    }
}
