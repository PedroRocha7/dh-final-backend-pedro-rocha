package com.dh.catalog.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQSenderConfig {

    @Value("${queue.movie.name}")
    private String moviesQueue;

    @Value("${queue.series.name}")
    private String seriesQueue;

    @Bean
    public Queue moviesQueue() {
        return new Queue(this.moviesQueue, true);
    }

    @Bean
    public Queue seriesQueue() {
        return new Queue(this.seriesQueue, true);
    }
}
