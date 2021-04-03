package org.example.model.domain;

public enum CrossServiceParameters {
    EXPRESSION("conditionExpression"),
    TIMER_DEFINITION("timerDefinition");

    private final String id;

    CrossServiceParameters(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
