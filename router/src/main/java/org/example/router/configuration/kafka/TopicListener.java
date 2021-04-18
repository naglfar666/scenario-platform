package org.example.router.configuration.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.common.util.JsonUtil;
import org.example.model.message.Message;
import org.example.router.service.MessageService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TopicListener {

    private final MessageService messageService;

    public TopicListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topicPattern = "${kafka.inTopicName}", containerFactory = "kafkaListenerContainerFactory")
    public void acceptMessage(String message) {
        log.info("Accepted kafka message {}", message);

        Message messageResult = JsonUtil.toObject(message, Message.class);

        log.info("Message in cache {}", JsonUtil.toString(messageService.getById(messageResult.getRequestId())));
    }

}