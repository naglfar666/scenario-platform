package org.example.router.service;

import lombok.extern.slf4j.Slf4j;
import org.example.common.util.JsonUtil;
import org.example.model.scenario.Scenario;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ScenarioService {

    String data = "{\"scenarioId\":\"SimpleScenario\",\"scenarioSteps\":{\"Event_13bwfmf\":{\"id\":\"Event_13bwfmf\",\"name\":null,\"instanceId\":\"ffa1fa7a-261f-489a-86de-a95192917d6c\",\"type\":\"END\",\"parameters\":{},\"branches\":[]},\"StartEvent_3\":{\"id\":\"StartEvent_3\",\"name\":null,\"instanceId\":\"25ce3def-284e-446d-840f-e662560cce6d\",\"type\":\"START_MESSAGE\",\"parameters\":{\"test1\":\"prop1\",\"test2\":\"prop2\"},\"branches\":[{\"stepId\":\"ExecutableTask_1\",\"expression\":null,\"defaultFlow\":true}]},\"Gateway_18tljzs\":{\"id\":\"Gateway_18tljzs\",\"name\":null,\"instanceId\":\"d2bfddc9-c2ae-40ce-9924-6c8dc2b46dec\",\"type\":\"EXCLUSIVE_GATEWAY\",\"parameters\":{},\"branches\":[{\"stepId\":\"Event_13bwfmf\",\"expression\":\"some expression\",\"defaultFlow\":false},{\"stepId\":\"Event_1mbwy37\",\"expression\":null,\"defaultFlow\":true}]},\"StartEvent_1\":{\"id\":\"StartEvent_1\",\"name\":null,\"instanceId\":\"cf76df41-32bf-4d2c-8c84-770c03a19ab8\",\"type\":\"START\",\"parameters\":{},\"branches\":[{\"stepId\":\"ExecutableTask_1\",\"expression\":null,\"defaultFlow\":true}]},\"ExecutableTask_1\":{\"id\":\"ExecutableTask_1\",\"name\":\"TaskExecutor\",\"instanceId\":\"59e9fe6c-09a0-4c2c-9ce2-a04b3e5efb34\",\"type\":\"TASK\",\"parameters\":{},\"branches\":[{\"stepId\":\"Gateway_18tljzs\",\"expression\":null,\"defaultFlow\":true}]},\"StartEvent_2\":{\"id\":\"StartEvent_2\",\"name\":null,\"instanceId\":\"d0120c15-da8f-4fe6-9a37-dbda62f1b4da\",\"type\":\"START_TIMER\",\"parameters\":{},\"branches\":[{\"stepId\":\"ExecutableTask_1\",\"expression\":null,\"defaultFlow\":true}]},\"Event_1mbwy37\":{\"id\":\"Event_1mbwy37\",\"name\":null,\"instanceId\":\"8bc040dc-2b40-4cf5-9062-10f26dd5fde5\",\"type\":\"END\",\"parameters\":{},\"branches\":[]}}}\n";

    Map<String, Scenario> scenarioMap = new HashMap<>();

    public ScenarioService() {
        Scenario scenario = JsonUtil.toObject(data, Scenario.class);

        scenarioMap.put(scenario.getScenarioId(), scenario);
    }

    public Scenario findById(String id) {
        return scenarioMap.get(id);
    }
}
