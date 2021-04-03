package org.example.router.controller;

import org.example.model.domain.ScenarioStepType;
import org.example.model.message.Message;
import org.example.model.message.MessageStep;
import org.example.model.scenario.Scenario;
import org.example.model.scenario.ScenarioBranch;
import org.example.model.scenario.ScenarioStep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/action")
@Slf4j
public class ActionController {

    String data = "{\"scenarioId\":\"SimpleScenario\",\"scenarioSteps\":{\"Event_13bwfmf\":{\"id\":\"Event_13bwfmf\",\"name\":null,\"instanceId\":\"ffa1fa7a-261f-489a-86de-a95192917d6c\",\"type\":\"END\",\"parameters\":{},\"branches\":[]},\"StartEvent_3\":{\"id\":\"StartEvent_3\",\"name\":null,\"instanceId\":\"25ce3def-284e-446d-840f-e662560cce6d\",\"type\":\"START_MESSAGE\",\"parameters\":{\"test1\":\"prop1\",\"test2\":\"prop2\"},\"branches\":[{\"stepId\":\"ExecutableTask_1\",\"expression\":null,\"defaultFlow\":true}]},\"Gateway_18tljzs\":{\"id\":\"Gateway_18tljzs\",\"name\":null,\"instanceId\":\"d2bfddc9-c2ae-40ce-9924-6c8dc2b46dec\",\"type\":\"EXCLUSIVE_GATEWAY\",\"parameters\":{},\"branches\":[{\"stepId\":\"Event_13bwfmf\",\"expression\":\"some expression\",\"defaultFlow\":false},{\"stepId\":\"Event_1mbwy37\",\"expression\":null,\"defaultFlow\":true}]},\"StartEvent_1\":{\"id\":\"StartEvent_1\",\"name\":null,\"instanceId\":\"cf76df41-32bf-4d2c-8c84-770c03a19ab8\",\"type\":\"START\",\"parameters\":{},\"branches\":[{\"stepId\":\"ExecutableTask_1\",\"expression\":null,\"defaultFlow\":true}]},\"ExecutableTask_1\":{\"id\":\"ExecutableTask_1\",\"name\":\"TaskExecutor\",\"instanceId\":\"59e9fe6c-09a0-4c2c-9ce2-a04b3e5efb34\",\"type\":\"TASK\",\"parameters\":{},\"branches\":[{\"stepId\":\"Gateway_18tljzs\",\"expression\":null,\"defaultFlow\":true}]},\"StartEvent_2\":{\"id\":\"StartEvent_2\",\"name\":null,\"instanceId\":\"d0120c15-da8f-4fe6-9a37-dbda62f1b4da\",\"type\":\"START_TIMER\",\"parameters\":{},\"branches\":[{\"stepId\":\"ExecutableTask_1\",\"expression\":null,\"defaultFlow\":true}]},\"Event_1mbwy37\":{\"id\":\"Event_1mbwy37\",\"name\":null,\"instanceId\":\"8bc040dc-2b40-4cf5-9062-10f26dd5fde5\",\"type\":\"END\",\"parameters\":{},\"branches\":[]}}}\n";

    Map<String, Scenario> scenarioMap = new HashMap<>();

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ActionController(KafkaTemplate<String, String> kafkaTemplate) throws JsonProcessingException {
        this.kafkaTemplate = kafkaTemplate;
        ObjectMapper objectMapper = new ObjectMapper();

        Scenario scenario = objectMapper.readValue(data, Scenario.class);
        scenarioMap.put(scenario.getScenarioId(), scenario);
    }

    @RequestMapping(path = "/{scenarioId}/{stepId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> executeScenarioAction(HttpServletRequest request,
                                                   @PathVariable String scenarioId,
                                                   @PathVariable String stepId,
                                                   @RequestBody String requestString
    ) throws JsonProcessingException {
        log.info("Request method {}", request.getMethod());
        log.info("Scenario ID {} step ID {}", scenarioId, stepId);

        Scenario scenario = scenarioMap.get(scenarioId);

        if (scenario == null) {
            throw new RuntimeException("Scenario not found");
        }

        ScenarioStep step = scenario.getScenarioSteps().get(stepId);

        if (step == null || !step.getType().equals(ScenarioStepType.START_MESSAGE)) {
            throw new RuntimeException("Scenario step not found");
        }

        Map<String, Object> requestData = new ObjectMapper().readValue(requestString, HashMap.class);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        UUID requestId = UUID.randomUUID();

        for (ScenarioBranch branch : step.getBranches()) {
            ScenarioStep nextStep = scenario.getScenarioSteps().get(branch.getStepId());

            Map<String, MessageStep> steps = new LinkedHashMap<>();

            steps.put(nextStep.getId(), MessageStep.builder()
                    .name(nextStep.getName())
                    .requestTimeStart(currentTime)
                    .build());

            Message message = Message.builder()
                    .requestId(requestId)
                    .scenarioId(scenario.getScenarioId())
                    .incomeData(requestData)
                    .requestTimeStart(currentTime)
                    .steps(steps)
                    .build();

            kafkaTemplate.send(nextStep.getName(), new ObjectMapper().writeValueAsString(message));
        }

        return ResponseEntity
                .ok()
                .body(String.format("{\"requestId\":\"%s\"}", requestId));
    }

}
