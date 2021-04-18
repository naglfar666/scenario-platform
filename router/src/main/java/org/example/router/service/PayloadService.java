package org.example.router.service;

import com.jayway.jsonpath.JsonPath;
import org.example.common.util.JsonUtil;
import org.example.model.message.Message;
import org.example.model.scenario.ScenarioStep;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PayloadService {

    public Map<String, Object> buildPayload(Message message, ScenarioStep step) {
        String requestData = JsonUtil.toString(message);
        Map<String, Object> result = new HashMap<>();

        step.getParameters().forEach((k, v) -> {
            if (k.contains("MAPPER_")) {
                String[] parameters = v.split("<=", 2);

                result.put(parameters[0], JsonPath.read(requestData, parameters[1]));
            }
        });

        return result;
    }

}
