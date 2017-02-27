package com.queen.sandbox.algorithms.views.view;

import com.github.javafaker.Bool;
import com.queen.sandbox.algorithms.views.store.FriendsListStore;
import com.queen.sandbox.algorithms.views.store.Store;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.text.Text;

public class FriendsListView implements View {

    private final Node friendsListView;
    private String friendsList;
    private BooleanProperty visibility = new SimpleBooleanProperty(false);

    public FriendsListView(Node node) {
        this.friendsListView = node;
        this.friendsListView.visibleProperty().bind(visibility);
    }

    @Override
    public void storeChanged(Store store) {
        this.friendsList = ((FriendsListStore) store).getFriendsList();
        this.visibility.set(((FriendsListStore) store).isVisibility());
        render();
    }

    @Override
    public void render() {
        ((Text) friendsListView).setText(this.friendsList);
    }
}
