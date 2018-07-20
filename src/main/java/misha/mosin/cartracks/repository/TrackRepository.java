package misha.mosin.cartracks.repository;

import misha.mosin.cartracks.model.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TrackRepository extends CrudRepository<Track, Long> {

    List<Track> findAll();
}
