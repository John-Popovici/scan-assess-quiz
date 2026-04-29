package org.uni.lu.quizselectorgame.repository.questions;

public class Condition {
    private Integer qId;
    private Integer answerType;

    public Integer getQuestionId() {
        return qId;
    }

    public void setQuestionId(Integer questionId) {
        this.qId = questionId;
    }

    public Integer getAnswerType() {
        return answerType;
    }

    public void setAnswerType(Integer answerType) {
        this.answerType = answerType;
    }
}
