package com.queen.algs.Maze.WeighetQuickUnion;

import javafx.scene.layout.Pane;

public class Window extends Pane {

    private int[] container;
    private int[] weight;

    public Window(int[] container) {
        this.container = container;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (weight[i] < weight[j]) {
            container[i] = j;
            weight[j] += weight[i];
        } else {
            container[j] = i;
            weight[i] += weight[j];
        }
    }

    /* chase parent pointers until reach root */
    private int root(int i) {
        while (i != container[i]) {
            container[i] = container[container[i]];
            i = container[i];
        }
        return i;
    }
}
