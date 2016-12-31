package com.queen.algs.QuickUnion;

import javafx.scene.layout.Pane;

public class Window extends Pane {

    private int[] container;

    public Window(int[] container) {
        this.container = container;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        container[i] = j;
    }

    /* chase parent pointers until reach root */
    private int root(int i) {
        while (i != container[i]) {
            i = container[i];
        }
        return i;
    }
}
