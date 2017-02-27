package com.queen.sandbox.algorithms.views.store;

import com.queen.sandbox.algorithms.views.action.Action;
import com.queen.sandbox.algorithms.views.view.View;

import java.util.LinkedList;
import java.util.List;

public abstract class Store {

    private List<View> views = new LinkedList<>();

    public abstract void onAction(Action action);

    public void registerView(View view) {
        views.add(view);
    }

    protected void notifyChange() {
        views.stream().forEach(view -> view.storeChanged(this));
    }
}
