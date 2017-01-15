package com.queen.algs.QuickFind.View;

import com.queen.algs.IWindow;
import com.queen.algs.QuickFind.Alg.QuickFind;
import com.queen.algs.QuickUnion.Person;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Map;

public class Window implements IWindow {

    private Pane container;
    private final QuickFind quickFind = new QuickFind();

    public Window() {
        this.container = new Pane();
        this.container.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
        this.container.setVisible(false);
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
            this.container.getChildren().addAll(person.getRectangle());
            this.container.getChildren().addAll(name);
        });
    }
}
