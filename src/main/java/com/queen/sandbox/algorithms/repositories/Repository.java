package com.queen.sandbox.algorithms.repositories;

import com.queen.sandbox.algorithms.models.quickfind.Person;

import java.util.Map;

public interface Repository {
    public Map<Person, Integer> getDataContainer();
}
