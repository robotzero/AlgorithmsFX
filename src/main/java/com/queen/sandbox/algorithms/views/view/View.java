package com.queen.sandbox.algorithms.views.view;

import com.queen.sandbox.algorithms.views.store.Store;

public interface View {
    void storeChanged(Store store);
    void render();
}
