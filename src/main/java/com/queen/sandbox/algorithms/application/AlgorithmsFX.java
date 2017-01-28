package com.queen.sandbox.algorithms.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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
            primaryStage.setTitle("AlgorithmsFX");
            primaryStage.setX(2516);
            primaryStage.setY(100);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
