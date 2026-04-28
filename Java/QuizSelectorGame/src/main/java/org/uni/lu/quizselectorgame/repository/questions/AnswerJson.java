package org.uni.lu.quizselectorgame.repository.questions;

public class AnswerJson {
    private Integer aIndex;
    private String label;
    private Integer followUpQuestionIndex;
    private String recommendations;
    private ScoreJson score;

    public Integer getaIndex() {
        return aIndex;
    }

    public void setaIndex(Integer aIndex) {
        this.aIndex = aIndex;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getFollowUpQuestionIndex() {
        return followUpQuestionIndex;
    }

    public void setFollowUpQuestionIndex(Integer followUpQuestionIndex) {
        this.followUpQuestionIndex = followUpQuestionIndex;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public ScoreJson getScore() {
        return score;
    }

    public void setScore(ScoreJson score) {
        this.score = score;
    }
}
