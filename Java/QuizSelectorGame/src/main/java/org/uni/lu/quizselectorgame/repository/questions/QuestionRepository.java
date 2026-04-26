package org.uni.lu.quizselectorgame.repository.questions;

import org.uni.lu.quizselectorgame.enums.QuestionOption;
import org.uni.lu.quizselectorgame.enums.ScoreMovementType;
import org.uni.lu.quizselectorgame.enums.ScoreType;
import org.uni.lu.quizselectorgame.repository.ScoreChange;

import java.util.*;

public class QuestionRepository {
    private final List<AbstractQuestion> questionList = new ArrayList<>();

    public QuestionRepository() {
        Map<ScoreMovementType, List<ScoreType>> optionOneScoreMovementType = new HashMap<>();
        optionOneScoreMovementType.put(ScoreMovementType.INCREASE, Arrays.asList(ScoreType.PHYSICAL, ScoreType.USER));
        optionOneScoreMovementType.put(ScoreMovementType.DECREASE, List.of(ScoreType.ITEM));
        ScoreChange optionOneScoreChange = new ScoreChange(QuestionOption.OPTION_ONE, optionOneScoreMovementType);
        AbstractQuestion hotelConnectQuestion = new ComputerQuestion(
                "You go to a hotel, do you connect to the WiFi?",
                "No, I don't trust them",
                "Yes, I see no issue",
                optionOneScoreChange,
                new ScoreChange(QuestionOption.OPTION_TWO, new HashMap<>()));

        Map<ScoreMovementType, List<ScoreType>> optionTwoScoreMovementType = new HashMap<>();
        optionTwoScoreMovementType.put(ScoreMovementType.DECREASE, Arrays.asList(ScoreType.PHYSICAL, ScoreType.USER));
        ScoreChange optionTwoScoreChange = new ScoreChange(QuestionOption.OPTION_TWO, optionTwoScoreMovementType);
        hotelConnectQuestion.setFollowUpQuestionOptionTwo(new ComputerQuestion(
                "Are you using a VPN?",
                "Yes",
                "No",
                new ScoreChange(QuestionOption.OPTION_ONE, new HashMap<>()),
                optionTwoScoreChange));

        questionList.add(hotelConnectQuestion);
    }

    public AbstractQuestion getQuestionAtIndex(int index) {
        return questionList.get(index);
    }
}
