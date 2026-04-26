package org.uni.lu.quizselectorgame;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private final SecurityScore securityScore = new SecurityScore();

    public MainView() {
        setAlignItems(Alignment.CENTER);

        add(new Text("You have a choice, do you go for option 1 or 2?"));
        Card leftCard = new Card();
        Card rightCard = new Card();

        Button leftButton = new Button("<-", (ComponentEventListener<ClickEvent<Button>>) _ -> choseOptionOne());
        Button rightButton = new Button("->", (ComponentEventListener<ClickEvent<Button>>) _ -> choseOptionTwo());

        leftCard.setTitle("Option 1");
        leftCard.add(leftButton);
        rightCard.setTitle("Option 2");
        rightCard.add(rightButton);

        HorizontalLayout buttonLayout = new HorizontalLayout(leftCard, rightCard);
        add(buttonLayout);
    }

    private void choseOptionOne() {

    }

    private void choseOptionTwo() {

    }
}
