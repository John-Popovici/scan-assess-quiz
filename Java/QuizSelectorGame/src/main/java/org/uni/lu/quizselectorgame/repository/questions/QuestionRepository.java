package org.uni.lu.quizselectorgame.repository.questions;

import com.google.gson.Gson;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import jakarta.annotation.Nullable;
import org.uni.lu.quizselectorgame.enums.QuestionOption;
import org.uni.lu.quizselectorgame.enums.ScoreMovementType;
import org.uni.lu.quizselectorgame.enums.ScoreType;
import org.uni.lu.quizselectorgame.repository.ScoreChange;
import org.uni.lu.quizselectorgame.repository.answers.ScoreMovementAmount;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionRepository {

    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    private final Logger logger = Logger.getLogger(QuestionRepository.class.getName());
    private final Map<Integer, Question> questionById = new HashMap<>();

    public QuestionRepository() {
        this(false);
    }

    public QuestionRepository(boolean createDummyQuestion) {
        if (createDummyQuestion) {
            createDummyQuestions();
        } else {
            //read JSON data
            File directory = new File("Questions/");
            if (directory.exists() && directory.isDirectory()) {
                File[] jsonFiles = directory.listFiles((_, name) -> name.toLowerCase(Locale.ROOT).endsWith(".json"));
                if (jsonFiles != null) {
                    questionById.clear();

                    for (File jsonFile : jsonFiles) {
                        try {
                            String jsonRead = Files.readString(jsonFile.toPath());
                            Type listType = new TypeToken<List<QuestionJson>>() {
                            }.getType();
                            List<QuestionJson> questionJsons = GSON.fromJson(jsonRead, listType);

                            questionJsons.forEach(qj -> {
                                if (qj.getQuestionId() == null) {
                                    logger.log(Level.SEVERE, "Question without qIndex found in " + jsonFile.getName());
                                    return;
                                }

                                if (qj.getAnswers() == null || qj.getAnswers().size() != 2) {
                                    logger.log(Level.SEVERE, qj.getQuestionId() + " is an invalid question");
                                    return;
                                }

                                AnswerJson answerOneJson = qj.getAnswers().getFirst();
                                AnswerJson answerTwoJson = qj.getAnswers().getLast();
                                Question question = new Question(qj.getLabel(), answerOneJson.getLabel(), answerTwoJson.getLabel());
                                question.setOptionOneScoreChange(buildScoreChange(QuestionOption.OPTION_ONE, answerOneJson.getScore()));
                                question.setOptionTwoScoreChange(buildScoreChange(QuestionOption.OPTION_TWO, answerTwoJson.getScore()));
                                question.setFollowUpQuestionIdOptionOne(answerOneJson.getFollowUpQuestionId());
                                question.setFollowUpQuestionIdOptionTwo(answerTwoJson.getFollowUpQuestionId());

                                questionById.put(qj.getQuestionId(), question);
                            });

                        } catch (IOException e) {
                            logger.log(Level.WARNING, e.getMessage());
                        }
                    }
                }
            }

            if (questionById.isEmpty()) {
                logger.log(Level.SEVERE, "No questions were loaded from JSON");
                createDummyQuestions();
            }
        }
    }

    private ScoreChange buildScoreChange(QuestionOption option, ScoreJson scoreJson) {
        Map<ScoreMovementAmount, List<ScoreType>> scoreMovementAmountListMap = new HashMap<>();
        if (scoreJson == null) {
            return new ScoreChange(option, scoreMovementAmountListMap);
        }

        addScoreMovement(scoreMovementAmountListMap, scoreJson.getEmployeeManagement(), ScoreType.EMPLOYEE_MANAGEMENT);
        addScoreMovement(scoreMovementAmountListMap, scoreJson.getLogicalAccess(), ScoreType.LOGICAL_ACCESS);
        addScoreMovement(scoreMovementAmountListMap, scoreJson.getAwarenessAndCompliance(), ScoreType.AWARENESS_AND_COMPLIANCE);
        addScoreMovement(scoreMovementAmountListMap, scoreJson.getInformationSystem(), ScoreType.INFORMATION_SYSTEM);
        addScoreMovement(scoreMovementAmountListMap, scoreJson.getLocalAreaNetwork(), ScoreType.LOCAL_AREA_NETWORK);
        addScoreMovement(scoreMovementAmountListMap, scoreJson.getThirdPartyManagement(), ScoreType.THIRD_PARTY_MANAGEMENT);

        return new ScoreChange(option, scoreMovementAmountListMap);
    }

    private void addScoreMovement(Map<ScoreMovementAmount, List<ScoreType>> scoreMovementAmountListMap, String value, ScoreType scoreType) {
        ScoreMovementAmount scoreMovementAmount = parseScoreValue(value);
        if (scoreMovementAmount == null) {
            return;
        }
        scoreMovementAmountListMap.putIfAbsent(scoreMovementAmount, new ArrayList<>());
        scoreMovementAmountListMap.get(scoreMovementAmount).add(scoreType);
    }

    @Nullable
    private ScoreMovementAmount parseScoreValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        ScoreMovementType scoreMovementType;
        if (value.contains("-")) {
            scoreMovementType = ScoreMovementType.DECREASE;
            value = value.replace("-", "");
        } else {
            scoreMovementType = ScoreMovementType.INCREASE;
            value = value.replace("+", "");
        }

        try {
            int amount = Integer.parseInt(value.trim());
            if (amount == 0) {
                return null;
            }
            return new ScoreMovementAmount(scoreMovementType, amount);
        } catch (Exception e) {
            logger.log(Level.FINE, "Error parsing string " + value);
        }
        return null;
    }

    private void createDummyQuestions() {
        /*
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
        Question vpnQuestion = new Question(
                "Are you using a VPN?",
                "Yes",
                "No",
                new ScoreChange(QuestionOption.OPTION_ONE, new HashMap<>()),
            optionTwoScoreChange);
        hotelConnectQuestion.setFollowUpQuestionIdOptionTwo(2);

        questionById.clear();
        questionById.put(1, hotelConnectQuestion);
        questionById.put(2, vpnQuestion);
        */
    }

    public Question getQuestionById(int questionId) {
        return questionById.get(questionId);
    }

    public Question getFirstQuestion() {
        return questionById.keySet().stream().sorted().findFirst().map(questionById::get).orElse(null);
    }
}
