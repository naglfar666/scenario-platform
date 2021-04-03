package org.example.builder.parser.bpmn.step;

import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.example.model.scenario.ScenarioStep;

public interface StepBuilder {

    ScenarioStep run(FlowNode flowNode);

    boolean isRunnable(FlowNode flowNode);

}
