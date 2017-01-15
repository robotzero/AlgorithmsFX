package com.queen.algs.QuickUnion;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private int id;
    private String name;
    private Rectangle picture;
    private Color color;
    private Circle circle;
    private List<Integer> friends = new ArrayList<>();

    public Person(int id, String name, Rectangle picture, Color color, Circle circle) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.color = color;
        this.circle = circle;
    }

    public void addFriend(Integer id) {
        friends.add(id);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Rectangle getRectangle() {
        return this.picture;
    }

    public Color getColor() {
        return this.color;
    }

    public Circle getCircle() {
        return this.circle;
    }
}
