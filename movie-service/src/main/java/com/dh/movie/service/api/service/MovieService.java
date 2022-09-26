package com.dh.movie.service.api.service;

import com.dh.movie.service.domain.models.Movie;
import com.dh.movie.service.domain.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovieByGenre(String genre){
        LOG.info("Searching movies by genre: " + genre);
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getMovieByGenre(String genre, Boolean throwError){
        LOG.info("Searching movies by genre: " + genre);
        if (throwError){
            LOG.error("Can't fetch movies by genre " + genre);
            throw new RuntimeException();
        }
        return movieRepository.findByGenre(genre);
    }

    @RabbitListener(queues = "${queue.movie.name}")
    public Movie saveMovie(Movie movie){
        LOG.info("Saving new movie on RabbitMQ " + movie.toString());
        return movieRepository.save(movie);
    }

}
