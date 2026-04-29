package org.uni.lu.quizselectorgame.repository.questions;

import java.util.List;

@SuppressWarnings("unused")
public class QuestionJson {
    private Integer qId;
    private Integer treeId;
    private String label;
    private List<AnswerJson> answers;
    private List<ConditionJson> conditions;

    public Integer getQuestionId() {
        return qId;
    }

    public void setQuestionId(Integer questionId) {
        this.qId = questionId;
    }

    public Integer getTreeId() {
        return treeId;
    }

    public void setTreeId(Integer treeId) {
        this.treeId = treeId;
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

    public List<ConditionJson> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionJson> conditions) {
        this.conditions = conditions;
    }
}
