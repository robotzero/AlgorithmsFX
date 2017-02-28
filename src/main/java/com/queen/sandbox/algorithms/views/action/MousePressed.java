package com.queen.sandbox.algorithms.views.action;

import com.queen.sandbox.algorithms.models.quickfind.Person;
import javafx.collections.ObservableSet;

public class MousePressed {
    private final Person person;
    private final ObservableSet<Integer> pressedRectangles;

    public MousePressed(Person person, ObservableSet<Integer> pressedRectangles) {
        this.person = person;
        this.pressedRectangles = pressedRectangles;
    }

    public Person getPerson() {
        return person;
    }

    public ObservableSet<Integer> getPressedRectangles() {
        return pressedRectangles;
    }
}
