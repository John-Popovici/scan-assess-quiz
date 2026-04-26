package org.uni.lu.quizselectorgame.repository.questions;

import org.uni.lu.quizselectorgame.repository.ScoreChange;

public class ComputerQuestion extends AbstractQuestion {
    public ComputerQuestion(String question, String optionOne, String optionTwo) {
        super(question, optionOne, optionTwo);
    }

    public ComputerQuestion(String question, String optionOne, String optionTwo, ScoreChange optionOneScoreChange, ScoreChange optionTwoScoreChange) {
        super(question, optionOne, optionTwo, optionOneScoreChange, optionTwoScoreChange);
    }
}
