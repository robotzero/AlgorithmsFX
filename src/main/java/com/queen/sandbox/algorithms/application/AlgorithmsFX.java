package com.queen.sandbox.algorithms.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent mainContainer = FXMLLoader.load(getClass().getResource("../views/maincontainer.fxml"));
            Scene scene = new Scene(mainContainer);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

//        GridPane mainWindow = new GridPane();
//        mainWindow.setGridLinesVisible(true);
//        Pane playPanel = new StackPane();
//        playPanel.setOpaqueInsets(new Insets(10, 10, 10, 10));
//        playPanel.autosize();
//        playPanel.setBackground(new Background(new BackgroundFill(Color.WHEAT, CornerRadii.EMPTY, Insets.EMPTY)));
//        playPanel.setMaxWidth(Double.MAX_VALUE);
//        playPanel.setMaxHeight(Double.MAX_VALUE);
//
//        VBox leftPanel = new VBox();
//        Button quickFind = new Button("Quick Find");
//        Button quickUnion = new Button("Quick Union");
//        Button weightedQuickUnion = new Button("Weighted Quick Union");
//        Collections.addAll(buttons, quickFind, quickUnion, weightedQuickUnion);
//        leftPanel.getChildren().addAll(buttons);
//        mainWindow.add(leftPanel, 0, 0);
//        mainWindow.add(playPanel, 1, 0);
//        mainWindow.setPrefWidth(WIDTH);
//        GridPane.setHgrow(playPanel, Priority.ALWAYS);
//        GridPane.setVgrow(playPanel, Priority.ALWAYS);
//        GridPane.setHgrow(leftPanel, Priority.SOMETIMES);
//        GridPane.setVgrow(leftPanel, Priority.SOMETIMES);
//        mainWindow.setPrefHeight(HEIGHT);
//        mainWindow.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
//        Scene scene = new Scene(mainWindow, WIDTH, HEIGHT);
//        Map<String, IWindow> windows = new HashMap<>();

//
//        playPanel.getChildren().addAll(windows.get("QuickUnion").getPane(), windows.get("QuickFind").getPane());
//        quickFind.setOnAction(event -> {
//            if (!windows.get("QuickFind").getPane().isVisible()) {
//                windows.get("QuickFind").getPane().setVisible(true);
//                windows.get("QuickUnion").getPane().setVisible(false);
//            }
//        });
//
//        quickUnion.setOnAction(event -> {
//            if (!windows.get("QuickUnion").getPane().isVisible()) {
//                windows.get("QuickFind").getPane().setVisible(false);
//                windows.get("QuickUnion").getPane().setVisible(true);
//            }
//        });
//
//        primaryStage.setX(2516);
//        primaryStage.setY(100);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
}
