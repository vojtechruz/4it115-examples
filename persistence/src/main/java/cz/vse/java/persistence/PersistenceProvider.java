package cz.vse.java.persistence;

import cz.vse.java.Person;

import java.util.List;

public interface PersistenceProvider {
    List<Person> loadData() throws PersistenceException;
    void saveData(List<Person> persons) throws PersistenceException;
}
