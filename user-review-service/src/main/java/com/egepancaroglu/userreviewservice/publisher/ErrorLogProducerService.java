package com.egepancaroglu.userreviewservice.publisher;

import com.egepancaroglu.userreviewservice.general.GeneralErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author egepancaroglu
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ErrorLogProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String eventRoutingKey;

    public void sendMessage(GeneralErrorMessages generalErrorMessages) {
        rabbitTemplate.convertAndSend(exchange, eventRoutingKey, generalErrorMessages);
    }
}

