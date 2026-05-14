package org.uni.lu.quizselectorgame.repository.questions;

@SuppressWarnings("unused")
public class ConditionJson {
    private Integer treeId;
    private Integer q_id;
    private Integer a_id;

    public Integer getTreeId() {
        return treeId;
    }

    public void setTreeId(Integer treeId) {
        this.treeId = treeId;
    }

    public Integer getQuestionId() {
        return q_id;
    }

    public void setQuestionId(Integer questionId) {
        this.q_id = questionId;
    }

    public Integer getAnswerId() {
        return a_id;
    }

    public void setAnswerId(Integer answerType) {
        this.a_id = answerType;
    }
}
