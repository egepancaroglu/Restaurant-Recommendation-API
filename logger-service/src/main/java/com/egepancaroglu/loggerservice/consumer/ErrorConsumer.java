package com.egepancaroglu.loggerservice.consumer;

import com.egepancaroglu.loggerservice.dto.ConsumedGeneralErrorMessages;
import com.egepancaroglu.loggerservice.dto.GeneralErrorMessages;
import com.egepancaroglu.loggerservice.repository.ErrorLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author egepancaroglu
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ErrorConsumer {

    private final ErrorLogRepository errorLogRepository;

    @RabbitListener(queues = {"${rabbitmq.queue.error.name}"})
    public void consume(ConsumedGeneralErrorMessages consumedGeneralErrorMessages) {

        log.info(String.format("Reieved message -> %s", consumedGeneralErrorMessages));

        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages();

        generalErrorMessages.setDate(consumedGeneralErrorMessages.getDate());
        generalErrorMessages.setMessage(consumedGeneralErrorMessages.getMessage());
        generalErrorMessages.setDescription(consumedGeneralErrorMessages.getDescription());

        errorLogRepository.save(generalErrorMessages);

    }

}
