package org.uni.lu.quizselectorgame.repository.questions;

@SuppressWarnings("unused")
public class ConditionJson {
    private Integer treeId;
    private Integer questionId;
    private Integer answerType;

    public Integer getTreeId() {
        return treeId;
    }

    public void setTreeId(Integer treeId) {
        this.treeId = treeId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswerType() {
        return answerType;
    }

    public void setAnswerType(Integer answerType) {
        this.answerType = answerType;
    }
}
