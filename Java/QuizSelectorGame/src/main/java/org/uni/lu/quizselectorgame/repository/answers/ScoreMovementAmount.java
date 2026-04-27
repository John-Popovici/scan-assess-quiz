package org.uni.lu.quizselectorgame.repository.answers;

import org.uni.lu.quizselectorgame.enums.ScoreMovementType;

import java.util.Objects;

public class ScoreMovementAmount {
    private final ScoreMovementType scoreMovementType;
    private final int amount;

    public ScoreMovementAmount(ScoreMovementType scoreMovementType, int amount) {
        this.scoreMovementType = scoreMovementType;
        this.amount = amount;
    }

    public ScoreMovementType getScoreMovementType() {
        return scoreMovementType;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ScoreMovementAmount that = (ScoreMovementAmount) o;
        return getAmount() == that.getAmount() && getScoreMovementType() == that.getScoreMovementType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScoreMovementType(), getAmount());
    }
}
