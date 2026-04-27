package org.uni.lu.quizselectorgame.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import org.uni.lu.quizselectorgame.repository.ScoreChange;
import org.uni.lu.quizselectorgame.repository.SecurityScore;
import org.uni.lu.quizselectorgame.repository.questions.AbstractQuestion;
import org.uni.lu.quizselectorgame.repository.questions.QuestionRepository;

@Route
public class MainView extends VerticalLayout {
    private static final Boolean READ_JSON_DATA = true;
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
        VerticalLayout verticalLayout = new VerticalLayout(getProgressBarLayout(LumoIcon.EYE),
                getProgressBarLayout(LumoIcon.USER),
                getProgressBarLayout(LumoIcon.CHEVRON_LEFT),
                getProgressBarLayout(LumoIcon.BAR_CHART),
                getProgressBarLayout(LumoIcon.CHECKMARK),
                getProgressBarLayout(LumoIcon.BELL));
        return verticalLayout;
    }

    private HorizontalLayout getProgressBarLayout(LumoIcon icon) {
        ProgressBar catOneProgressBar = new ProgressBar(0, 100, 50);
        catOneProgressBar.setWidthFull();
        Icon catOneIcon = icon.create();
        HorizontalLayout horizontalLayout = new HorizontalLayout(catOneIcon, catOneProgressBar);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setWidthFull();
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
            switch (k) {
                case DECREASE -> v.forEach(securityScore::decreaseScore);
                case INCREASE -> v.forEach(securityScore::increaseScore);
            }
        });
    }
}
