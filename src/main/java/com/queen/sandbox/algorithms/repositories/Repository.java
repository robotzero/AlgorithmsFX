package com.queen.sandbox.algorithms.repositories;

import com.queen.sandbox.algorithms.models.quickfind.Person;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Repository {
    Supplier<Stream<Person>> searchPerson(Predicate<Person> searchFilter);
    Supplier<Stream<Person>> findAllPeople();
    Integer getPersonConnectedToId(Person person);
    void updatePersonConnection(int toUpdate, int newId);
    void resetAllConnections();
}
