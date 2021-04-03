package org.example.model.scenario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scenario {

    private String scenarioId;

    private Map<String, ScenarioStep> scenarioSteps;
}
