package org.uni.lu.quizselectorgame.repository.questions;

public class RequiredQuestion {

    private final Integer treeIndex;
    private final Integer questionIndex;
    private final Integer answerOptionIndex;

    public RequiredQuestion(Integer treeIndex, Integer questionIndex, Integer answerOptionIndex) {
        this.treeIndex = treeIndex;
        this.questionIndex = questionIndex;
        this.answerOptionIndex = answerOptionIndex;
    }

    public Integer getTreeIndex() {
        return treeIndex;
    }

    public Integer getQuestionIndex() {
        return questionIndex;
    }

    public Integer getAnswerOptionIndex() {
        return answerOptionIndex;
    }
}
