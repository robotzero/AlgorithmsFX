package com.queen.sandbox.algorithms.views.view;

import com.queen.sandbox.algorithms.views.store.RedCircleStore;
import com.queen.sandbox.algorithms.views.store.Store;
import javafx.scene.Node;

public class RedCircleView implements View {

    private Node redCircle;
    private boolean visibility;

    @Override
    public void storeChanged(Store store) {
        this.redCircle = ((RedCircleStore) store).getRedCircle();
        this.visibility = ((RedCircleStore) store).isVisibility();
        render();
    }

    @Override
    public void render() {
        redCircle.setVisible(visibility);
    }
}
