package org.uni.lu.quizselectorgame.repository;

import org.uni.lu.quizselectorgame.enums.ScoreType;

public class SecurityScore {

    private int userScore = 50;
    private int physicalScore = 50;
    private int itemScore = 50;
    private int otherScore = 50;

    public int getUserScore() {
        return userScore;
    }

    public int getPhysicalScore() {
        return physicalScore;
    }

    public int getItemScore() {
        return itemScore;
    }

    public int getOtherScore() {
        return otherScore;
    }

    public void increaseScore(ScoreType scoreType) {
        switch (scoreType) {
            case USER -> userScore++;
            case PHYSICAL -> physicalScore++;
            case ITEM -> itemScore++;
            case OTHER -> otherScore++;
        }
    }

    public void decreaseScore(ScoreType scoreType) {
        switch (scoreType) {
            case USER -> userScore--;
            case PHYSICAL -> physicalScore--;
            case ITEM -> itemScore--;
            case OTHER -> otherScore--;
        }
    }
}
