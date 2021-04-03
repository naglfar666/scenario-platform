package org.example.model.scenario;

import lombok.Data;

@Data
public class ScenarioBranch {

    private String      stepId;

    private String      expression;

    private boolean     defaultFlow = true;

}
