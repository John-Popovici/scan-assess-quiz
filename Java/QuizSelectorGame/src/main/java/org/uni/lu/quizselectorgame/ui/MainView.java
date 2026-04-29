package org.uni.lu.quizselectorgame.ui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import org.uni.lu.quizselectorgame.enums.ScoreType;
import org.uni.lu.quizselectorgame.repository.ScoreChange;
import org.uni.lu.quizselectorgame.repository.SecurityScore;
import org.uni.lu.quizselectorgame.repository.questions.Question;
import org.uni.lu.quizselectorgame.repository.questions.QuestionRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Route
public class MainView extends VerticalLayout {
    private static final Boolean GENERATE_QUESTIONS = false;
    private final Map<ScoreType, ProgressBar> scoreTypeProgressBarMap = new HashMap<>();
    private final SecurityScore securityScore = new SecurityScore();
    private final QuestionRepository questionRepository = new QuestionRepository(GENERATE_QUESTIONS);
    private final VerticalLayout questionLayout;

    public MainView() {
        setAlignItems(Alignment.CENTER);
        add(getSecurityScoreLayout());
        questionLayout = new VerticalLayout();
        add(questionLayout);
        Question firstQuestion = questionRepository.getFirstQuestion();
        if (firstQuestion != null) {
            addQuestion(firstQuestion);
        }
    }

    private VerticalLayout getSecurityScoreLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Arrays.stream(ScoreType.values()).forEach(scoreType -> verticalLayout.add(getProgressBarLayout(scoreType)));
        return verticalLayout;
    }

    private HorizontalLayout getProgressBarLayout(ScoreType scoreType) {
        ProgressBar progressBar = new ProgressBar(0, 100, securityScore.getScore(scoreType));
        progressBar.setWidth("250px");
        progressBar.getStyle().set("height", "12px");
        Icon icon;
        String color;

        switch (scoreType) {
            case EMPLOYEE_MANAGEMENT -> {
                icon = LumoIcon.USER.create();
                color = "#3b82f6";
            }
            case LOGICAL_ACCESS -> {
                icon = LumoIcon.COG.create();
                color = "#10b981";
            }
            case AWARENESS_AND_COMPLIANCE -> {
                icon = LumoIcon.BELL.create();
                color = "#f59e0b";
            }
            case INFORMATION_SYSTEM -> {
                icon = LumoIcon.PHONE.create();
                color = "#8b5cf6";
            }
            case LOCAL_AREA_NETWORK -> {
                icon = LumoIcon.PLAY.create();
                color = "#06b6d4";
            }
            case THIRD_PARTY_MANAGEMENT -> {
                icon = LumoIcon.SEARCH.create();
                color = "#ec4899";
            }
            default -> {
                icon = LumoIcon.ERROR.create();
                color = "#64748b";
            }
        }

        icon.setColor(color);
        // icon.setTooltipText(scoreType.name());
        progressBar.getStyle().set("--lumo-primary-color", color);
        progressBar.getStyle().set("--vaadin-progress-bar-value-background", color);

        Span label = new Span(scoreType.name());
        label.setWidth("250px");
        label.getStyle().set("text-align", "right");

        HorizontalLayout horizontalLayout = new HorizontalLayout(label, icon, progressBar);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        horizontalLayout.setWidthFull();

        horizontalLayout.setSpacing(true);

        scoreTypeProgressBarMap.put(scoreType, progressBar);
        return horizontalLayout;
    }

    private void addQuestion(Question question) {
        questionLayout.removeAll();
        questionLayout.setAlignItems(Alignment.CENTER);
        questionLayout.add(new Text(question.getQuestion()));
        Card leftCard = new Card();
        Card rightCard = new Card();
        leftCard.setWidth("280px");
        rightCard.setWidth("280px");
        leftCard.setHeight("140px");
        rightCard.setHeight("140px");

        Button leftButton = new Button("<-", (ComponentEventListener<ClickEvent<Button>>) _ -> choseOptionOne(question));
        leftButton.addClickShortcut(Key.ARROW_LEFT);
        Button rightButton = new Button("->", (ComponentEventListener<ClickEvent<Button>>) _ -> choseOptionTwo(question));
        rightButton.addClickShortcut(Key.ARROW_RIGHT);

        Span leftText = new Span(question.getOptionOne());
        Span rightText = new Span(question.getOptionTwo());

        VerticalLayout leftContent = new VerticalLayout(leftText, leftButton);
        leftContent.setSizeFull();
        leftContent.setPadding(false);
        leftContent.setSpacing(false);
        leftContent.setAlignItems(Alignment.CENTER);
        leftContent.setJustifyContentMode(JustifyContentMode.BETWEEN);

        VerticalLayout rightContent = new VerticalLayout(rightText, rightButton);
        rightContent.setSizeFull();
        rightContent.setPadding(false);
        rightContent.setSpacing(false);
        rightContent.setAlignItems(Alignment.CENTER);
        rightContent.setJustifyContentMode(JustifyContentMode.BETWEEN);

        leftCard.add(leftContent);
        rightCard.add(rightContent);

        HorizontalLayout buttonLayout = new HorizontalLayout(leftCard, rightCard);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setAlignItems(Alignment.CENTER);
        questionLayout.add(buttonLayout);
    }


    private void choseOptionOne(Question question) {
        ScoreChange scoreChange = question.getOptionOneScoreChange();
        updateScores(scoreChange);
        if (question.hasFollowUpForOptionOne()) {
            Integer followUpId = question.getFollowUpQuestionIdOptionOne();
            if (followUpId != null) {
                Question followUpQuestion = questionRepository.getQuestionById(followUpId);
                if (followUpQuestion != null) {
                    addQuestion(followUpQuestion);
                }
            }
        }
    }

    private void choseOptionTwo(Question question) {
        ScoreChange scoreChange = question.getOptionTwoScoreChange();
        updateScores(scoreChange);
        if (question.hasFollowUpForOptionTwo()) {
            Integer followUpId = question.getFollowUpQuestionIdOptionTwo();
            if (followUpId != null) {
                Question followUpQuestion = questionRepository.getQuestionById(followUpId);
                if (followUpQuestion != null) {
                    addQuestion(followUpQuestion);
                }
            }
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
