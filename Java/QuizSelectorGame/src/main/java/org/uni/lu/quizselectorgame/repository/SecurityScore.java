package org.uni.lu.quizselectorgame.repository;

import org.uni.lu.quizselectorgame.enums.ScoreType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SecurityScore {

    private final Map<ScoreType, Integer> scoreTypeValues = new HashMap<>();

    public SecurityScore() {
        Arrays.stream(ScoreType.values()).forEach(value -> scoreTypeValues.put(value, 50));
    }

    public int getScore(ScoreType scoreType) {
        return scoreTypeValues.get(scoreType);
    }

    public void increaseScore(ScoreType scoreType, int value) {
        int newValue = (scoreTypeValues.get(scoreType) + value);
        if (newValue > 100) {
            newValue = 100;
        }
        scoreTypeValues.put(scoreType, newValue);
    }

    public void decreaseScore(ScoreType scoreType, int value) {
        int newValue = (scoreTypeValues.get(scoreType) - value);
        if (newValue < 0) {
            newValue = 0;
        }
        scoreTypeValues.put(scoreType, newValue);
    }
}
