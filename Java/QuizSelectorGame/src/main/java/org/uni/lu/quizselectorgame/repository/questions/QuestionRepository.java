package org.uni.lu.quizselectorgame.repository.questions;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Nullable;
import org.uni.lu.quizselectorgame.enums.QuestionOption;
import org.uni.lu.quizselectorgame.enums.ScoreMovementType;
import org.uni.lu.quizselectorgame.enums.ScoreType;
import org.uni.lu.quizselectorgame.repository.ScoreChange;
import org.uni.lu.quizselectorgame.repository.answers.AnswerGiven;
import org.uni.lu.quizselectorgame.repository.answers.ScoreMovementAmount;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionRepository {

    private final Logger logger = Logger.getLogger(QuestionRepository.class.getName());
    private final Map<Integer, List<Question>> questionMap = new HashMap<>();

    private final Map<Integer, List<AnswerGiven>> answerGivens = new HashMap<>();

    private Integer currentTreeId = 0;

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
            if (directory.exists() && directory.isDirectory()) {
                File[] jsonFiles = directory.listFiles((_, name) -> name.toLowerCase(Locale.ROOT).endsWith(".json"));
                if (jsonFiles != null) {
                    for (File jsonFile : jsonFiles) {
                        try {
                            String jsonRead = Files.readString(jsonFile.toPath());
                            Gson gson = new GsonBuilder()
                                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                    .create();
                            Type listType = new TypeToken<List<QuestionJson>>() {
                            }.getType();
                            List<QuestionJson> questionJsons = gson.fromJson(jsonRead, listType);

                            questionJsons.forEach(qj -> {
                                if (qj.getQuestionId() != null && qj.getAnswers() != null && qj.getAnswers().size() == 2) {
                                    AnswerJson answerOneJson = qj.getAnswers().getFirst();
                                    AnswerJson answerTwoJson = qj.getAnswers().getLast();
                                    Question question = new Question(qj.getQuestionId(), qj.getTreeId(), qj.getLabel(), answerOneJson.getLabel(), answerTwoJson.getLabel());
                                    if (answerOneJson.getScore() != null) {
                                        Map<ScoreMovementAmount, List<ScoreType>> scoreMovementAmountListMap = new HashMap<>();
                                        createScoreMovementMapping(answerOneJson, scoreMovementAmountListMap);
                                        ScoreChange scoreChange = new ScoreChange(QuestionOption.OPTION_ONE, scoreMovementAmountListMap);
                                        question.setOptionOneScoreChange(scoreChange);
                                    }
                                    if (answerTwoJson.getScore() != null) {
                                        Map<ScoreMovementAmount, List<ScoreType>> scoreMovementAmountListMap = new HashMap<>();
                                        createScoreMovementMapping(answerTwoJson, scoreMovementAmountListMap);
                                        ScoreChange scoreChange = new ScoreChange(QuestionOption.OPTION_TWO, scoreMovementAmountListMap);
                                        question.setOptionTwoScoreChange(scoreChange);
                                    }
                                    if (answerOneJson.getFollowUpQuestionId() != null) {
                                        question.setFollowUpQuestionOptionOne(answerOneJson.getFollowUpQuestionId());
                                    }

                                    if (qj.getConditions() != null && !qj.getConditions().isEmpty()) {
                                        qj.getConditions().forEach(condition -> {
                                            RequiredQuestion requiredQuestion = new RequiredQuestion(condition.getTreeIndex(), condition.getQuestionId(), condition.getAnswerType());
                                            question.getRequiredQuestions().add(requiredQuestion);
                                        });
                                    }

                                    questionMap.putIfAbsent(question.getTreeId(), new ArrayList<>());
                                    questionMap.get(question.getTreeId()).add(question);
                                } else {
                                    logger.log(Level.SEVERE, qj.getQuestionId() + " is an invalid question");
                                }
                            });
                            jsonDataRead = true;
                        } catch (IOException e) {
                            logger.log(Level.WARNING, e.getMessage());
                        }
                    }
                }
            }
            if (!jsonDataRead || questionMap.isEmpty()) {
                createDummyQuestions();
            }
        }
    }

    private void createScoreMovementMapping(AnswerJson answerOneJson, Map<ScoreMovementAmount, List<ScoreType>> scoreMovementAmountListMap) {
        ScoreJson scoreJson = answerOneJson.getScore();
        Method[] methods = scoreJson.getClass().getMethods();
        Arrays.asList(methods).forEach(m -> {
            try {
                String toCheck = m.getName().replace("get", "");
                Optional<ScoreType> optionalScoreType = Arrays.stream(ScoreType.values()).filter(t -> t.getPropertyValue().equalsIgnoreCase(toCheck)).findAny();
                if (optionalScoreType.isPresent()) {
                    ScoreType scoreType = optionalScoreType.get();
                    ScoreMovementAmount scoreMovementAmount = parseScoreValue((String) m.invoke(scoreJson));
                    scoreMovementAmountListMap.putIfAbsent(scoreMovementAmount, new ArrayList<>());
                    scoreMovementAmountListMap.get(scoreMovementAmount).add(scoreType);
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        });
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
        Map<ScoreMovementAmount, List<ScoreType>> optionOneScoreMovementType = new HashMap<>();
        ScoreMovementAmount scoreMovementAmount = new ScoreMovementAmount(ScoreMovementType.INCREASE, 2);
        ScoreMovementAmount scoreMovementAmountDecrease = new ScoreMovementAmount(ScoreMovementType.DECREASE, 1);
        optionOneScoreMovementType.put(scoreMovementAmount, Arrays.asList(ScoreType.LOGICAL_ACCESS, ScoreType.EMPLOYEE_MANAGEMENT));
        optionOneScoreMovementType.put(scoreMovementAmountDecrease, List.of(ScoreType.AWARENESS_AND_COMPLIANCE));
        ScoreChange optionOneScoreChange = new ScoreChange(QuestionOption.OPTION_ONE, optionOneScoreMovementType);
        Question hotelConnectQuestion = new Question(
                1,
                1,
                "You go to a hotel, do you connect to the WiFi?",
                "No, I don't trust them",
                "Yes, I see no issue",
                optionOneScoreChange,
                new ScoreChange(QuestionOption.OPTION_TWO, new HashMap<>()));

        Map<ScoreMovementAmount, List<ScoreType>> optionTwoScoreMovementType = new HashMap<>();
        optionTwoScoreMovementType.put(scoreMovementAmountDecrease, Arrays.asList(ScoreType.LOGICAL_ACCESS, ScoreType.EMPLOYEE_MANAGEMENT));
        ScoreChange optionTwoScoreChange = new ScoreChange(QuestionOption.OPTION_TWO, optionTwoScoreMovementType);
        hotelConnectQuestion.setFollowUpQuestionOptionTwo(2);
        Question hotelFollowUpQuestion = new Question(
                2,
                1,
                "Are you using a VPN?",
                "Yes",
                "No",
                new ScoreChange(QuestionOption.OPTION_ONE, new HashMap<>()),
                optionTwoScoreChange);
        questionMap.put(hotelConnectQuestion.getTreeId(), Arrays.asList(hotelConnectQuestion, hotelFollowUpQuestion));
    }

    public Question getQuestionWithIdAndTreeIdAndPop(int treeId, int questionId) {
        List<Question> questions = questionMap.get(treeId);
        Question question = questions.stream().filter(q -> q.getQuestionId() == questionId).findAny().orElse(null);
        if (question != null) {
            questions.remove(question);
            questionMap.put(treeId, questions);
        }
        return question;
    }

    public Question getNextAndPopQuestion() {
        if (questionMap.isEmpty()) {
            return null;
        }
        List<Question> questionList = questionMap.get(currentTreeId);
        if (questionList == null || questionList.isEmpty()) {
            currentTreeId = questionMap.keySet().stream().min(Integer::compareTo).orElse(null);
            if (currentTreeId == null) {
                return null;
            }
            questionList = questionMap.get(currentTreeId);
        }
        Question returnQuestion = questionList.stream().min(Comparator.comparingInt(Question::getQuestionId)).orElse(null);
        if (returnQuestion == null) {
            questionMap.remove(currentTreeId);
            return getNextAndPopQuestion();
        }

        //currentQuestionId = returnQuestion.getQuestionId();
        questionList.remove(returnQuestion);
        questionMap.put(returnQuestion.getTreeId(), questionList);
        if (!returnQuestion.getRequiredQuestions().isEmpty()) {
            if (answerGivens.isEmpty()) {
                return getNextAndPopQuestion();
            } else {
                for (RequiredQuestion requiredQuestion : returnQuestion.getRequiredQuestions()) {
                    List<AnswerGiven> answersToCheck = answerGivens.get(requiredQuestion.getTreeIndex());
                    if (answersToCheck == null || answersToCheck.isEmpty()) {
                        return getNextAndPopQuestion();
                    }
                    if (answersToCheck.stream().noneMatch(ac ->
                            ac.getQuestion().getQuestionId() == requiredQuestion.getQuestionIndex() &&
                                    Objects.equals(ac.getOptionChosen(), requiredQuestion.getAnswerOptionIndex()))) {
                        return getNextAndPopQuestion();
                    }
                }
            }
        }

        return returnQuestion;
    }

    public void updateAnswerGiven(Question question, Integer optionChosen) {
        answerGivens.putIfAbsent(question.getTreeId(), new ArrayList<>());
        answerGivens.get(question.getTreeId()).add(new AnswerGiven(question, optionChosen));
    }
}
