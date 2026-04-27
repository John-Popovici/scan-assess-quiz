package org.uni.lu.quizselectorgame.repository.questions;

public class AnswerJson {
    private Integer aIndex;
    private String aType;
    private Integer followUpQuestionIndex;
    private ScoreJson score;
    private String label;

    public Integer getaIndex() {
        return aIndex;
    }

    public void setaIndex(Integer aIndex) {
        this.aIndex = aIndex;
    }

    public String getaType() {
        return aType;
    }

    public void setaType(String aType) {
        this.aType = aType;
    }

    public Integer getFollowUpQuestionIndex() {
        return followUpQuestionIndex;
    }

    public void setFollowUpQuestionIndex(Integer followUpQuestionIndex) {
        this.followUpQuestionIndex = followUpQuestionIndex;
    }

    public ScoreJson getScore() {
        return score;
    }

    public void setScore(ScoreJson score) {
        this.score = score;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
