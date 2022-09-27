package com.dh.serie.service.controller;


import com.dh.serie.service.model.Serie;
import com.dh.serie.service.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class SeriesController {

    private final SeriesService seriesService;

    @Autowired
    public SeriesController(SeriesService seriesService){
        this.seriesService = seriesService;
    }

    @GetMapping
    public ResponseEntity<List<Serie>> getAllSeries(){
        return ResponseEntity.ok(seriesService.getAllSeries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> getSeriesById(@PathVariable("id") String id){
        return ResponseEntity.ok(seriesService.getSeriesById(id));
    }

    @PostMapping
    public ResponseEntity<Serie> saveSeries(@RequestBody Serie serie){
        return ResponseEntity.ok(seriesService.saveSeries(serie));
    }

}
