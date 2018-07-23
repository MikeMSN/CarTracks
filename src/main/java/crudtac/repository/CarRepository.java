package crudtac.repository;

import crudtac.model.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {

    List<Car> findAllByTrackId(long trackId);
}
