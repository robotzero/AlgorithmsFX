package com.queen.sandbox.algorithms.controllers;

import com.queen.sandbox.algorithms.models.quickfind.Person;
import com.queen.sandbox.algorithms.models.quickfind.QuickFind;
import com.queen.sandbox.algorithms.views.grahpics.AnimationPlayer;
import com.queen.sandbox.algorithms.views.grahpics.LineFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class QuickFindController implements Initializable {

    @FXML
    private Pane QFwindow;

    @FXML
    private Line initialConnectionLine;

    @FXML
    private Text friendsList;

    private MainContainerController mainContainerController;
    private final QuickFind quickFind = new QuickFind();
    private final LineFactory lineFactory = new LineFactory();
    private final AnimationPlayer animationPlayer = new AnimationPlayer();
    private List<Integer> pressedRectangles = new ArrayList<>();

    public void init(MainContainerController mainContainerController) {
        this.mainContainerController = mainContainerController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.pressedRectangles = new ArrayList<>();
        this.draw();
    }

    private void draw() {
        Map<Person, Integer> dataContainer = quickFind.getDatContainer();
        this.friendsList.setText("BALH");
        this.friendsList.setTranslateX(20);
        this.friendsList.setTranslateY(30);
        dataContainer.entrySet().forEach(entry -> {
            Person person = entry.getKey();
            this.QFwindow.getChildren().addAll(person.getRectangle(), person.getCircle(), person.getNameText());

            person.getRectangle().setOnMouseEntered(e -> {
                if (!person.getCircle().isVisible()) {
                    person.getCircle().setVisible(true);
                }

                if (!this.friendsList.isVisible()) {
                    this.friendsList.setVisible(true);
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
                    if (this.pressedRectangles.contains(person.getId())) {
                        person.getCircle().setVisible(false);
                    }
                    if (!person.isRectanglePressed()) {
                        person.getCircle().setVisible(false);
                    }
                }

                this.friendsList.setVisible(false);
            });

            person.getRectangle().setOnMousePressed(e -> {
                int numberOfPressedRectangles = this.pressedRectangles.size();
//                long numberOfPressedRectangles = currentListOfPressedRectangles(dataContainer.entrySet().stream()).get();
                if (this.pressedRectangles.contains(person.getId()) && numberOfPressedRectangles != 0) {
//                if (person.isRectanglePressed() && numberOfPressedRectangles != 0) {
                    //@TODO fix it!!!
                    this.pressedRectangles.remove(person.getId());
//                    person.setRectanglePressed(false);
                    person.getCircle().setVisible(false);
                } else {
                    if (numberOfPressedRectangles != 2) {
                        this.pressedRectangles.add(person.getId());
//                        person.setRectanglePressed(true);
                        this.pressedRectangles.stream()
                                .filter(personId -> person.getId() != personId)
                                .findFirst()
                                .ifPresent(personId -> {
                                    dataContainer.entrySet().stream().filter(entryset -> entryset.getValue().intValue() == personId).findFirst().ifPresent(toConnect -> {
                                        if (numberOfPressedRectangles == 1 && !this.quickFind.connected(person, toConnect.getKey())) {
                                            this.quickFind.union(person, toConnect.getKey());
                                            Rectangle rootRectangle = toConnect.getKey().getRectangle();
                                            Rectangle toAnimate = person.getRectangle();
                                            this.animationPlayer.play(
                                                    rootRectangle,
                                                    toAnimate
                                            );
                                            this.initialConnectionLine.setVisible(false);
                                            this.lineFactory.addNewConnectionLine(
                                                    person.getCircle().centerXProperty(),
                                                    person.getCircle().centerYProperty(),
                                                    toConnect.getKey().getCircle().centerXProperty(),
                                                    toConnect.getKey().getCircle().centerYProperty(),
                                                    this.QFwindow
                                            );
                                            this.pressedRectangles.clear();
//                                            this.quickFind.getDatContainer().forEach((p, id) -> {
//                                                p.setRectanglePressed(false);
//                                            });
                                        } else {
                                            if (numberOfPressedRectangles == 1) {
                                                this.initialConnectionLine.setVisible(false);
                                            }
                                        }
                                    });
//                                });
//                        dataContainer.entrySet().stream().filter(entryset -> entryset.getKey().isRectanglePressed())
//                                .filter(entryset -> entryset.getKey() != person)
//                                .findFirst().ifPresent(toConnect -> {
//                                    if (numberOfPressedRectangles == 1 && !this.quickFind.connected(person, toConnect.getKey())) {
//                                        this.quickFind.union(person, toConnect.getKey());
//                                        Rectangle rootRectangle = toConnect.getKey().getRectangle();
//                                        Rectangle toAnimate = person.getRectangle();
//                                        this.animationPlayer.play(
//                                            rootRectangle,
//                                            toAnimate
//                                        );
//                                        this.initialConnectionLine.setVisible(false);
//                                        this.lineFactory.addNewConnectionLine(
//                                            person.getCircle().centerXProperty(),
//                                            person.getCircle().centerYProperty(),
//                                            toConnect.getKey().getCircle().centerXProperty(),
//                                            toConnect.getKey().getCircle().centerYProperty(),
//                                            this.QFwindow
//                                        );
//                                        this.quickFind.getDatContainer().forEach((p, id) -> {
//                                            p.setRectanglePressed(false);
//                                        });
//                                    } else {
//                                        if (numberOfPressedRectangles == 1) {
//                                            this.initialConnectionLine.setVisible(false);
//                                        }
//                                    }
                                });
                            }
                        }
                    });
                    person.getCircle().setMouseTransparent(true);
                });

        this.QFwindow.setOnMouseMoved(e -> {
            int numberOfPressedRectangles = this.pressedRectangles.size();
//            long numberOfPressedRectangles = currentListOfPressedRectangles(dataContainer.entrySet().stream()).get();
            if (numberOfPressedRectangles == 1) {
                int pressedRectangleId = this.pressedRectangles.get(0);
                Circle circle = Optional.of(dataContainer.entrySet().stream()
                        .filter(entryset -> entryset.getValue() == pressedRectangleId).findFirst().get().getKey().getCircle()).get();
//                Circle circle = Optional.of(dataContainer.entrySet().stream()
//                        .filter(entryset -> entryset.getKey().isRectanglePressed())
//                        .findFirst().get()).get().getKey().getCircle();

                if (!this.initialConnectionLine.isVisible()) {
                    this.setInitialConnectionLinePref(line -> {
                        line.setVisible(true);
                        line.setStartX(circle.getCenterX());
                        line.setStartY(circle.getCenterY());
                    });
                }

                if (this.initialConnectionLine.getStartX() != circle.getCenterX()) {
                    this.setInitialConnectionLinePref(line -> {
                        line.setStartX(circle.getCenterX());
                        line.setStartY(circle.getCenterY());
                    });
                }

                setInitialConnectionLinePref(line -> {
                    line.setEndX(e.getX());
                    line.setEndY(e.getY());
                });
            }

            if (numberOfPressedRectangles == 0 && this.initialConnectionLine.isVisible()) {
                setInitialConnectionLinePref(line -> {
                    line.setVisible(false);
                    line.setStartX(0);
                    line.setStartY(0);
                    line.setEndX(0);
                    line.setEndY(0);
                });
            }
        });
    }

    private void setInitialConnectionLinePref(Consumer<Line> newSetup) {
            newSetup.accept(this.initialConnectionLine);
    }

    private Supplier<Long> currentListOfPressedRectangles(Stream<Map.Entry<Person, Integer>> currentStream) {
        return () -> currentStream.filter(entry -> entry.getKey().isRectanglePressed()).count();
    }
}
