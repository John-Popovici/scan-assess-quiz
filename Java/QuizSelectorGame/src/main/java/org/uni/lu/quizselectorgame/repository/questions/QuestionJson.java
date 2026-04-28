package org.uni.lu.quizselectorgame.repository.questions;

import java.util.List;

public class QuestionJson {
    private Integer qIndex;
    private Integer treeIndex;
    private String label;
    private List<AnswerJson> answers;
    private List<Condition> conditions;

    public Integer getqIndex() {
        return qIndex;
    }

    public void setqIndex(Integer qIndex) {
        this.qIndex = qIndex;
    }

    public Integer getTreeIndex() {
        return treeIndex;
    }

    public void setTreeIndex(Integer treeIndex) {
        this.treeIndex = treeIndex;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<AnswerJson> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerJson> answers) {
        this.answers = answers;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
