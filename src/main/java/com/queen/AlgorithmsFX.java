package com.queen;

import com.queen.algs.IWindow;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class AlgorithmsFX extends Application {

    private final List<Button> buttons = new ArrayList<>();
    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane mainWindow = new GridPane();
        mainWindow.setGridLinesVisible(true);
        Pane playPanel = new StackPane();
        playPanel.autosize();
        playPanel.setBackground(new Background(new BackgroundFill(Color.WHEAT, CornerRadii.EMPTY, Insets.EMPTY)));
        playPanel.setMaxWidth(Double.MAX_VALUE);
        playPanel.setMaxHeight(Double.MAX_VALUE);

        VBox leftPanel = new VBox();
        Button quickFind = new Button("Quick Find");
        Button quickUnion = new Button("Quick Union");
        Button weightedQuickUnion = new Button("Weighted Quick Union");
        Collections.addAll(buttons, quickFind, quickUnion, weightedQuickUnion);
        leftPanel.getChildren().addAll(buttons);
        mainWindow.add(leftPanel, 0, 0);
        mainWindow.add(playPanel, 1, 0);
        mainWindow.setPrefWidth(WIDTH);
        GridPane.setHgrow(playPanel, Priority.ALWAYS);
        GridPane.setVgrow(playPanel, Priority.ALWAYS);
        GridPane.setHgrow(leftPanel, Priority.SOMETIMES);
        GridPane.setVgrow(leftPanel, Priority.SOMETIMES);
        mainWindow.setPrefHeight(HEIGHT);
        mainWindow.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(mainWindow, WIDTH, HEIGHT);
        Map<String, IWindow> windows = new HashMap<>();
        windows.put("QuickUnion", new com.queen.algs.QuickUnion.View.Window());
        windows.put("QuickFind", new com.queen.algs.QuickFind.View.Window());

        playPanel.getChildren().addAll(windows.get("QuickUnion").getPane(), windows.get("QuickFind").getPane());
        quickFind.setOnAction(event -> {
            if (!windows.get("QuickFind").getPane().isVisible()) {
                windows.get("QuickFind").getPane().setVisible(true);
                windows.get("QuickUnion").getPane().setVisible(false);
            }
        });

        quickUnion.setOnAction(event -> {
            if (!windows.get("QuickUnion").getPane().isVisible()) {
                windows.get("QuickFind").getPane().setVisible(false);
                windows.get("QuickUnion").getPane().setVisible(true);
            }
        });

        primaryStage.setX(2516);
        primaryStage.setY(100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
