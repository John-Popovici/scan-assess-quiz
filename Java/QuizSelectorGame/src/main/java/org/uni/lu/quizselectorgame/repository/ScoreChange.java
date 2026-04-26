package org.uni.lu.quizselectorgame.repository;

import org.uni.lu.quizselectorgame.enums.QuestionOption;
import org.uni.lu.quizselectorgame.enums.ScoreMovementType;
import org.uni.lu.quizselectorgame.enums.ScoreType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreChange {
    private final QuestionOption questionOption;
    private final Map<ScoreMovementType, List<ScoreType>> movementType = new HashMap<>();

    public ScoreChange(QuestionOption questionOption, Map<ScoreMovementType, List<ScoreType>> movementType) {
        this.questionOption = questionOption;
        this.movementType.putAll(movementType);
    }

    public QuestionOption getQuestionOption() {
        return questionOption;
    }

    public Map<ScoreMovementType, List<ScoreType>> getMovementType() {
        return movementType;
    }
}
