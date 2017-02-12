package com.queen.sandbox.algorithms.views.grahpics;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Random;

public class AnimationPlayer {

    private final Random random = new Random();
    private TranslateTransition translateTransition = new TranslateTransition();

    public void play(Node fromNode, Node toAnimate) {

        translateTransition.setFromX(toAnimate.getTranslateX());
        translateTransition.setFromY(toAnimate.getTranslateY());
        translateTransition.setToX(fromNode.getTranslateX() + random.nextInt(1920 - (int)fromNode.getTranslateX() - 100));
        translateTransition.setToY(fromNode.getTranslateY() + random.nextInt(1060 - (int)fromNode.getTranslateY() - 100));
        translateTransition.setDuration(Duration.millis(500));
        translateTransition.setNode(toAnimate);
        translateTransition.play();
    }
}
