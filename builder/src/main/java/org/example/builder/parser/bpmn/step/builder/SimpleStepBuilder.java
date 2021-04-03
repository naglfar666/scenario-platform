package org.example.builder.parser.bpmn.step.builder;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.example.model.domain.ScenarioStepType;
import org.example.model.scenario.ScenarioStep;
import org.example.builder.parser.bpmn.step.StepBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleStepBuilder implements StepBuilder {
    @Override
    public ScenarioStep run(FlowNode flowNode) {
        ScenarioStep step = new ScenarioStep();
        step.setType(ScenarioStepType.TASK);
        return step;
    }

    @Override
    public boolean isRunnable(FlowNode flowNode) {
        return flowNode instanceof Task;
    }
}
