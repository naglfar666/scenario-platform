package org.example.model.scenario;

import lombok.Data;

import java.util.HashMap;
import org.example.model.domain.ScenarioStepType;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class ScenarioStep {

    private String                  id;

    private String                  name;

    private UUID                    instanceId;

    private ScenarioStepType        type;

    private Map<String, String>     parameters = new HashMap<>();

    private List<ScenarioBranch>    branches;

}
