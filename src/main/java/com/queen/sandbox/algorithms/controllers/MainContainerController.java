package com.queen.sandbox.algorithms.controllers;

import javafx.fxml.FXML;

public class MainContainerController {

    @FXML
    private QuickFindController quickFindController;
    @FXML
    private QuickUnionController quickUnionController;

    @FXML public void initialize() {
        System.out.println("Application started");
//        quickFindController.init(this);
//        quickUnionController.init(this);
    }
}
