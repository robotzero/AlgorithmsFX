package com.queen.sandbox.algorithms.views.action;

public class MousePressedAction extends Action {
    private final MousePressed mousePressed;

    public MousePressedAction(MousePressed mousePressed) {
        super(ActionType.MOUSE_PRESSED);
        this.mousePressed = mousePressed;
    }

    public MousePressed getMousePressed() {
        return mousePressed;
    }
}
