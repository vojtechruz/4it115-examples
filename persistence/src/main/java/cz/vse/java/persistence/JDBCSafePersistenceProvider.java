package cz.vse.java.persistence;

import cz.vse.java.Person;

import java.util.List;

public class JDBCSafePersistenceProvider implements PersistenceProvider {

    @Override
    public List<Person> loadData() throws PersistenceException {
      return null;
    }

    @Override
    public void saveData(List<Person> persons) throws PersistenceException {

    }
}
