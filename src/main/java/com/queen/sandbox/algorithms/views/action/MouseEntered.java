package com.queen.sandbox.algorithms.views.action;

import com.queen.sandbox.algorithms.models.quickfind.Person;

public class MouseEntered {
    private final Person person;

    public MouseEntered(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
