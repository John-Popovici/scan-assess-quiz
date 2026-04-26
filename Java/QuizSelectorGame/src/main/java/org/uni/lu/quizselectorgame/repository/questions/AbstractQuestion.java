package org.uni.lu.quizselectorgame.repository.questions;

import org.uni.lu.quizselectorgame.enums.QuestionOption;
import org.uni.lu.quizselectorgame.repository.ScoreChange;

import java.util.HashMap;

public abstract class AbstractQuestion {
    private final String question;
    private final String optionOne;
    private final String optionTwo;
    private final ScoreChange optionOneScoreChange;
    private final ScoreChange optionTwoScoreChange;
    private AbstractQuestion followUpQuestionOptionOne;
    private AbstractQuestion followUpQuestionOptionTwo;

    public AbstractQuestion(String question, String optionOne, String optionTwo, ScoreChange optionOneScoreChange, ScoreChange optionTwoScoreChange) {
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionOneScoreChange = optionOneScoreChange;
        this.optionTwoScoreChange = optionTwoScoreChange;
    }

    public AbstractQuestion(String question, String optionOne, String optionTwo) {
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

    public ScoreChange getOptionTwoScoreChange() {
        return optionTwoScoreChange;
    }

    public AbstractQuestion getFollowUpQuestionOptionOne() {
        return followUpQuestionOptionOne;
    }

    public void setFollowUpQuestionOptionOne(AbstractQuestion followUpQuestionOptionOne) {
        this.followUpQuestionOptionOne = followUpQuestionOptionOne;
    }

    public AbstractQuestion getFollowUpQuestionOptionTwo() {
        return followUpQuestionOptionTwo;
    }

    public void setFollowUpQuestionOptionTwo(AbstractQuestion followUpQuestionOptionTwo) {
        this.followUpQuestionOptionTwo = followUpQuestionOptionTwo;
    }

    public boolean hasFollowUpForOptionOne() {
        return this.followUpQuestionOptionOne != null;
    }

    public boolean hasFollowUpForOptionTwo() {
        return this.followUpQuestionOptionTwo != null;
    }
}
