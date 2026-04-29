package org.uni.lu.quizselectorgame.enums;

public enum ScoreType {

    EMPLOYEE_MANAGEMENT("Employee Management", "EmployeeManagement"),
    LOGICAL_ACCESS("Logical Access", "LogicalAccess"),
    AWARENESS_AND_COMPLIANCE("Awareness And Compliance", "AwarenessAndCompliance"),
    INFORMATION_SYSTEM("Information System", "InformationSystem"),
    LOCAL_AREA_NETWORK("Local Area Network", "LocalAreaNetwork"),
    THIRD_PARTY_MANAGEMENT("Third Party Management", "ThirdPartyManagement");

    private final String humanValue;
    private final String propertyValue;

    ScoreType(String humanValue, String propertyValue) {
        this.humanValue = humanValue;
        this.propertyValue = propertyValue;
    }

    public String getHumanValue() {
        return humanValue;
    }

    public String getPropertyValue() {
        return propertyValue;
    }
}
