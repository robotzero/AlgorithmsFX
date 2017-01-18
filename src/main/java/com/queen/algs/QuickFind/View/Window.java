package com.queen.algs.QuickFind.View;

import com.queen.algs.IWindow;
import com.queen.algs.QuickFind.Alg.QuickFind;
import com.queen.algs.QuickUnion.Person;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Map;

public class Window implements IWindow {

    private final Pane container;
    private final QuickFind quickFind = new QuickFind();
    private AnimationTimer circleFollowingTimer;
    private Circle circleFollowing = new Circle();
    private boolean pressingOnRectangle;

    public Window() {
        this.container = new Pane();
        this.container.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
        this.container.setVisible(false);
        this.circleFollowing.setFill(Color.DARKGREEN);
        this.circleFollowing.setRadius(10);
        this.draw();
    }

    public Pane getPane() {
        return this.container;
    }

    public boolean isVisible() {
        return this.container.isVisible();
    }

    public void draw() {
        Map<Integer, Person> dataContainer = quickFind.getDatContainer();
        dataContainer.entrySet().forEach(entry -> {
            Person person = entry.getValue();
            Text name = new Text();
            name.setX(person.getRectangle().getX());
            name.setY(person.getRectangle().getY() - 25);
            name.setText(person.getName());
            name.setFont(new Font(40));
            this.container.getChildren().addAll(person.getRectangle(), person.getCircle(), name);
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
                    person.getCircle().setVisible(false);
                }
            });

            person.getRectangle().setOnMousePressed(e -> this.pressingOnRectangle = true);
        });

        this.container.getChildren().add(this.circleFollowing);
        this.container.setOnMouseMoved(e -> {
            if (this.pressingOnRectangle) {
                if (!circleFollowing.isVisible()) {
                    circleFollowing.setVisible(true);
                }
                circleFollowing.setTranslateX(e.getX());
                circleFollowing.setTranslateY(e.getY());
            } else {
                circleFollowing.setVisible(false);
            }
        });
    }
}
