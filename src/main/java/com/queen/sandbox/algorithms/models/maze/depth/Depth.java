package com.queen.sandbox.algorithms.models.maze.depth;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Depth extends Pane {

    public static final int w = 80;
    public static int COLLS;
    public static int ROWS;
    private List<Cell> grid = new ArrayList<>();
    private Cell current;
    private Timeline timeline = new Timeline();
    private boolean running = false;

    public void setUp() {
        COLLS = (int) Math.floor(this.getWidth() / w);
        ROWS = (int) Math.floor(this.getHeight() / w);

        for (int j = 0; j < ROWS; j++) {
            for (int i = 0; i < COLLS; i++) {
                Cell cell = new Cell(i, j, w);
                grid.add(cell);
            }
        }

        this.current = grid.get(0);
        this.current.setVisited(true);
        this.start();
    }

    private void start() {
        this.running = true;
        KeyFrame frame = new KeyFrame(Duration.seconds(0.5), event -> {
            if (!running) {
                return;
            }

//            Random random = new Random();
//            int value = random.nextInt(20);
//            this.current = grid.get(value);
            this.current.setVisited(true);
            Cell next = this.current.checkNeighbours(grid);
            if (next != null) {
                next.setVisited(true);
                next.removeWalls(1);
                this.current = next;
                this.removeWalls(this.current, next);
                this.current.removeWalls(1);
            }
            //grid.set(value, this.current);
        });

        grid.forEach(c -> this.getChildren().addAll(c.getGroup()));
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void removeWalls(Cell current, Cell next) {
        int x = current.getI() - next.getI();
        current.removeWalls(3);
        next.removeWalls(1);
        if (x == 1) {
            current.removeWalls(3);
        }
    }
}