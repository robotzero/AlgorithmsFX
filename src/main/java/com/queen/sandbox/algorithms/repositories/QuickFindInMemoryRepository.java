package com.queen.sandbox.algorithms.repositories;

import com.github.javafaker.Faker;
import com.queen.sandbox.algorithms.models.quickfind.Person;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class QuickFindInMemoryRepository implements Repository {

    private final Faker faker = new Faker();
    private final Map<Person, Integer> dataContainer = IntStream.range(0, 10)
            .mapToObj(id -> {
                int rectangleX = (id + 192) * id;
                Rectangle rectangle = new Rectangle(100, 100, 100, 100);
                rectangle.setId(Integer.toString(rectangleX));
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


    @Override
    public Supplier<Stream<Person>> searchPerson(Predicate<Person> searchFilter) {
        return () -> this.dataContainer.entrySet().stream().map(Map.Entry::getKey).filter(searchFilter);
    }

    @Override
    public Supplier<Stream<Person>> findAllPeople() {
        return () -> this.dataContainer.entrySet().stream().map(Map.Entry::getKey);
    }

    @Override
    public Integer getPersonConnectedToId(Person person) {
        return this.dataContainer.get(person);
    }

    @Override
    public void updatePersonConnection(int toUpdate, int newId) {
        this.dataContainer.forEach((person, id) -> {
            if (toUpdate == id) {
                this.dataContainer.replace(person, newId);
            }
        });
    }

    @Override
    public void resetAllConnections() {
        this.dataContainer.entrySet().forEach(entry -> entry.setValue(entry.getKey().getId()));
    }
}
