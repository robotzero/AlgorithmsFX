package com.queen.sandbox.algorithms.views.action;

import com.queen.sandbox.algorithms.models.quickfind.Person;
import javafx.collections.ObservableSet;

public class MouseExited {
    private final Person person;
    private final ObservableSet<Integer> pressedRectangles;

    public MouseExited(Person person, ObservableSet<Integer> pressedRectangles) {
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
