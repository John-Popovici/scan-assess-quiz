package org.uni.lu.quizselectorgame.repository.questions;

import java.util.List;

public class Condition {
    private List<RequiredQuestion> requiredQuestions;

    public List<RequiredQuestion> getRequiredQuestions() {
        return requiredQuestions;
    }

    public void setRequiredQuestions(List<RequiredQuestion> requiredQuestions) {
        this.requiredQuestions = requiredQuestions;
    }
}
