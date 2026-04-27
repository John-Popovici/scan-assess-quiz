package org.uni.lu.quizselectorgame.ui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import org.uni.lu.quizselectorgame.enums.ScoreType;
import org.uni.lu.quizselectorgame.repository.ScoreChange;
import org.uni.lu.quizselectorgame.repository.SecurityScore;
import org.uni.lu.quizselectorgame.repository.questions.AbstractQuestion;
import org.uni.lu.quizselectorgame.repository.questions.QuestionRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Route
public class MainView extends VerticalLayout {
    private static final Boolean READ_JSON_DATA = true;
    private final Map<ScoreType, ProgressBar> scoreTypeProgressBarMap = new HashMap<>();
    private final SecurityScore securityScore = new SecurityScore();
    private final QuestionRepository questionRepository = new QuestionRepository(READ_JSON_DATA);
    private final VerticalLayout questionLayout;

    public MainView() {
        setAlignItems(Alignment.CENTER);
        add(getSecurityScoreLayout());
        questionLayout = new VerticalLayout();
        add(questionLayout);
        addQuestion(questionRepository.getQuestionAtIndex(0));
    }

    private VerticalLayout getSecurityScoreLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Arrays.stream(ScoreType.values()).forEach(scoreType -> verticalLayout.add(getProgressBarLayout(scoreType)));
        return verticalLayout;
    }

    private HorizontalLayout getProgressBarLayout(ScoreType scoreType) {
        ProgressBar progressBar = new ProgressBar(0, 100, securityScore.getScore(scoreType));
        progressBar.setWidthFull();
        LumoIcon icon;
        switch (scoreType) {
            case USER -> icon = LumoIcon.USER;
            case PHYSICAL -> {
                icon = LumoIcon.COG;
                progressBar.getStyle().set("--vaadin-progress-bar-value-background", "yellow");
            }
            case ITEM -> {
                icon = LumoIcon.BELL;
                progressBar.getStyle().set("--vaadin-progress-bar-value-background", "orange");
            }
            case OTHER -> {
                icon = LumoIcon.BAR_CHART;
                progressBar.getStyle().set("--vaadin-progress-bar-value-background", "purple");
            }
            default -> icon = LumoIcon.ERROR;
        }
        Icon catOneIcon = icon.create();
        HorizontalLayout horizontalLayout = new HorizontalLayout(catOneIcon, progressBar);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setWidthFull();
        scoreTypeProgressBarMap.put(scoreType, progressBar);
        return horizontalLayout;
    }

    private void addQuestion(AbstractQuestion question) {
        questionLayout.removeAll();
        questionLayout.setAlignItems(Alignment.CENTER);
        questionLayout.add(new Text(question.getQuestion()));
        Card leftCard = new Card();
        Card rightCard = new Card();

        Button leftButton = new Button("<-", (ComponentEventListener<ClickEvent<Button>>) _ -> choseOptionOne(question));
        leftButton.addClickShortcut(Key.ARROW_LEFT);
        Button rightButton = new Button("->", (ComponentEventListener<ClickEvent<Button>>) _ -> choseOptionTwo(question));
        rightButton.addClickShortcut(Key.ARROW_RIGHT);

        leftCard.setTitle(question.getOptionOne());
        leftCard.add(leftButton);
        rightCard.setTitle(question.getOptionTwo());
        rightCard.add(rightButton);

        HorizontalLayout buttonLayout = new HorizontalLayout(leftCard, rightCard);
        questionLayout.add(buttonLayout);
    }


    private void choseOptionOne(AbstractQuestion question) {
        ScoreChange scoreChange = question.getOptionOneScoreChange();
        updateScores(scoreChange);
        if (question.hasFollowUpForOptionOne()) {
            addQuestion(question.getFollowUpQuestionOptionOne());
        }
    }

    private void choseOptionTwo(AbstractQuestion question) {
        ScoreChange scoreChange = question.getOptionTwoScoreChange();
        updateScores(scoreChange);
        if (question.hasFollowUpForOptionTwo()) {
            addQuestion(question.getFollowUpQuestionOptionTwo());
        }
    }

    private void updateScores(ScoreChange scoreChange) {
        scoreChange.getMovementType().forEach((k, v) -> {
            switch (k.getScoreMovementType()) {
                case DECREASE -> v.forEach(scoreType -> {
                    securityScore.decreaseScore(scoreType, k.getAmount());
                    int newAmount = securityScore.getScore(scoreType);
                    scoreTypeProgressBarMap.get(scoreType).setValue(newAmount);
                    if (newAmount <= 0) {
                        Dialog gameOverDialog = new Dialog("Game over!");
                        gameOverDialog.setModality(ModalityMode.MODELESS);
                        gameOverDialog.open();
                    }
                });
                case INCREASE -> v.forEach(scoreType -> {
                    securityScore.increaseScore(scoreType, k.getAmount());
                    scoreTypeProgressBarMap.get(scoreType).setValue(securityScore.getScore(scoreType));
                });

            }
        });
    }
}
