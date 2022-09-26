package com.dh.catalog.api.controller;

import com.dh.catalog.api.service.CatalogService;
import com.dh.catalog.domain.dto.MovieDTO;
import com.dh.catalog.domain.dto.SerieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{genre}")
    public ResponseEntity<List<MovieDTO>> getMoviesByGenre(@PathVariable String genre) {
        return catalogService.getMoviesByGenre(genre);
    }

    @GetMapping("/withErrors/{genre}")
    public ResponseEntity<List<MovieDTO>> getMoviesByGenre(@PathVariable String genre, @RequestParam("throwError") boolean throwError) {
        return catalogService.getMoviesByGenre(genre, throwError);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMovie(@RequestBody MovieDTO movieDTO) {
        catalogService.saveMovie(movieDTO);
        return ResponseEntity.ok("New movie sent to the queue");
    }

    @PostMapping("/save/series")
    public ResponseEntity<String> saveSeries(@RequestBody SerieDTO serieDTO) {
        catalogService.saveSeries(serieDTO);
        return ResponseEntity.ok("Series was sent to queue");
    }
}
