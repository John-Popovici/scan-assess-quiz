package org.uni.lu.quizselectorgame.repository;

import org.uni.lu.quizselectorgame.enums.QuestionOption;
import org.uni.lu.quizselectorgame.enums.ScoreType;
import org.uni.lu.quizselectorgame.repository.answers.ScoreMovementAmount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreChange {
    private final QuestionOption questionOption;
    private final Map<ScoreMovementAmount, List<ScoreType>> movementType = new HashMap<>();

    public ScoreChange(QuestionOption questionOption, Map<ScoreMovementAmount, List<ScoreType>> movementType) {
        this.questionOption = questionOption;
        this.movementType.putAll(movementType);
    }

    public QuestionOption getQuestionOption() {
        return questionOption;
    }

    public Map<ScoreMovementAmount, List<ScoreType>> getMovementType() {
        return movementType;
    }
}
