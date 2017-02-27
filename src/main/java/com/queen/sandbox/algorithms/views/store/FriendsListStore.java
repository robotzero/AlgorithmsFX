package com.queen.sandbox.algorithms.views.store;

import com.queen.sandbox.algorithms.models.quickfind.Person;
import com.queen.sandbox.algorithms.models.quickfind.QuickFind;
import com.queen.sandbox.algorithms.repositories.Repository;
import com.queen.sandbox.algorithms.views.action.Action;
import com.queen.sandbox.algorithms.views.action.ActionType;
import com.queen.sandbox.algorithms.views.action.MouseEnteredAction;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.stream.Collectors;

public class FriendsListStore extends Store {

    private String friendsList = "Current connections: none";
    private BooleanProperty visibility = new SimpleBooleanProperty(false);
    private final Repository repository;
    private final QuickFind quickFind;

    public FriendsListStore(Repository repository, QuickFind quickFind) {
        this.repository = repository;
        this.quickFind = quickFind;
    }

    @Override
    public void onAction(Action action) {
        if (action.getType() == ActionType.MOUSE_ENTERED) {
            this.friendsList = this.getCurrentFriendsList(((MouseEnteredAction)action).getMouseEntered().getPerson() );
            if(!this.friendsList.equals("Current connections: .")) {
                this.visibility.set(true);
                notifyChange();
            }
        }

        if (action.getType() == ActionType.MOUSE_EXITED) {
            this.visibility.set(false);
            notifyChange();
        }
    }

    public String getFriendsList() {
        return friendsList;
    }

    public boolean isVisibility() {
        return visibility.get();
    }

    public BooleanProperty visibilityProperty() {
        return visibility;
    }

    private String getCurrentFriendsList(Person person) {
        return this.repository.searchPerson(
                entryPerson -> entryPerson.getId() != person.getId())
                .get()
                .filter(entryPerson -> this.quickFind.connected(entryPerson, person))
                .map(Person::getName)
                .collect(Collectors.joining(", ", "Current connections: ", "."));
    }
}
