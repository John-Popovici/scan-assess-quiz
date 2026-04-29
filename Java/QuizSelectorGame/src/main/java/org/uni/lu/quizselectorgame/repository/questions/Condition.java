package org.uni.lu.quizselectorgame.repository.questions;

public class Condition {
    private final Integer treeIndex;
    private final Integer qId;
    private final Integer answerType;

    public Condition(Integer treeIndex, Integer qId, Integer answerType) {
        this.treeIndex = treeIndex;
        this.qId = qId;
        this.answerType = answerType;
    }

    public Integer getTreeIndex() {
        return treeIndex;
    }

    public Integer getQuestionId() {
        return qId;
    }

    public Integer getAnswerType() {
        return answerType;
    }

}
