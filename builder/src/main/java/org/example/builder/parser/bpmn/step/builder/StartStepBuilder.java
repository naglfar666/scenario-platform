package org.example.builder.parser.bpmn.step.builder;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.instance.*;
import org.example.model.domain.ScenarioStepType;
import org.example.model.scenario.ScenarioStep;
import org.example.builder.parser.bpmn.step.StepBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class StartStepBuilder implements StepBuilder {

    @Override
    public ScenarioStep run(FlowNode flowNode) {
        StartEvent startEvent = (StartEvent) flowNode;

        ScenarioStepType stepType;

        Collection<EventDefinition> eventDefinitions = startEvent.getEventDefinitions();

        EventDefinition messageEventDefinition = eventDefinitions.stream()
                .filter(ed -> ed instanceof MessageEventDefinition)
                .findAny()
                .orElse(null);

        EventDefinition timerEventDefinition = eventDefinitions.stream()
                .filter(ed -> ed instanceof TimerEventDefinition)
                .findAny()
                .orElse(null);

        if (messageEventDefinition != null) {
            log.trace("Working on event {}", ScenarioStepType.START_MESSAGE);
            stepType = ScenarioStepType.START_MESSAGE;
        } else if (timerEventDefinition != null) {
            log.trace("Working on event {}", ScenarioStepType.START_TIMER);
            stepType = ScenarioStepType.START_TIMER;
        } else {
            log.trace("Working on event {}", ScenarioStepType.START);
            stepType = ScenarioStepType.START;
        }

        ScenarioStep step = new ScenarioStep();

        step.setType(stepType);

        return step;
    }

    @Override
    public boolean isRunnable(FlowNode flowNode) {
        return flowNode instanceof StartEvent;
    }

}
