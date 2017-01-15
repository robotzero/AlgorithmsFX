package com.queen.algs.QuickFind.Alg;

import com.queen.algs.QuickUnion.Person;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuickFind {
//    private int[] container;
    private final Map<Integer, Person> dataContainer;

    public QuickFind() {
        this.dataContainer = IntStream.range(0, 10)
                .mapToObj(id -> {
                    Rectangle rectangle = new Rectangle(100, 100, 100, 100);
                    rectangle.setStroke(Color.BLACK);
                    rectangle.setX(id * 180);
                    rectangle.setY(200);
                    return new Person(
                            id,
                            Integer.toString(id),
                            rectangle,
                            new Color(0.5, 0.5, 0.5, 0));
                })
                .collect(Collectors.toMap(Person::getId, entry -> entry));
//        this.container = new int[10];
//        for (int i = 0; i < 10; i++) {
//            this.container[i] = i;
//        }
    }

    public boolean connected(int p, int q) {
        return dataContainer.get(p) == dataContainer.get(q);
//        return container[p] == container[q];
    }

    public void union(int p, int q) {
//        int pid = container[p];
//        int qid = container[q];
        int pid = dataContainer.get(p).getId();
        int qid = dataContainer.get(q).getId();


        for(int i = 0; i < dataContainer.size(); i++) {
            if (dataContainer.get(i).getId() == pid) {
                Person person = dataContainer.get(qid);
                dataContainer.put(i, person);
            }
//            if (container[i] == pid) {
//                container[i] = qid;
//            }
        }
    }

    public Map<Integer, Person> getDatContainer() {
        return this.dataContainer;
    }
}
