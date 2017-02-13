package com.queen.sandbox.algorithms.repositories;

import com.queen.sandbox.algorithms.models.quickfind.Person;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Repository {
    public Map<Person, Integer> getDataContainer();

    public Supplier<Stream<Person>> searchPerson(Function<Map.Entry<Person, Integer>, Person> translate, Predicate<Person> searchFilter);
    public Supplier<Stream<Person>> findAllPeople();
}
