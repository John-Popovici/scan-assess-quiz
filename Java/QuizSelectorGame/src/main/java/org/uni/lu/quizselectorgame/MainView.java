package org.uni.lu.quizselectorgame;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    public MainView() {


        add(new Text("You have a choice, do you go for option 1 or 2?"));
        Button leftButton = new Button("Select option 1", (ComponentEventListener<ClickEvent<Button>>) _ -> choseOptionOne());
        Button rightButton = new Button("Select option 2", (ComponentEventListener<ClickEvent<Button>>) _ -> choseOptionTwo());
        HorizontalLayout buttonLayout = new HorizontalLayout(leftButton, rightButton);
        add(buttonLayout);
    }

    private void choseOptionOne() {

    }

    private void choseOptionTwo() {

    }
}
