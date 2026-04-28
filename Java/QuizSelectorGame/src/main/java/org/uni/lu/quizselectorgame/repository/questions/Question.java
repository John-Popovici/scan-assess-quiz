package org.uni.lu.quizselectorgame.repository.questions;

import org.uni.lu.quizselectorgame.enums.QuestionOption;
import org.uni.lu.quizselectorgame.repository.ScoreChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Question {

    private final int questionId;
    private final int treeId;
    private final String question;
    private final String optionOne;
    private final String optionTwo;
    private ScoreChange optionOneScoreChange;
    private ScoreChange optionTwoScoreChange;
    private Integer followUpQuestionOptionOne;
    private Integer followUpQuestionOptionTwo;
    private List<RequiredQuestion> requiredQuestions = new ArrayList<>();

    public Question(int questionId, int treeId, String question, String optionOne, String optionTwo, ScoreChange optionOneScoreChange, ScoreChange optionTwoScoreChange) {
        this.questionId = questionId;
        this.treeId = treeId;
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionOneScoreChange = optionOneScoreChange;
        this.optionTwoScoreChange = optionTwoScoreChange;
    }

    public Question(int questionId, int treeId, String question, String optionOne, String optionTwo) {
        this.questionId = questionId;
        this.treeId = treeId;
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionOneScoreChange = new ScoreChange(QuestionOption.OPTION_ONE, new HashMap<>());
        this.optionTwoScoreChange = new ScoreChange(QuestionOption.OPTION_TWO, new HashMap<>());
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getTreeId() {
        return treeId;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public ScoreChange getOptionOneScoreChange() {
        return optionOneScoreChange;
    }

    public void setOptionOneScoreChange(ScoreChange optionOneScoreChange) {
        this.optionOneScoreChange = optionOneScoreChange;
    }

    public ScoreChange getOptionTwoScoreChange() {
        return optionTwoScoreChange;
    }

    public void setOptionTwoScoreChange(ScoreChange optionTwoScoreChange) {
        this.optionTwoScoreChange = optionTwoScoreChange;
    }

    public Integer getFollowUpQuestionOptionOne() {
        return followUpQuestionOptionOne;
    }

    public void setFollowUpQuestionOptionOne(Integer followUpQuestionOptionOne) {
        this.followUpQuestionOptionOne = followUpQuestionOptionOne;
    }

    public Integer getFollowUpQuestionOptionTwo() {
        return followUpQuestionOptionTwo;
    }

    public void setFollowUpQuestionOptionTwo(Integer followUpQuestionOptionTwo) {
        this.followUpQuestionOptionTwo = followUpQuestionOptionTwo;
    }

    public boolean hasFollowUpForOptionOne() {
        return this.followUpQuestionOptionOne != null;
    }

    public boolean hasFollowUpForOptionTwo() {
        return this.followUpQuestionOptionTwo != null;
    }

    public List<RequiredQuestion> getRequiredQuestions() {
        return requiredQuestions;
    }

    public void setRequiredQuestions(List<RequiredQuestion> requiredQuestions) {
        this.requiredQuestions = requiredQuestions;
    }
}
