package com.queen.algs.QuickFind;

import javafx.scene.layout.Pane;

public class Window extends Pane {

    private int[] container;

    public Window(int[] container) {
        this.container = container;
    }

    public boolean connected(int p, int q) {
        return container[p] == container[q];
    }

    public void union(int p, int q) {
        int pid = container[p];
        int qid = container[q];

        for(int i = 0; i < container.length; i++) {
            if (container[i] == pid) {
                container[i] = qid;
            }
        }
    }
}
