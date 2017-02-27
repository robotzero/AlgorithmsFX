package com.queen.sandbox.algorithms.views.view;

import com.queen.sandbox.algorithms.views.store.FriendsListStore;
import com.queen.sandbox.algorithms.views.store.Store;
import javafx.scene.Node;
import javafx.scene.text.Text;

public class FriendsListView implements View {

    private final Node friendsListView;
    private String friendsList;

    public FriendsListView(Node node) {
        this.friendsListView = node;
    }

    @Override
    public void storeChanged(Store store) {
        this.friendsList = ((FriendsListStore) store).getFriendsList();
        render();
    }

    @Override
    public void render() {
        ((Text) friendsListView).setText(this.friendsList);
        this.friendsListView.setVisible(true);
    }
}
