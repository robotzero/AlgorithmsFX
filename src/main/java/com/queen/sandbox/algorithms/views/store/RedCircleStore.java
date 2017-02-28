package com.queen.sandbox.algorithms.views.store;

import com.queen.sandbox.algorithms.views.action.Action;
import com.queen.sandbox.algorithms.views.action.ActionType;
import com.queen.sandbox.algorithms.views.action.MouseEnteredAction;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;

public class RedCircleStore extends Store {

    private BooleanProperty visibility = new SimpleBooleanProperty(false);
    private Node redCircle;

    @Override
    public void onAction(Action action) {
        if (action.getType() == ActionType.MOUSE_ENTERED) {
            this.redCircle = ((MouseEnteredAction)action).getMouseEntered().getPerson().getCircle();
            this.visibility.set(true);
            notifyChange();
        }

        if (action.getType() == ActionType.MOUSE_EXITED) {
            notifyChange();
        }
    }

    public boolean isVisibility() {
        return visibility.get();
    }

    public BooleanProperty visibilityProperty() {
        return visibility;
    }

    public Node getRedCircle() {
        return redCircle;
    }
}
