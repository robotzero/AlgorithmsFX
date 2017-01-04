package com.queen.algs.QuickFind.Alg;

public class QuickFind {
    private int[] container;

    public QuickFind() {
        this.container = new int[10];
        for (int i = 0; i < 10; i++) {
            this.container[i] = i;
        }
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
