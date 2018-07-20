package misha.mosin.cartracks.repository;

import misha.mosin.cartracks.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface CarRepository extends CrudRepository<Car, Long> {
}
