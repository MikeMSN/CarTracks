package crudtac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import crudtac.model.embedded.MaxSpeed;
import crudtac.model.embedded.enums.AI;
import crudtac.model.embedded.enums.Transmission;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
public class Car implements Serializable{

    @Id
    @GeneratedValue
    private  Long id;
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="TRACK_ID", nullable = false, updatable = false, insertable = true)
    private Track track;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    public AI ai;

    @Embedded
    @JsonProperty
    public MaxSpeed maxSpeed;


    public Car() {
        maxSpeed = new MaxSpeed();
    }

    public Car(String name, Transmission transmission, AI ai, MaxSpeed maxSpeed) {
        this.name = name;
        this.transmission = transmission;
        this.ai = ai;
        this.maxSpeed = maxSpeed;
    }

    public void setTrack(Track track) {
        this.track = track;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Track getTrack() {
        return track;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }

    public MaxSpeed getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(MaxSpeed maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Long getId() {
        return id;
    }
}
