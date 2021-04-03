package org.example.model.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Message {

    private UUID requestId;

    private Timestamp requestTimeStart;

    private Timestamp requestTimeEnd;

    private String scenarioId;

    private Map<String, MessageStep> steps;

    private Map<String, Object> incomeData;

}
