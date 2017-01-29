package com.queen.sandbox.algorithms.controllers;

import com.queen.sandbox.algorithms.models.quickfind.Person;
import com.queen.sandbox.algorithms.models.quickfind.QuickFind;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class QuickFindController implements Initializable {

    @FXML
    private Pane QFwindow;

    @FXML
    private Line initialConnectionLine;

    private MainContainerController mainContainerController;
    private final QuickFind quickFind = new QuickFind();
    private final TranslateTransition translateTransition = new TranslateTransition();

    public void init(MainContainerController mainContainerController) {
        this.mainContainerController = mainContainerController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.draw();
    }

    private void draw() {
        Map<Person, Integer> dataContainer = quickFind.getDatContainer();
        this.QFwindow.autosize();
        dataContainer.entrySet().forEach(entry -> {
            Person person = entry.getKey();
            this.QFwindow.getChildren().addAll(person.getRectangle(), person.getCircle(), person.getNameText());

            person.getRectangle().setOnMouseEntered(e -> {
                if (!person.getCircle().isVisible()) {
                    person.getCircle().setVisible(true);
                }
            });

            person.getRectangle().setOnMouseExited(e -> {
                double rectangleX = e.getX();
                double rectangleY = e.getY();

                double centerX = person.getCircle().getCenterX();
                double centerY = person.getCircle().getCenterY();
                double radius = person.getCircle().getRadius();


                double distance = Math.pow(centerX - rectangleX, 2) + Math.pow(centerY - rectangleY, 2);

                if (!(distance < Math.pow(radius, 2))) {
                    if (!person.isRectanglePressed()) {
                        person.getCircle().setVisible(false);
                    }
                }
            });

            person.getRectangle().setOnMousePressed(e -> {
                long numberOfPressedRectangles = dataContainer.entrySet().stream()
                        .filter(entryset -> entryset.getKey().isRectanglePressed())
                        .count();

                if (person.isRectanglePressed() && numberOfPressedRectangles != 0) {
                    person.setRectanglePressed(false);
                    person.getCircle().setVisible(false);
                } else {
                    if (!person.isRectanglePressed() && numberOfPressedRectangles != 2) {
                        person.setRectanglePressed(true);
                        if (numberOfPressedRectangles == 1) {
                            Person toConnect = dataContainer.entrySet().stream().filter(entryset -> entryset.getKey().isRectanglePressed())
                                                                                .filter(entryset -> entryset.getKey() != person)
                                                                                .findFirst().get().getKey();
                            if (!this.quickFind.connected(person, toConnect)) {
                                this.quickFind.union(person, toConnect);
                                Rectangle rootRectangle = toConnect.getRectangle();
                                Rectangle toAnimate = person.getRectangle();
                                translateTransition.setFromX(toAnimate.getTranslateX());
                                translateTransition.setFromY(toAnimate.getTranslateY());
                                translateTransition.setToX(rootRectangle.getTranslateX() + 120);
                                translateTransition.setToY(rootRectangle.getTranslateY() + 200);
                                translateTransition.setDuration(Duration.millis(500));
                                translateTransition.setNode(toAnimate);
                                translateTransition.play();
                                this.initialConnectionLine.setVisible(false);
                                Line connectionLine = new Line();
                                connectionLine.setStartX(toConnect.getCircle().getCenterX());
                                connectionLine.setStartY(toConnect.getCircle().getCenterY());
                                connectionLine.setEndX(person.getCircle().getCenterX());
                                connectionLine.setEndY(person.getCircle().getCenterY());
                                this.QFwindow.getChildren().addAll(connectionLine);
                            }
                        }
                    }
                }
            });

            person.getCircle().setMouseTransparent(true);
        });

        this.QFwindow.setOnMouseMoved(e -> {
            long numberOfPressedRectangles = dataContainer.entrySet().stream()
                    .filter(entryset -> entryset.getKey().isRectanglePressed())
                    .count();

            if (numberOfPressedRectangles == 1) {
                Circle circle = Optional.of(dataContainer.entrySet().stream()
                        .filter(entryset -> entryset.getKey().isRectanglePressed())
                        .findFirst().get()).get().getKey().getCircle();

                if (!this.initialConnectionLine.isVisible()) {
                    this.initialConnectionLine.setVisible(true);
                    this.initialConnectionLine.setStartX(circle.getCenterX());
                    this.initialConnectionLine.setStartY(circle.getCenterY());
                }

                if (this.initialConnectionLine.getStartX() != circle.getCenterX()) {
                    this.initialConnectionLine.setStartX(circle.getCenterX());
                    this.initialConnectionLine.setStartY(circle.getCenterY());
                }

                this.initialConnectionLine.setEndX(e.getX());
                this.initialConnectionLine.setEndY(e.getY());
            }

            if (numberOfPressedRectangles == 0 && this.initialConnectionLine.isVisible()) {
                this.initialConnectionLine.setVisible(false);
                this.initialConnectionLine.setStartX(0);
                this.initialConnectionLine.setStartY(0);
                this.initialConnectionLine.setEndX(0);
                this.initialConnectionLine.setEndY(0);
            }
        });
    }
}
