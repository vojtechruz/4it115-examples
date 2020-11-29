package cz.vse.java.persistence;

import cz.vse.java.Person;

import java.io.*;
import java.util.List;

public class SerializationPersistenceProvider implements PersistenceProvider {
    private static final String SAVE_FILE_NAME = "persons.bin";

    @Override
    public List<Person> loadData() throws PersistenceException {
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(SAVE_FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object

            List<Person> personList = (List<Person>) in.readObject();
            in.close();
            file.close();
            return personList;
        } catch (IOException | ClassNotFoundException ex) {
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void saveData(List<Person> persons) throws PersistenceException {
        try {
            FileOutputStream file = new FileOutputStream("persons.bin");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(persons);

            out.close();
            file.close();
        } catch (IOException ex) {
            throw new PersistenceException(ex);
        }
    }
}
