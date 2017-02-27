package com.queen.sandbox.algorithms.views.action;

public class MouseExitedAction extends Action {
    private final MouseExited mouseExited;

    public MouseExitedAction(MouseExited mouseExited) {
        super(ActionType.MOUSE_EXITED);
        this.mouseExited = mouseExited;
    }
}
