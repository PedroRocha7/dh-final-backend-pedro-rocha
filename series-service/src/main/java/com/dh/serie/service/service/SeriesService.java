package com.dh.serie.service.service;


import com.dh.serie.service.model.Serie;
import com.dh.serie.service.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;

    private static final Logger LOG = LoggerFactory.getLogger(SeriesService.class);

    @Value("${queue.series.name}")
    private String seriesQueue;

    @Autowired
    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public Serie getSeriesById(String id) {
        return seriesRepository.findById(id).orElse(null);
    }

    public List<Serie> getAllSeries() {
        return seriesRepository.findAll();
    }

    @RabbitListener(queues = "${queue.series.name}")
    public Serie saveSeries(Serie serie) {
        LOG.info("Saving the new series");
        return seriesRepository.save(serie);
    }
}
