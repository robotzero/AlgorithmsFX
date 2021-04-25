package com.robotzero.sandbox.algorithms.application;

import com.robotzero.sandbox.algorithms.models.quickunion.QuickUnion;

import java.util.Random;

public class Algorithms {

    public static void main(String[] args) {
        QuickUnion quickUnion = new QuickUnion();
        quickUnion.union(1, 2);
        quickUnion.union(2, 5);
        quickUnion.union(5, 4);
        boolean hi = quickUnion.connected(4, 2);
        int[][] arr = new int[2][2];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.println("i " + i + " j " + j);
                    arr[i][j]= new Random().nextInt(20);

            }
        }
    }
}
