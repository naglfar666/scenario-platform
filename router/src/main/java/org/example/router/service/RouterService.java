package org.example.router.service;

import org.example.common.util.JsonUtil;
import org.example.model.message.Message;
import org.example.model.message.MessageStep;
import org.example.model.scenario.Scenario;
import org.example.model.scenario.ScenarioStep;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RouterService {

    private final MessageService messageService;

    private final ScenarioService scenarioService;

    private final PayloadService payloadService;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public RouterService(MessageService messageService,
                         ScenarioService scenarioService,
                         PayloadService payloadService, KafkaTemplate<String, String> kafkaTemplate) {
        this.messageService = messageService;
        this.scenarioService = scenarioService;
        this.payloadService = payloadService;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Message message) {
        Scenario scenario = scenarioService.findById(message.getScenarioId());

        MessageStep lastStep = getLastStep(message);

        lastStep.setOutcome(message.getPayload());

        List<ScenarioStep> nextSteps = getNextSteps(lastStep, scenario);

        for (ScenarioStep nextStep : nextSteps) {
            Message clonedMessage = message.clone();

            fillHistory(clonedMessage, nextStep);

            clonedMessage.setPayload(payloadService.buildPayload(clonedMessage, nextStep));

            kafkaTemplate.send(nextStep.getName(), JsonUtil.toString(clonedMessage));
        }

    }

    private MessageStep getLastStep(Message message) {
        Iterator<Map.Entry<String, MessageStep>> iterator = message.getSteps().entrySet().iterator();

        Map.Entry<String, MessageStep> lastStep = null;

        while (iterator.hasNext()) {
            lastStep = iterator.next();
        }

        return lastStep.getValue();
    }

    private void fillHistory(Message message, ScenarioStep step) {
        message.getSteps().put(step.getId(), MessageStep.builder()
                .id(step.getId())
                .name(step.getName())
                .requestTimeStart(new Timestamp(System.currentTimeMillis()))
                .build());
    }

    private List<ScenarioStep> getNextSteps(MessageStep step, Scenario scenario) {
        ScenarioStep lastStep = scenario.getScenarioSteps().get(step.getId());

        return lastStep.getBranches().stream()
                .map(sb -> scenario.getScenarioSteps().get(sb.getStepId()))
                .collect(Collectors.toList());
    }

}
