package com.queen.sandbox.algorithms.views.action;

public class MouseMovedAction extends Action {
    private MouseMoved mouseMoved;

    public MouseMovedAction(MouseMoved mouseMoved) {
        super(ActionType.MOUSE_MOVED);
        this.mouseMoved = mouseMoved;
    }
}
