package crudtac.model.embedded;

import com.fasterxml.jackson.annotation.JsonProperty;
import crudtac.model.embedded.enums.UnitLen;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class Length implements Serializable{
    @JsonProperty
    @Column(precision=10, scale=3)
    double value;

    @JsonProperty("unit")
    @Enumerated(EnumType.STRING)
    UnitLen unitLen;

    public Length() {

    }

    public Length(Double v, UnitLen km) {
        this.value = v;
        this.unitLen = km;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public UnitLen getUnitLen() {
        return unitLen;
    }

    public void setUnitLen(UnitLen unitLen) {
        this.unitLen = unitLen;
    }

    @Override
    public String toString() {
        return value + " " + unitLen;
    }
}
