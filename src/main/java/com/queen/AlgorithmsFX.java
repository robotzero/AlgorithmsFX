package com.queen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgorithmsFX extends Application {

    private final List<Button> buttons = new ArrayList<>();
    private final int WIDTH = 1024;
    private final int HEIGHT = 768;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        int[] container = new int[10];
        for (int i = 0; i < 10; i++) {
            container[i] = i;
        }
        GridPane mainWindow = new GridPane();
        VBox leftPanel = new VBox();
        Button quickFind = new Button("Quick Find");
        Button quickUnion = new Button("Quick Union");
        Button weightedQuickUnion = new Button("Weighted Quick  Union");
        Collections.addAll(buttons, quickFind, quickUnion, weightedQuickUnion);
        leftPanel.getChildren().addAll(buttons);
        mainWindow.add(leftPanel, 1, 1);
        mainWindow.setPrefWidth(WIDTH);
        mainWindow.setPrefHeight(HEIGHT);
        mainWindow.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(mainWindow, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
