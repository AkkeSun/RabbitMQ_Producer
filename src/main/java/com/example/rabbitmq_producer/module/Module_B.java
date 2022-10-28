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
public class Module_B {

    private final RabbitTemplate rabbitTemplate;
    private String exchange;
    private String nowDate;
    private String moduleName;

    @PostConstruct
    public void init(){
        this.exchange = "myExchange";
        this.moduleName = "Module_B";
    }


    // 문자열 전송
    @Scheduled(fixedRate = 1400)
    public void textSend() {
        nowDate = LocalDateTime.now().toString();

        System.out.println("[textSend] ==> " + nowDate);
        System.out.println("[textSend] ==> " + moduleName);
        rabbitTemplate.convertAndSend(exchange, "mail_1_key", nowDate);
    }


    @Scheduled(fixedRate = 1300)
    public  void dtoSend() {
        nowDate = LocalDateTime.now().toString();
        DataDTO dto = DataDTO.builder().apiName(moduleName).nowDate(nowDate).brokerName(exchange).build();
        System.out.println("    [dtoSend] ==> " + nowDate);
        System.out.println("    [dtoSend] ==> " + dto.toString());
        rabbitTemplate.convertAndSend(exchange, "mail_2_key", dto);
    }
}
