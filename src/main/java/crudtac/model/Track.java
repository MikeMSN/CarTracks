package crudtac.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import crudtac.model.embedded.Length;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Track implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRACK_ID")
    private Long id;

    private String name;

    private String description;

    @JsonProperty
    @Embedded
    private Length length;

    /*___@OneToMany(fetch = FetchType.LAZY, mappedBy = "track", cascade = CascadeType.ALL)
    private List<Car> cars;
*/

    public Track() {
        length = new Length();
    }

    public Track(String name, String description, Length length/*, List<Car> cars*/) {
        this.name = name;
        this.description = description;
        this.length = length;
        //cars.forEach(car -> car.setTrack(this));
        //this.cars = cars;
    }

    public Long getId() {
        return id;
    }

  /*  public void addCar(Car car) {
        if (car != null) {
            car.setTrack(this);
            cars.add(car);
        }
    }

    public List<Car> getCars() {
        return cars;
    }*/


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Length getLength() {
        return length;
    }

    public void setLength(Length length) {
        this.length = length;
    }

  /*  public void setCars(List<Car> cars) {
        this.cars = cars;
    }*/
}
