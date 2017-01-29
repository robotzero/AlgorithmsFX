package com.queen.sandbox.algorithms.models.quickfind;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuickFind {
//    private int[] container;
    private final Map<Person, Integer> dataContainer;

    public QuickFind() {
        this.dataContainer = IntStream.range(0, 10)
                .mapToObj(id -> {
                    int rectangleX = (id + 192) * id;
                    Rectangle rectangle = new Rectangle(100, 100, 100, 100);
                    rectangle.setStroke(Color.BLACK);
                    rectangle.setX(rectangleX);
                    rectangle.setY(200);
                    Circle circle = new Circle(10, 10, 10, Color.RED);
                    circle.setStroke(Color.BLACK);
                    circle.setVisible(false);
                    circle.setCenterX(rectangle.getX() + rectangle.getWidth() / 2);
                    circle.setCenterY(rectangle.getY() + rectangle.getHeight() / 2);
                    return new Person(
                            id,
                            Integer.toString(id),
                            rectangle,
                            new Color(0.5, 0.5, 0.5, 0),
                            circle);
                })
                .collect(Collectors.toMap(entry -> entry, Person::getId));
//        this.container = new int[10];
//        for (int i = 0; i < 10; i++) {
//            this.container[i] = i;
//        }
    }

    public boolean connected(Person p1, Person p2) {
        return dataContainer.get(p1).intValue() == dataContainer.get(p2).intValue();
//        return container[p] == container[q];
    }

    public void union(Person p1, Person p2) {

        int pid = dataContainer.get(p1);
        int qid = dataContainer.get(p2);

//        int pid = container[p];
//        int qid = container[q];

        this.dataContainer.forEach((person, id) -> {
            if (id == pid) {
                this.dataContainer.replace(person, qid);
            }
        });
//            if (container[i] == pid) {
//                container[i] = qid;
//            }
//        }
    }

    public Map<Person, Integer> getDatContainer() {
        return this.dataContainer;
    }
}
