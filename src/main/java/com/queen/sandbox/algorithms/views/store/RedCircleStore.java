package com.queen.sandbox.algorithms.views.store;

import com.queen.sandbox.algorithms.models.quickfind.Person;
import com.queen.sandbox.algorithms.models.quickfind.QuickFind;
import com.queen.sandbox.algorithms.repositories.Repository;
import com.queen.sandbox.algorithms.views.action.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableSet;
import javafx.scene.Node;

public class RedCircleStore extends Store {

    private BooleanProperty visibility = new SimpleBooleanProperty(false);
    private Node redCircle;
    private final Repository repository;
    private final QuickFind quickFind;

    public RedCircleStore(Repository repository, QuickFind quickFind) {
        this.repository = repository;
        this.quickFind = quickFind;
    }

    @Override
    public void onAction(Action action) {
        if (action.getType() == ActionType.MOUSE_ENTERED) {
            this.redCircle = ((MouseEnteredAction)action).getMouseEntered().getPerson().getCircle();
            this.visibility.set(true);
            notifyChange();
        }

        if (action.getType() == ActionType.MOUSE_EXITED) {
            ObservableSet<Integer> pressedRectangles = ((MouseExitedAction)action).getMouseExited().getPressedRectangles();
            Person person = ((MouseExitedAction)action).getMouseExited().getPerson();
            if (pressedRectangles.contains(person.getId())) {
                this.visibility.set(true);
            } else {
                boolean isPersonConnectedToAnyone = this.repository.searchPerson(
                        entryPerson -> entryPerson.getId() != person.getId())
                        .get()
                        .anyMatch(entryPerson -> quickFind.connected(person, entryPerson));
                if (!pressedRectangles.contains(person.getId()) && !isPersonConnectedToAnyone) {
                    this.visibility.set(false);
                }
            }
            notifyChange();
        }

        if (action.getType() == ActionType.MOUSE_PRESSED) {
            ObservableSet<Integer> pressedRectangles = ((MousePressedAction)action).getMousePressed().getPressedRectangles();
            int numberOfPressedRectangles = pressedRectangles.size();
            Person person = ((MousePressedAction)action).getMousePressed().getPerson();
            if (pressedRectangles.contains(person.getId()) && (numberOfPressedRectangles % 2 != 0)) {
                this.visibility.set(false);
                notifyChange();
            }
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
