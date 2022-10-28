package com.example.rabbitmq_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케쥴링을 위한 어노테이션
public class RabbitMqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqProducerApplication.class, args);
    }

}
