package com.queen.sandbox.algorithms.views.action;

import javafx.event.Event;

public class MouseMoved {

    private final Event mouseEvent;

    public MouseMoved(Event mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    public Event getMouseEvent() {
        return mouseEvent;
    }
}
