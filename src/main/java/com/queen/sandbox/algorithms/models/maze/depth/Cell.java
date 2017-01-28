package com.queen.sandbox.algorithms.models.maze.depth;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private List<Shape> lines = new ArrayList<>();
    private boolean[] walls = {true, true, true, true};
    private boolean visited = false;
    private int i, j, w, x, y;
    private Group group = new Group();

    private ObservableList<Node> nodes;

    public Cell(int i, int j, int w) {
        this.visited = false;
        this.w = w;
        int x = i * w;
        int y = j * w;
        this.x = x;
        this.y = y;
        this.i = i;
        this.j = j;
        Line line1 = new Line(x, y, x + w, y);
        Line line2 = new Line(x + w, y, x + w, y + w);
        Line line3 = new Line(x + w, y + w, x, y + w);
        Line line4 = new Line(x, y + w, x, y);
        // TOP
        if (this.walls[0]) {
            line1.setStroke(Color.BEIGE);
            line1.setFill(null);
            this.lines.add(line1);
        }
        // RIGHT
        if (this.walls[1]) {
            line2.setStroke(Color.BEIGE);
            line2.setFill(null);
            this.lines.add(line2);
        }
        // BOTTOM
        if (this.walls[2]) {
            line3.setStroke(Color.BEIGE);
            line3.setFill(null);
            this.lines.add(line3);
        }
        // LEFT
        if (this.walls[3]) {
            line4.setStroke(Color.BEIGE);
            line4.setFill(null);
            this.lines.add(line4);
        }

        if (this.visited) {
            Rectangle rectangle = new Rectangle(x, y, w, w);
            rectangle.setFill(Color.AQUAMARINE);
            this.lines.add(rectangle);
        }

        this.group.getChildren().addAll(this.lines);
        this.nodes = this.group.getChildren();
    }

    public List<Shape> getNodes() {
        return this.lines;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setVisited(boolean isVisited) {
        this.visited = isVisited;
//        if (this.visited) {
//            this.lines.stream().forEach(n -> n.setStroke(Color.AQUAMARINE));
//        }

        Rectangle rectangle = new Rectangle(x, y, w, w);
        rectangle.setFill(Color.AQUAMARINE);
//        this.lines.add(rectangle);
//        this.group.getChildren().add(rectangle);
    }

    public Cell checkNeighbours(List<Cell> grid) {
        List<Cell> neighbors = new ArrayList<>();
        Cell top = null, right = null, bottom = null, left = null;
        {
            int index = index(i, j - 1);
            if (index > 0) {
                top = grid.get(index);
            }
        }
        {
            int index = index(i + 1, j);
            if (index > 0) {
                right = grid.get(index);
            }
        }
        {
            int index = index(i, j + 1);
            if (index > 0) {
                bottom = grid.get(index);
            }
        }

        {
            int index = index(i - 1, j);
            if (index > 0) {
                left = grid.get(index);
            }
        }

        if (top != null && !top.visited) {
            neighbors.add(top);
        }

        if (right != null && !right.visited) {
            neighbors.add(right);
        }

        if (bottom != null && !bottom.visited) {
            neighbors.add(bottom);
        }

        if (left != null && !left.visited) {
            neighbors.add(left);
        }

        if (neighbors.size() > 0) {
            double r = Math.floor(Math.random() * neighbors.size());
            return neighbors.get((int) r);
        }

        return null;
    }

    private int index(int i, int j) {
        if (i < 0 || j < 0 || i > Depth.COLLS - 1 || j > Depth.ROWS - 1) {
            return -1;
        }
        return i + j * Depth.COLLS;
    }

    public int getI() {
        return this.i;
    }

    public int getJ() {
        return this.j;
    }

    public void removeWalls(int index) {
        this.walls[index] = false;
        this.group.getChildren().removeAll();
        // TOP
//        if (!this.walls[0]) {
//            this.group.getChildren().remove(0);
//        }
//        // RIGHT
//        if (!this.walls[1]) {
//            this.group.getChildren().remove(1);
//        }
//        // BOTTOM
//        if (!this.walls[2]) {
//            this.group.getChildren().remove(2);
//        }
//        // LEFT
//        if (!this.walls[3]) {
//            this.group.getChildren().remove(3);
//        }
    }
}
