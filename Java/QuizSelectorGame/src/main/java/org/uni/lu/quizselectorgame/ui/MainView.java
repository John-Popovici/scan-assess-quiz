package org.uni.lu.quizselectorgame.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.uni.lu.quizselectorgame.repository.ScoreChange;
import org.uni.lu.quizselectorgame.repository.SecurityScore;
import org.uni.lu.quizselectorgame.repository.questions.AbstractQuestion;
import org.uni.lu.quizselectorgame.repository.questions.QuestionRepository;

@Route
public class MainView extends VerticalLayout {

    private final SecurityScore securityScore = new SecurityScore();
    private final QuestionRepository questionRepository = new QuestionRepository();
    private final VerticalLayout questionLayout;

    public MainView() {
        setAlignItems(Alignment.CENTER);
        add(getSecurityScoreLayout());
        questionLayout = new VerticalLayout();
        add(questionLayout);
        addQuestion(questionRepository.getQuestionAtIndex(0));
    }

    private VerticalLayout getSecurityScoreLayout() {
        return new VerticalLayout();
    }

    private void addQuestion(AbstractQuestion question) {
        questionLayout.removeAll();
        questionLayout.setAlignItems(Alignment.CENTER);
        questionLayout.add(new Text(question.getQuestion()));
        Card leftCard = new Card();
        Card rightCard = new Card();

        Button leftButton = new Button("<-", (ComponentEventListener<ClickEvent<Button>>) _ -> choseOptionOne(question));
        Button rightButton = new Button("->", (ComponentEventListener<ClickEvent<Button>>) _ -> choseOptionTwo(question));

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
