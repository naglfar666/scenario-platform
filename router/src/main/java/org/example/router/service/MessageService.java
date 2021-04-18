package org.example.router.service;

import org.example.common.exception.MessageNotFoundException;
import org.example.common.util.JsonUtil;
import org.example.model.message.Message;
import org.example.router.entity.MessageCached;
import org.example.router.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message getById(UUID id) {
        MessageCached messageCached = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("Не найдено сообщение в кэше для ID " + id));
        return JsonUtil.toObject(messageCached.getContent(), Message.class);
    }

    public Message save(Message message) {
        MessageCached messageCached = new MessageCached(message.getRequestId(), JsonUtil.toString(message));

        messageRepository.save(messageCached);

        return message;
    }

}
