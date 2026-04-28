package org.uni.lu.quizselectorgame.repository.questions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.uni.lu.quizselectorgame.enums.QuestionOption;
import org.uni.lu.quizselectorgame.enums.ScoreMovementType;
import org.uni.lu.quizselectorgame.enums.ScoreType;
import org.uni.lu.quizselectorgame.repository.ScoreChange;
import org.uni.lu.quizselectorgame.repository.answers.ScoreMovementAmount;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionRepository {

    private final Logger logger = Logger.getLogger(QuestionRepository.class.getName());
    private final List<Question> questionList = new ArrayList<>();

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
                File[] jsonFiles = directory.listFiles((_, name) -> name.toLowerCase(Locale.ROOT).endsWith("json"));
                if (jsonFiles != null) {
                    for (File jsonFile : jsonFiles) {
                        try {
                            String jsonRead = new String(Files.readAllBytes(jsonFile.toPath()));
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<QuestionJson>>() {
                            }.getType();
                            List<QuestionJson> questionJsons = gson.fromJson(jsonRead, listType);

                            questionJsons.forEach(qj -> {
                                if (qj.getAnswers() != null && qj.getAnswers().size() == 2) {
                                    Question question = new Question(qj.getLabel(), qj.getAnswers().getFirst().getLabel(), qj.getAnswers().getLast().getLabel());
                                    questionList.add(question);
                                } else {
                                    logger.log(Level.SEVERE, qj.getqIndex() + " is an invalid question");
                                }
                            });
                            jsonDataRead = true;
                        } catch (IOException e) {
                            logger.log(Level.WARNING, e.getMessage());
                        }
                    }
                }
            }
            if (!jsonDataRead || questionList.isEmpty()) {
                createDummyQuestions();
            }
        }
    }

    private void createDummyQuestions() {
        Map<ScoreMovementAmount, List<ScoreType>> optionOneScoreMovementType = new HashMap<>();
        ScoreMovementAmount scoreMovementAmount = new ScoreMovementAmount(ScoreMovementType.INCREASE, 2);
        ScoreMovementAmount scoreMovementAmountDecrease = new ScoreMovementAmount(ScoreMovementType.DECREASE, 1);
        optionOneScoreMovementType.put(scoreMovementAmount, Arrays.asList(ScoreType.LOGICAL_ACCESS, ScoreType.EMPLOYEE_MANAGEMENT));
        optionOneScoreMovementType.put(scoreMovementAmountDecrease, List.of(ScoreType.AWARENESS_AND_COMPLIANCE));
        ScoreChange optionOneScoreChange = new ScoreChange(QuestionOption.OPTION_ONE, optionOneScoreMovementType);
        Question hotelConnectQuestion = new Question(
                "You go to a hotel, do you connect to the WiFi?",
                "No, I don't trust them",
                "Yes, I see no issue",
                optionOneScoreChange,
                new ScoreChange(QuestionOption.OPTION_TWO, new HashMap<>()));

        Map<ScoreMovementAmount, List<ScoreType>> optionTwoScoreMovementType = new HashMap<>();
        optionTwoScoreMovementType.put(scoreMovementAmountDecrease, Arrays.asList(ScoreType.LOGICAL_ACCESS, ScoreType.EMPLOYEE_MANAGEMENT));
        ScoreChange optionTwoScoreChange = new ScoreChange(QuestionOption.OPTION_TWO, optionTwoScoreMovementType);
        hotelConnectQuestion.setFollowUpQuestionOptionTwo(new Question(
                "Are you using a VPN?",
                "Yes",
                "No",
                new ScoreChange(QuestionOption.OPTION_ONE, new HashMap<>()),
                optionTwoScoreChange));

        questionList.add(hotelConnectQuestion);
    }

    public Question getQuestionAtIndex(int index) {
        return questionList.get(index);
    }
}
