package org.uni.lu.quizselectorgame.repository.questions;

public class AnswerJson {
    private Integer aId;
    private String label;
    private Integer followUpQuestionId;
    private String recommendations;
    private ScoreJson score;

    public Integer getAnswerId() {
        return aId;
    }

    public void setAnswerId(Integer answerId) {
        this.aId = answerId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getFollowUpQuestionId() {
        return followUpQuestionId;
    }

    public void setFollowUpQuestionId(Integer followUpQuestionId) {
        this.followUpQuestionId = followUpQuestionId;
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
