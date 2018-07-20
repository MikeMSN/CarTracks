package misha.mosin.cartracks.service;


import misha.mosin.cartracks.model.Track;

import java.util.List;
import java.util.Optional;


public interface TrackService {

    void add(Track track);

    List<Track> getAll();

    boolean isExist(Track track);

    Optional<Track> getById(Integer trackId);

}
