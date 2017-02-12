package com.queen.sandbox.algorithms.controllers;

import com.queen.sandbox.algorithms.models.quickfind.Person;
import com.queen.sandbox.algorithms.models.quickfind.QuickFind;
import com.queen.sandbox.algorithms.views.grahpics.AnimationPlayer;
import com.queen.sandbox.algorithms.views.grahpics.LineFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
    private Predicate<Node> isNodeVisible = Node::isVisible;

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
                if(!isNodeVisible.test(person.getCircle())) {
                    person.getCircle().setVisible(true);
                }

                if (!isNodeVisible.test(this.friendsList)) {
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
                    if (this.pressedRectangles.contains(person.getId()) && !isNodeVisible.test(person.getCircle())) {
                        person.getCircle().setVisible(true);
                    } else {
                        boolean isPersonConnectedToAnyone = dataContainer.entrySet().stream()
                                .map(Map.Entry::getKey)
                                .filter(mapentry -> mapentry.getId() != person.getId())
                                .anyMatch(mapentry -> quickFind.connected(person, mapentry));
                        if (!isPersonConnectedToAnyone && this.pressedRectangles.size() != 1) {
                            person.getCircle().setVisible(false);
                        }
                    }
                }

                this.friendsList.setVisible(false);
            });

            person.getRectangle().setOnMousePressed(e -> {
                int numberOfPressedRectangles = this.pressedRectangles.size();
                if (this.pressedRectangles.contains(person.getId())) {
                    this.pressedRectangles.remove(new Integer(person.getId()));
                    person.getCircle().setVisible(false);
                } else {
                    if (numberOfPressedRectangles == 0 || numberOfPressedRectangles % 2 != 0) {
                        this.pressedRectangles.add(person.getId());
                        this.pressedRectangles.stream()
                                .filter(personId -> person.getId() != personId)
                                .findFirst()
                                .ifPresent(personId -> {
                                    dataContainer.entrySet().stream().filter(entryset -> entryset.getValue().intValue() == personId).findFirst().ifPresent(toConnect -> {
                                        if (!this.quickFind.connected(person, toConnect.getKey())) {
                                            this.quickFind.union(person, toConnect.getKey());
                                            Rectangle rootRectangle = toConnect.getKey().getRectangle();
                                            Rectangle toAnimate = person.getRectangle();
                                            this.animationPlayer.play(
                                                    rootRectangle,
                                                    toAnimate
                                            );
                                            this.lineFactory.addNewConnectionLine(
                                                    person.getCircle().centerXProperty(),
                                                    person.getCircle().centerYProperty(),
                                                    toConnect.getKey().getCircle().centerXProperty(),
                                                    toConnect.getKey().getCircle().centerYProperty(),
                                                    this.QFwindow
                                            );
                                            this.pressedRectangles.clear();
                                        }
                                    });
                                });
                            }
                        }
                        if (isNodeVisible.test(initialConnectionLine)) {
                            this.initialConnectionLine.setVisible(false);
                        }
                    });
                    person.getCircle().setMouseTransparent(true);
                });

        this.QFwindow.setOnMouseMoved(e -> {
            int numberOfPressedRectangles = this.pressedRectangles.size();
            if (numberOfPressedRectangles != 0 || numberOfPressedRectangles % 2 != 0) {
                //@ TODO change array to observable list.
                int pressedRectangleId = this.pressedRectangles.get(0);
                dataContainer.entrySet().stream()
                        .filter(entryset -> entryset.getKey().getId() == pressedRectangleId)
                        .findFirst()
                        .ifPresent(entry -> {
                            Circle circle = entry.getKey().getCircle();
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
                        });
            }

            if (numberOfPressedRectangles == 0 && isNodeVisible.test(this.initialConnectionLine)) {
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
}
