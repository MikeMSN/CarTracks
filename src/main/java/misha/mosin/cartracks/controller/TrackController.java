package misha.mosin.cartracks.controller;


import misha.mosin.cartracks.model.Car;
import misha.mosin.cartracks.model.Track;
import misha.mosin.cartracks.service.CarService;
import misha.mosin.cartracks.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class TrackController {
    @Autowired
    public TrackService trackService;

    @Autowired
    public CarService carService;

    @PostMapping(value = "/track/")
    public ResponseEntity<?> addTrack(@RequestBody Track track, UriComponentsBuilder ucBuilder) {
        if(trackService.isExist(track)){
            return ResponseEntity.badRequest().build();
        }
        trackService.add(track);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("/track/{id}").buildAndExpand(track.getId()).toUri());
        return  new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping(path = "/track/{track-id}/car/")
    public ResponseEntity<?> addCar(
            @PathVariable(value = "track-id") String trackId,
            @RequestBody Car car, UriComponentsBuilder ucBuilder) {

        Integer id = Integer.parseInt(trackId);
        if(carService.isExist(id, car)) {
            return ResponseEntity.badRequest().build();
        }
        carService.add(id, car);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("/track/{id}").buildAndExpand(id).toUri());
        return  new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/track/")
    public ResponseEntity<List<Track>> getTracks() {
        List<Track> tracks = trackService.getAll();
        if(tracks.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(tracks);
    }

}
