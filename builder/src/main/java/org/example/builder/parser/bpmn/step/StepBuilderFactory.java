package org.example.builder.parser.bpmn.step;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StepBuilderFactory {

    private final List<StepBuilder> stepBuilders;

    public StepBuilderFactory(List<StepBuilder> stepBuilders) {
        this.stepBuilders = stepBuilders;
    }

    public StepBuilder create(FlowNode flowNode) {
        log.trace("Obtaining step builder for {}", flowNode.getId());
        return stepBuilders.stream()
                .filter(sb -> sb.isRunnable(flowNode))
                .findAny()
                .orElseThrow(() -> new NoSuchBeanDefinitionException("Not found builder for flow " + flowNode.getId()));
    }

}
