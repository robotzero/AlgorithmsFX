package com.queen.algs.QuickFind.Alg;

import com.queen.algs.QuickUnion.Person;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuickFind {
//    private int[] container;
    private final Map<Integer, Person> dataContainer;

    public QuickFind() {
        this.dataContainer = IntStream.range(0, 10)
                .mapToObj(id -> new Person(
                        id,
                        Integer.toString(id),
                        new Rectangle(10, 10, 10, 10),
                        new Color(234, 222, 122, 0)))
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
