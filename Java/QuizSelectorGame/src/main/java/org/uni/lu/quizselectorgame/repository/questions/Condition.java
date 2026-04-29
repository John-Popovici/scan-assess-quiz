package org.uni.lu.quizselectorgame.repository.questions;

public class Condition {
    private Integer treeIndex;
    private Integer qId;
    private Integer answerType;

    public Integer getTreeIndex() {
        return treeIndex;
    }

    public void setTreeIndex(Integer treeIndex) {
        this.treeIndex = treeIndex;
    }

    public Integer getQuestionId() {
        return qId;
    }

    public void setQuestionId(Integer qId) {
        this.qId = qId;
    }

    public Integer getAnswerType() {
        return answerType;
    }

    public void setAnswerType(Integer answerType) {
        this.answerType = answerType;
    }
}
