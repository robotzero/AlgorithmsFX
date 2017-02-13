package com.queen.sandbox.algorithms.models.quickfind;
import com.queen.sandbox.algorithms.repositories.Repository;

public class QuickFind {
//    private int[] container;
    private final Repository repository;

    public QuickFind(Repository repository) {
        this.repository = repository;
//        this.container = new int[10];
//        for (int i = 0; i < 10; i++) {
//            this.container[i] = i;
//        }
    }

    public boolean connected(Person p1, Person p2) {
        return this.repository.getDataContainer().get(p1).intValue() == this.repository.getDataContainer().get(p2).intValue();
//        return container[p] == container[q];
    }

    public void union(Person p1, Person p2) {

        int pid = repository.getDataContainer().get(p1);
        int qid = repository.getDataContainer().get(p2);

//        int pid = container[p];
//        int qid = container[q];

        this.repository.getDataContainer().forEach((person, id) -> {
            if (id == pid) {
                this.repository.getDataContainer().replace(person, qid);
            }
        });
//            if (container[i] == pid) {
//                container[i] = qid;
//            }
//        }
    }
}
