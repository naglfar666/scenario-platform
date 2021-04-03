package org.example.builder.util;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.instance.ConditionExpression;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.example.model.scenario.ScenarioBranch;

import java.util.*;

public class CamundaFlowUtil {

    public static List<ScenarioBranch> getBranchesForFlowNode(FlowNode flowNode) {
        List<ScenarioBranch> branches = new ArrayList<>();
        Collection<SequenceFlow> sequenceFlows = flowNode.getOutgoing();

        for (SequenceFlow sequenceFlow : sequenceFlows) {

            ScenarioBranch scenarioBranch = new ScenarioBranch();

            scenarioBranch.setStepId(sequenceFlow.getTarget().getId());

            // Заполняем условие перехода на таргет
            ConditionExpression conditionExpression = sequenceFlow.getConditionExpression();

            if (conditionExpression != null) {
                String expression = sequenceFlow.getConditionExpression().getTextContent();
                if (!StringUtils.isEmpty(expression)) {
                    scenarioBranch.setExpression(expression);
                    scenarioBranch.setDefaultFlow(false);
                }
            }

            branches.add(scenarioBranch);
        }

        return branches;
    }

    public static Map<String, String> getParametersForFlowNode(FlowNode sourceFlowNode) {
        Map<String, String> parameters = new HashMap<>();
        ExtensionElements extensionElements = sourceFlowNode.getExtensionElements();

        if (extensionElements != null) {
            CamundaProperties camundaProperties = (CamundaProperties) extensionElements.getUniqueChildElementByType(CamundaProperties.class);

            if (camundaProperties != null) {
                Collection<CamundaProperty> extensions = camundaProperties.getCamundaProperties();

                for (CamundaProperty extension : extensions) {
                    parameters.put(extension.getCamundaName(), extension.getCamundaValue());
                }
            }

        }

        return parameters;
    }

}
