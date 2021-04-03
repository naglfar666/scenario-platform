package org.example.router.configuration.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TopicListener {

    @KafkaListener(topicPattern = "${kafka.inTopicName}", containerFactory = "kafkaListenerContainerFactory")
    public void acceptMessage(String message) {
        log.info("Accepted kafka message {}", message);
    }

}