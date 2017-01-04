package com.queen.algs.QuickUnion.Alg;

public class QuickUnion {

    private int[] container;

    public QuickUnion() {
        this.container = new int[10];
        for (int i = 0; i < 10; i++) {
            this.container[i] = i;
        }
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
