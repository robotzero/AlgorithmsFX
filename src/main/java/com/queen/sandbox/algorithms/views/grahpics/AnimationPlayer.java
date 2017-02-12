package com.queen.sandbox.algorithms.views.grahpics;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationPlayer {

    private TranslateTransition translateTransition = new TranslateTransition();

    public void play(Node fromNode, Node toAnimate) {
        translateTransition.setFromX(toAnimate.getTranslateX());
        translateTransition.setFromY(toAnimate.getTranslateY());
        translateTransition.setToX(fromNode.getTranslateX() + 120);
        translateTransition.setToY(fromNode.getTranslateY() + 200);
        translateTransition.setDuration(Duration.millis(500));
        translateTransition.setNode(toAnimate);
        translateTransition.play();
    }
}
