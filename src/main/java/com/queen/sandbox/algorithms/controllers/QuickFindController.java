package com.queen.sandbox.algorithms.controllers;

import com.queen.sandbox.algorithms.models.quickfind.QuickFind;
import com.queen.sandbox.algorithms.repositories.QuickFindInMemoryRepository;
import com.queen.sandbox.algorithms.views.dispatch.Dispatcher;
import com.queen.sandbox.algorithms.views.graphics.AnimationPlayer;
import com.queen.sandbox.algorithms.views.graphics.LineObjectPool;
import com.queen.sandbox.algorithms.views.store.FriendsListStore;
import com.queen.sandbox.algorithms.views.store.RedCircleStore;
import com.queen.sandbox.algorithms.views.store.Store;
import com.queen.sandbox.algorithms.views.view.FriendsListView;
import com.queen.sandbox.algorithms.views.view.RedCircleView;
import com.queen.sandbox.algorithms.views.view.View;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class QuickFindController implements Initializable {

    @FXML
    private Pane QFwindow;

    @FXML
    private Line initialConnectionLine;

    @FXML
    private Text friendsList;

    @FXML
    private Button reset;

    @FXML
    private Text unionNotificationText;

    private MainContainerController mainContainerController;
    private final QuickFindInMemoryRepository repository = new QuickFindInMemoryRepository();
    private final QuickFind quickFind = new QuickFind(repository);
    private final LineObjectPool lineObjectPool = new LineObjectPool();
    private final AnimationPlayer animationPlayer = new AnimationPlayer();
    private Predicate<Node> isNodeVisible = Node::isVisible;
    private final ObservableSet<Integer> pressedRectangles = FXCollections.observableSet(new HashSet<>());
    private Font font = new Font(30);
    private Font unionNotificationFont = new Font(20);
    private IntegerProperty xLineDifference = new SimpleIntegerProperty();
    private IntegerProperty yLineDifference = new SimpleIntegerProperty();
    private final Rotate textRotateTransform = new Rotate();
    private Dispatcher dispatcher;
    private View friendsListView;
    private Store friendsListStore;
    private Store redCircleStore;
    private View redCircleView;

    public void init(MainContainerController mainContainerController) {
        this.mainContainerController = mainContainerController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dispatcher = Dispatcher.getInstance();
        this.friendsListStore = new FriendsListStore(this.repository, this.quickFind);
        this.friendsListView = new FriendsListView(this.friendsList);
        this.friendsListStore.registerView(this.friendsListView);
        this.redCircleStore = new RedCircleStore();
        this.redCircleView = new RedCircleView();
        this.redCircleStore.registerView(this.redCircleView);
        dispatcher.registerStore(this.friendsListStore);
        dispatcher.registerStore(this.redCircleStore);
        this.friendsListView.render();
        this.draw();
    }

    private void draw() {
        this.reset.setTranslateX(1820 - this.reset.getWidth());
        this.unionNotificationText.setFont(unionNotificationFont);
        this.unionNotificationText.yProperty().bind(this.initialConnectionLine.startYProperty().add(this.initialConnectionLine.endYProperty()).divide(2));
        this.unionNotificationText.xProperty().bind(this.initialConnectionLine.startXProperty().add(this.initialConnectionLine.endXProperty()).divide(2));
        this.xLineDifference.bind(this.initialConnectionLine.endXProperty().subtract(this.initialConnectionLine.startXProperty()));
        this.yLineDifference.bind(this.initialConnectionLine.endYProperty().subtract(this.initialConnectionLine.startYProperty()));
        textRotateTransform.setAngle(0);
        textRotateTransform.setPivotX(this.unionNotificationText.getX());
        textRotateTransform.setPivotY(this.unionNotificationText.getY());
        textRotateTransform.pivotXProperty().bind(this.unionNotificationText.xProperty());
        textRotateTransform.pivotYProperty().bind(this.unionNotificationText.yProperty());
        this.unionNotificationText.getTransforms().add(textRotateTransform);
        this.friendsList.setFont(this.font);
        this.friendsList.setTranslateX(20);
        this.friendsList.setTranslateY(30);
        this.repository.findAllPeople().get().forEach(person -> {
            this.QFwindow.getChildren().addAll(person.getRectangle(), person.getCircle(), person.getNameText());

            person.getRectangle().setOnMouseEntered(e -> {
                dispatcher.mouseEntered(person);

                if (isNodeVisible.test(this.initialConnectionLine)) {
                    this.repository.searchPerson(entryPerson -> entryPerson.getId() != person.getId())
                            .get()
                            .filter(entryPerson -> this.quickFind.connected(entryPerson, person))
                            .map(entryPerson -> entryPerson.getRectangle().getBoundsInParent())
                            .filter(rectangleBounds -> rectangleBounds.contains(new Point2D(this.initialConnectionLine.getStartX(), this.initialConnectionLine.getStartY())))
                            .findFirst().ifPresent(rectangle -> {
                                textRotateTransform.angleProperty().setValue(Math.toDegrees(Math.atan2(yLineDifference.doubleValue(), xLineDifference.doubleValue())));
                                if (!this.isNodeVisible.test(this.unionNotificationText)) {
                                    this.unionNotificationText.setVisible(true);
                                }
                    });
                }
            });

            person.getRectangle().setOnMouseExited(e -> {
                this.dispatcher.mouseExited(person);
                if (this.pressedRectangles.contains(person.getId()) && !isNodeVisible.test(person.getCircle())) {
                    person.getCircle().setVisible(true);
                } else {
                    boolean isPersonConnectedToAnyone = this.repository.searchPerson(
                            entryPerson -> entryPerson.getId() != person.getId())
                            .get()
                            .anyMatch(entryPerson -> quickFind.connected(person, entryPerson));
                    if (!this.pressedRectangles.contains(person.getId()) && !isPersonConnectedToAnyone) {
                        person.getCircle().setVisible(false);
                    }
                }

                if (this.isNodeVisible.test(this.unionNotificationText)) {
                    this.unionNotificationText.setVisible(false);
                }
            });

            person.getRectangle().setOnMousePressed(e -> {
                int numberOfPressedRectangles = this.pressedRectangles.size();
                if (this.pressedRectangles.contains(person.getId()) && (numberOfPressedRectangles % 2 != 0)) {
                    this.pressedRectangles.remove(person.getId());
                    person.getCircle().setVisible(false);
                } else {
                    this.pressedRectangles.add(person.getId());
                    this.pressedRectangles.stream()
                            .filter(personId -> person.getId() != personId)
                            .findFirst()
                            .ifPresent(personId -> {
                                this.repository.searchPerson(personToConnect -> personToConnect.getId() == personId).get().findFirst().ifPresent(personToConnect -> {
                                    if (!this.quickFind.connected(person, personToConnect)) {
                                        this.quickFind.union(person, personToConnect);
                                        Rectangle rootRectangle = personToConnect.getRectangle();
                                        Rectangle toAnimate = person.getRectangle();
                                        this.animationPlayer.play(
                                                rootRectangle,
                                                toAnimate
                                        );
                                        this.lineObjectPool.addNewConnectionLine(
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
            });
            person.getCircle().setMouseTransparent(true);
        });

        pressedRectangles.addListener((SetChangeListener<Integer>) c -> {
            int newSize = c.getSet().size();
            if (newSize != 0 || newSize % 2 != 0) {
                int pressedRectangleId = this.pressedRectangles.stream().findFirst().get();
                this.repository.searchPerson(person -> person.getId() == pressedRectangleId)
                        .get()
                        .findFirst()
                        .ifPresent(person -> {
                            Circle circle = person.getCircle();
                            if (!this.initialConnectionLine.isVisible()) {
                                this.setInitialConnectionLinePref(line -> {
                                    line.setStartX(circle.getCenterX());
                                    line.setStartY(circle.getCenterY());
                                    line.setEndX(circle.getCenterX());
                                    line.setEndY(circle.getCenterY());
                                    line.setVisible(true);
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
                this.unionNotificationText.setVisible(false);
            }
        });

        this.QFwindow.setOnMouseMoved(e -> {
            if (isNodeVisible.test(this.initialConnectionLine)) {
                setInitialConnectionLinePref(line -> {
                    line.setEndX(e.getX());
                    line.setEndY(e.getY());
                });

                if (this.isNodeVisible.test(this.unionNotificationText)) {
                    textRotateTransform.angleProperty().setValue(Math.toDegrees(Math.atan2(yLineDifference.doubleValue(), xLineDifference.doubleValue())));
                }
            }
        });

        this.reset.setOnMouseClicked(e -> {
            this.pressedRectangles.clear();
            this.initialConnectionLine.setVisible(false);
            this.unionNotificationText.setVisible(false);
            List<Line> linesToRemove =  this.QFwindow.getChildren().stream().filter(child -> child.getClass().equals(Line.class)).filter(line -> line.getId() == null).map(line -> (Line) line).collect(Collectors.toList());
            this.QFwindow.getChildren().removeAll(linesToRemove);
            this.lineObjectPool.removeNewConnectionLine(linesToRemove);
            this.QFwindow.getChildren().stream().filter(child -> child.getClass().equals(Circle.class)).forEach(circle -> circle.setVisible(false));
            this.QFwindow.getChildren().stream().filter(child -> child.getClass().equals(Rectangle.class)).forEach(rectangle -> {
                ((Rectangle)rectangle).setX(0);
                ((Rectangle)rectangle).setY(0);
                rectangle.setTranslateX(Integer.parseInt(rectangle.getId()));
                rectangle.setTranslateY(200);
            });
            this.repository.resetAllConnections();
        });
    }

    private void setInitialConnectionLinePref(Consumer<Line> newSetup) {
            newSetup.accept(this.initialConnectionLine);
    }
}
