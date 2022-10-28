package com.example.rabbitmq_producer.module;

import com.example.rabbitmq_producer.domain.DataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class Module_A {

    private final RabbitTemplate rabbitTemplate;
    private String exchange;
    private String nowDate;
    private String moduleName;

    @PostConstruct
    public void init(){
        this.exchange = "myExchange";
        this.moduleName = "Module_A";
    }


    // 문자열 전송
    @Scheduled(fixedRate = 1000) // 1초 마다 반복
    public void textSend() {
        nowDate = LocalDateTime.now().toString();

        System.out.println("[textSend] ==> " + nowDate);
        System.out.println("[textSend] ==> " + moduleName);

        // rabbitmq 전송 (exchange, routing Key, Request data)
        rabbitTemplate.convertAndSend(exchange, "mail_1_key", nowDate);
    }


    // 객채 전송 (Jackson2JsonMessageConverter Bean 생성 필요)
    @Scheduled(fixedRate = 1100)
    public  void dtoSend() {
        nowDate = LocalDateTime.now().toString();
        DataDTO dto = DataDTO.builder().apiName(moduleName).nowDate(nowDate).brokerName(exchange).build();
        System.out.println("    [dtoSend] ==> " + nowDate);
        System.out.println("    [dtoSend] ==> " + dto.toString());
        rabbitTemplate.convertAndSend(exchange, "mail_2_key", dto);
    }
}
