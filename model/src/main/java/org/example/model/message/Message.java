package org.example.model.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Message implements Serializable, Cloneable {

    private UUID requestId;

    private Timestamp requestTimeStart;

    private Timestamp requestTimeEnd;

    private String scenarioId;

    @JsonDeserialize(as = LinkedHashMap.class)
    @JsonSerialize(as = LinkedHashMap.class)
    private Map<String, MessageStep> steps;

    private Map<String, Object> payload;

    @Override
    public Message clone() {
        try {
            return (Message) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Message(requestId, requestTimeStart, requestTimeEnd, scenarioId, steps, payload);
        }
    }
}
