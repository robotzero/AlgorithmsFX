package com.queen.sandbox.algorithms.views.graphics;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Collection;

public class LineObjectPool {

    private final ObjectPool<Line> objectPool = new ObjectPool<Line>() {
        @Override
        protected Line create() {
            return new Line();
        }
    };
    public void addNewConnectionLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, Node parentNode) {
        Line connectionLine = this.objectPool.checkOut();
        connectionLine.setStroke(Color.RED);
        connectionLine.startXProperty().bind(startX);
        connectionLine.startYProperty().bind(startY);
        connectionLine.endXProperty().bind(endX);
        connectionLine.endYProperty().bind(endY);
        ((Pane) parentNode).getChildren().addAll(connectionLine);
    }

    public void removeNewConnectionLine(Collection<Line> lines) {
        lines.forEach(line -> {
            line.startXProperty().unbind();
            line.startYProperty().unbind();
            line.endXProperty().unbind();
            line.endYProperty().unbind();
            this.objectPool.checkIn(line);
        });
    }
}
