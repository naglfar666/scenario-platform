package org.example.model.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MessageStep {

    private String name;

    private Timestamp requestTimeStart;

    private Timestamp requestTimeEnd;

    private Map<String, Object> executionResult;

}
