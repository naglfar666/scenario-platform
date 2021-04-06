package org.example.task.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TopicListener {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topicPattern = "${kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void accept(String message) {

        log.info("Accepted message {}", message);

        kafkaTemplate.send("router-in-topic", message);
    }

}
