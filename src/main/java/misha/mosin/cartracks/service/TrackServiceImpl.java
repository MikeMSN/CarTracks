package misha.mosin.cartracks.service;

import misha.mosin.cartracks.model.Track;
import misha.mosin.cartracks.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImpl implements TrackService {

    @Autowired
    TrackRepository trackRepository;

    @Override
    public void add(Track track) {
        trackRepository.save(track);
    }

    @Override
    public List<Track> getAll() {
        return trackRepository.findAll();
    }

    @Override
    public boolean isExist(Track track) {
        return trackRepository.findAll().stream().anyMatch(track::equals);
    }

    @Override
    public Optional<Track> getById(Integer trackId) {
        return trackRepository.findById(Long.valueOf(trackId));
    }




}
