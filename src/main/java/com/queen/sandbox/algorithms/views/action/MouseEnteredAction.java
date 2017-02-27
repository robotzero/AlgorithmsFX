package com.queen.sandbox.algorithms.views.action;

public class MouseEnteredAction extends Action {

    private final MouseEntered mouseEntered;

    public MouseEnteredAction(MouseEntered mouseEntered) {
        super(ActionType.MOUSE_ENTERED);
        this.mouseEntered = mouseEntered;
    }

    public MouseEntered getMouseEntered() {
        return mouseEntered;
    }
}
