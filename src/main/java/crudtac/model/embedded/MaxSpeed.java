package crudtac.model.embedded;

import com.fasterxml.jackson.annotation.JsonProperty;
import crudtac.model.embedded.enums.UnitVelocity;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@ToString
@Embeddable
public class MaxSpeed implements Serializable{
    @JsonProperty
    @Column(precision=10, scale=3)
    double value;


    @JsonProperty
    @Enumerated(EnumType.STRING)
    UnitVelocity unit;

    public MaxSpeed() {}

    public MaxSpeed(double v, UnitVelocity mps) {
        this.value = v;
        this.unit = mps;
    }




    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public UnitVelocity getUnit() {
        return unit;
    }

    public void setUnit(UnitVelocity unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return  value + " " + unit;
    }
}
