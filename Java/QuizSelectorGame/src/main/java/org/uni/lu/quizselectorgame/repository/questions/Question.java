package org.uni.lu.quizselectorgame.repository.questions;

import org.uni.lu.quizselectorgame.enums.QuestionOption;
import org.uni.lu.quizselectorgame.repository.ScoreChange;

import java.util.HashMap;

public class Question {
    private final String question;
    private final String optionOne;
    private final String optionTwo;
    private ScoreChange optionOneScoreChange;
    private ScoreChange optionTwoScoreChange;
    private Integer followUpQuestionIdOptionOne;
    private Integer followUpQuestionIdOptionTwo;

    public Question(String question, String optionOne, String optionTwo, ScoreChange optionOneScoreChange, ScoreChange optionTwoScoreChange) {
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionOneScoreChange = optionOneScoreChange;
        this.optionTwoScoreChange = optionTwoScoreChange;
    }

    public Question(String question, String optionOne, String optionTwo) {
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionOneScoreChange = new ScoreChange(QuestionOption.OPTION_ONE, new HashMap<>());
        this.optionTwoScoreChange = new ScoreChange(QuestionOption.OPTION_TWO, new HashMap<>());
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

    public Integer getFollowUpQuestionIdOptionOne() {
        return followUpQuestionIdOptionOne;
    }

    public void setFollowUpQuestionIdOptionOne(Integer followUpQuestionIdOptionOne) {
        this.followUpQuestionIdOptionOne = followUpQuestionIdOptionOne;
    }

    public Integer getFollowUpQuestionIdOptionTwo() {
        return followUpQuestionIdOptionTwo;
    }

    public void setFollowUpQuestionIdOptionTwo(Integer followUpQuestionIdOptionTwo) {
        this.followUpQuestionIdOptionTwo = followUpQuestionIdOptionTwo;
    }

    public boolean hasFollowUpForOptionOne() {
        return this.followUpQuestionIdOptionOne != null;
    }

    public boolean hasFollowUpForOptionTwo() {
        return this.followUpQuestionIdOptionTwo != null;
    }
}
