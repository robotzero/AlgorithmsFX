package com.queen.sandbox.algorithms.models.quickfind;

import com.github.javafaker.Faker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuickFind {
//    private int[] container;
    private final Map<Person, Integer> dataContainer;
    private final Faker faker = new Faker();

    public QuickFind() {
        this.dataContainer = IntStream.range(0, 10)
                .mapToObj(id -> {
                    int rectangleX = (id + 192) * id;
                    Rectangle rectangle = new Rectangle(100, 100, 100, 100);
                    rectangle.setStroke(Color.BLACK);
                    rectangle.setX(0);
                    rectangle.setY(0);
                    Circle circle = new Circle(10, 10, 10, Color.RED);
                    circle.setStroke(Color.BLACK);
                    circle.setVisible(false);
                    rectangle.setTranslateX(rectangleX);
                    rectangle.setTranslateY(200);
                    circle.centerXProperty().bind(rectangle.translateXProperty().add(rectangle.widthProperty()).subtract(rectangle.widthProperty().divide(2)));
                    circle.centerYProperty().bind(rectangle.translateYProperty().add(rectangle.heightProperty()).subtract(rectangle.heightProperty().divide(2)));
                    Text name = new Text();
                    name.setX(0);
                    name.setY(0);
                    name.translateXProperty().bind(rectangle.translateXProperty().add(0));
                    name.translateYProperty().bind(rectangle.translateYProperty().subtract(20));
                    String nameString = faker.name().firstName();
                    name.setText(nameString);
                    name.setFont(new Font(15));
                    return new Person(
                            id,
                            nameString,
                            rectangle,
                            new Color(0.5, 0.5, 0.5, 0),
                            circle,
                            name);
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
