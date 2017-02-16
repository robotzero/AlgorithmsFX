package com.queen.sandbox.algorithms.controllers;

import com.queen.sandbox.algorithms.models.quickfind.Person;
import com.queen.sandbox.algorithms.models.quickfind.QuickFind;
import com.queen.sandbox.algorithms.repositories.QuickFindInMemoryRepository;
import com.queen.sandbox.algorithms.views.grahpics.AnimationPlayer;
import com.queen.sandbox.algorithms.views.grahpics.LineFactory;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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

    @FXML
    private Button reset;

    private MainContainerController mainContainerController;
    private final QuickFindInMemoryRepository repository = new QuickFindInMemoryRepository();
    private final QuickFind quickFind = new QuickFind(repository);
    private final LineFactory lineFactory = new LineFactory();
    private final AnimationPlayer animationPlayer = new AnimationPlayer();
    private Predicate<Node> isNodeVisible = Node::isVisible;
    private final ObservableList<Integer> pressedRectangles = FXCollections.observableList(new ArrayList<>());
    private Font font = new Font(30);

    public void init(MainContainerController mainContainerController) {
        this.mainContainerController = mainContainerController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.draw();
    }

    private void draw() {
        this.reset.setTranslateX(1820 - this.reset.getWidth());
        this.reset.setTranslateY(10);
        this.friendsList.setText("Current connections: none");
        this.friendsList.setFont(this.font);
        this.friendsList.setTranslateX(20);
        this.friendsList.setTranslateY(30);
        this.repository.findAllPeople().get().forEach(person -> {
            this.QFwindow.getChildren().addAll(person.getRectangle(), person.getCircle(), person.getNameText());

            person.getRectangle().setOnMouseEntered(e -> {
                String friends = this.repository.searchPerson(
                        Map.Entry::getKey,
                        mapEntry -> mapEntry.getId() != person.getId())
                        .get()
                        .filter(entryPerson -> this.quickFind.connected(entryPerson, person))
                        .map(Person::getName)
                        .collect(StringBuilder::new,
                            (StringBuilder sb1, String s1) -> {
                                if (sb1.length() == 0) {
                                    sb1.append(s1);
                                } else {
                                    sb1.append(", ").append(s1);
                                }
                             },
                             StringBuilder::append)
                        .toString();

                if (!friends.isEmpty()) {
                    this.friendsList.setText("Current connections: " + " " + friends);
                } else {
                    this.friendsList.setText("Current connections: none");
                }

                if(!isNodeVisible.test(person.getCircle())) {
                    person.getCircle().setVisible(true);
                }

                if (!isNodeVisible.test(this.friendsList)) {
                    this.friendsList.setVisible(true);
                }
            });

            person.getRectangle().setOnMouseExited(e -> {
                if (this.pressedRectangles.contains(person.getId()) && !isNodeVisible.test(person.getCircle())) {
                    person.getCircle().setVisible(true);
                } else {
                    boolean isPersonConnectedToAnyone = this.repository.searchPerson(
                            Map.Entry::getKey,
                            mapEntry -> mapEntry.getId() != person.getId())
                            .get()
                            .anyMatch(mapEntry -> quickFind.connected(person, mapEntry));
                    if (!this.pressedRectangles.contains(person.getId()) && !isPersonConnectedToAnyone) {
                        person.getCircle().setVisible(false);
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
                                    this.repository.searchPerson(Map.Entry::getKey, personToConnect -> personToConnect.getId() == personId).get().findFirst().ifPresent(personToConnect -> {
                                        if (!this.quickFind.connected(person, personToConnect)) {
                                            this.quickFind.union(person, personToConnect);
                                            Rectangle rootRectangle = personToConnect.getRectangle();
                                            Rectangle toAnimate = person.getRectangle();
                                            this.animationPlayer.play(
                                                    rootRectangle,
                                                    toAnimate
                                            );
                                            this.lineFactory.addNewConnectionLine(
                                                    person.getCircle().centerXProperty(),
                                                    person.getCircle().centerYProperty(),
                                                    personToConnect.getCircle().centerXProperty(),
                                                    personToConnect.getCircle().centerYProperty(),
                                                    this.QFwindow
                                            );
                                            this.pressedRectangles.clear();
                                        }
                                    });
                                });
                            }
                        }
                    });
                    person.getCircle().setMouseTransparent(true);
                });

        pressedRectangles.addListener((ListChangeListener<Integer>) c -> {
            if (c.next()) {
                int newSize = c.getAddedSize();
                if (newSize != 0 || newSize % 2 != 0) {
                    int pressedRectangleId = this.pressedRectangles.get(0);
                    this.repository.searchPerson(Map.Entry::getKey, person -> person.getId() == pressedRectangleId)
                            .get()
                            .findFirst()
                            .ifPresent(person -> {
                                Circle circle = person.getCircle();
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
                            });
                }
                if (newSize == 0 && isNodeVisible.test(this.initialConnectionLine)) {
                    setInitialConnectionLinePref(line -> {
                        line.setVisible(false);
                        line.setStartX(0);
                        line.setStartY(0);
                        line.setEndX(0);
                        line.setEndY(0);
                    });
                }
            }
        });

        this.QFwindow.setOnMouseMoved(e -> {
            if (isNodeVisible.test(this.initialConnectionLine)) {
                setInitialConnectionLinePref(line -> {
                    line.setEndX(e.getX());
                    line.setEndY(e.getY());
                });
            }
        });
    }

    private void setInitialConnectionLinePref(Consumer<Line> newSetup) {
            newSetup.accept(this.initialConnectionLine);
    }
}
