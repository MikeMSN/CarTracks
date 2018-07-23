package crudtac.repository;

import crudtac.model.Track;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TrackRepository extends CrudRepository<Track, Long> {

    List<Track> findAll();

    List<Track> findByNameStartsWithIgnoreCase(String filterText);

}
