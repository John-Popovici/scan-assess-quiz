package org.uni.lu.quizselectorgame.repository.answers;

import org.uni.lu.quizselectorgame.repository.questions.Question;

public class AnswerGiven {

    private final Question question;
    private final Integer optionChosen;

    public AnswerGiven(Question question, Integer optionChosen) {
        this.question = question;
        this.optionChosen = optionChosen;
    }

    public Question getQuestion() {
        return question;
    }

    public Integer getOptionChosen() {
        return optionChosen;
    }
}
