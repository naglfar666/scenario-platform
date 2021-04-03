package org.example.builder.parser.bpmn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.*;
import org.example.model.scenario.Scenario;
import org.example.model.scenario.ScenarioStep;
import org.example.builder.parser.ScenarioParser;
import org.example.builder.parser.bpmn.step.StepBuilderFactory;
import org.example.builder.util.CamundaFlowUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Slf4j
@Component
public class BpmnParser implements ScenarioParser {

    private final StepBuilderFactory stepBuilderFactory;

    private BpmnModelInstance bpmnModelInstance;

    private Scenario scenario;

    private final Map<String, ScenarioStep> scenarioSteps = new HashMap<>();

    public BpmnParser(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Override
    public void parse(File file) {
        bpmnModelInstance = Bpmn.readModelFromFile(file);

        Process process = getProcess();

        log.info("BPMN scenario ID {}", process.getId());

        List<StartEvent> startEvents = getStartEvents();

        for (StartEvent startEvent : startEvents) {
            log.info("Start event ID {}", startEvent.getId());
            buildSteps(startEvent);
        }

        Scenario scenario = new Scenario(process.getId(), scenarioSteps);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writeValueAsString(scenario);
            log.info("Result of processing {}", result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Текущий процесс
     *
     * @return Process
     */
    private Process getProcess() {
        Collection<RootElement> rootElements = bpmnModelInstance.getDefinitions().getRootElements();
        for(RootElement rootElement : rootElements) {
            if (rootElement instanceof Process) {
                return (Process) rootElement;
            }
        }
        throw new RuntimeException("Process not found");
    }

    /**
     * Стартовое событие сценария
     *
     * @return StartEvent
     */
    private List<StartEvent> getStartEvents() {
        return (List<StartEvent>) bpmnModelInstance.getModelElementsByType(StartEvent.class);
    }

    private void buildSteps(FlowNode flowNode) {

        Collection<SequenceFlow> sequenceFlows = flowNode.getOutgoing();

        for (SequenceFlow sequenceFlow : sequenceFlows) {
            FlowNode flowNodeTarget = sequenceFlow.getTarget();
            buildSteps(flowNodeTarget);
        }

        ScenarioStep scenarioStep;

        if (scenarioSteps.get(flowNode.getId()) == null) {
            scenarioStep = stepBuilderFactory.create(flowNode).run(flowNode);

            scenarioStep.setId(flowNode.getId());
            scenarioStep.setName(flowNode.getName());
            scenarioStep.setInstanceId(UUID.randomUUID());
            scenarioStep.setBranches(CamundaFlowUtil.getBranchesForFlowNode(flowNode));
            scenarioStep.getParameters().putAll(CamundaFlowUtil.getParametersForFlowNode(flowNode));

            scenarioSteps.put(flowNode.getId(), scenarioStep);
        }

    }

}
