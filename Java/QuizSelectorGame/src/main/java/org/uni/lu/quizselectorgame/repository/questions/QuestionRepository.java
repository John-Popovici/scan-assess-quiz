package org.uni.lu.quizselectorgame.repository.questions;

import com.google.gson.Gson;
import org.uni.lu.quizselectorgame.enums.QuestionOption;
import org.uni.lu.quizselectorgame.enums.ScoreMovementType;
import org.uni.lu.quizselectorgame.enums.ScoreType;
import org.uni.lu.quizselectorgame.repository.ScoreChange;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionRepository {

    private final Logger logger = Logger.getLogger(QuestionRepository.class.getName());
    private final List<AbstractQuestion> questionList = new ArrayList<>();

    public QuestionRepository() {
        this(true);
    }

    public QuestionRepository(boolean createDummyQuestion) {
        if (createDummyQuestion) {
            createDummyQuestions();
        } else {
            //read JSON data
            boolean jsonDataRead = false;
            File directory = new File("Questions/");
            if (directory.exists()) {
                File[] jsonFiles = directory.listFiles((dir, name) -> name.toLowerCase(Locale.ROOT).endsWith("json"));
                if (jsonFiles != null) {
                    for (File jsonFile : jsonFiles) {
                        try {
                            String jsonRead = new String(Files.readAllBytes(jsonFile.toPath()));
                            Gson gson = new Gson();
                            QuestionJson questionJson = gson.fromJson(jsonRead, QuestionJson.class);
                            //Convert json into a question
                            jsonDataRead = true;
                        } catch (IOException e) {
                            logger.log(Level.WARNING, e.getMessage());
                        }
                    }
                }
            }
            if (!jsonDataRead) {
                createDummyQuestions();
            }
        }
    }

    private void createDummyQuestions() {
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
