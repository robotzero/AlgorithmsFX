package com.queen.sandbox.algorithms.views.grahpics;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineFactory {

    public void addNewConnectionLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY, Node parentNode) {
        Line connectionLine = new Line();
        connectionLine.setStroke(Color.RED);
        connectionLine.startXProperty().bind(startX);
        connectionLine.startYProperty().bind(startY);
        connectionLine.endXProperty().bind(endX);
        connectionLine.endYProperty().bind(endY);
        ((Pane) parentNode).getChildren().addAll(connectionLine);
    }
}
