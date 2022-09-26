package com.dh.catalog.api.service;

import com.dh.catalog.api.client.MovieClient;
import com.dh.catalog.domain.dto.MovieDTO;
import com.dh.catalog.domain.dto.SerieDTO;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {

    private final MovieClient movieClient;
    private final RabbitTemplate rabbitTemplate;
    private final Logger LOG = LoggerFactory.getLogger(CatalogService.class);

    @Value("${queue.movie.name}")
    private String moviesQueue;

    @Value("${queue.series.name}")
    private String seriesQueue;

    @Autowired
    public CatalogService(MovieClient movieClient, RabbitTemplate rabbitTemplate) {
        this.movieClient = movieClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResponseEntity<List<MovieDTO>> getMoviesByGenre(String genre) {
        LOG.info("Fetching movies by genre: " + genre);
        return movieClient.getMoviesByGenre(genre);
    }

    @CircuitBreaker(name = "movies", fallbackMethod = "moviesFallBackMethod")
    public ResponseEntity<List<MovieDTO>> getMoviesByGenre(String genre, Boolean throwError) {
        LOG.info("Fetching movies by genre: " + genre);
        return movieClient.getMoviesByGenreWithErrors(genre, throwError);
    }

    public ResponseEntity<List<MovieDTO>> moviesFallBackMethod(CallNotPermittedException exception) {
        LOG.info("An error occurred while fetching movies by genre");
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void saveMovie(MovieDTO movieDTO) {
        rabbitTemplate.convertAndSend(moviesQueue, movieDTO);
    }

    public void saveSeries(SerieDTO serieDTO) {
        rabbitTemplate.convertAndSend(seriesQueue, serieDTO);
    }

}
