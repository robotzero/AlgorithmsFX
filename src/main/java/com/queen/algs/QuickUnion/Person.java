package com.queen.algs.QuickUnion;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private int id;
    private String name;
    private Rectangle picture;
    private Color color;
    private List<Integer> friends = new ArrayList<>();

    public Person(String name, Rectangle picture, Color color) {
        this.name = name;
        this.picture = picture;
        this.color = color;
    }

    public void addFriend(Integer id) {
        friends.add(id);
    }
}
