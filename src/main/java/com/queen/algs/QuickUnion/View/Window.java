package com.queen.algs.QuickUnion.View;

import com.queen.algs.IWindow;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Window implements IWindow {

    private Pane container;

    public Window() {
        this.container = new Pane();
        this.container.setVisible(false);
        this.container.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.container.setMaxHeight(Double.MAX_VALUE);
        this.container.setMaxWidth(Double.MAX_VALUE);
        this.container.getChildren().add(new Rectangle(20, 10, 20, 10));
    }

    public Pane getPane() {
        return this.container;
    }

    public boolean isVisible() {
        return this.container.isVisible();
    }

    public void setUpPane() {

    }
}
