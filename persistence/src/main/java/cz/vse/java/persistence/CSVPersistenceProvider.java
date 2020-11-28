package cz.vse.java.persistence;

import cz.vse.java.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVPersistenceProvider implements PersistenceProvider {
    private static final String SAVE_FILE_NAME = "persons.csv";

    @Override
    public List<Person> loadData() throws PersistenceException {
        List<Person> persons = new ArrayList<>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(SAVE_FILE_NAME));
            String row = csvReader.readLine();

            while (row != null) {
                String[] data = row.split(",");

                Person person = new Person();
                person.setFirstName(data[0]);
                person.setLastName(data[1]);
                person.setAge(Integer.parseInt(data[2]));
                persons.add(person);

                row = csvReader.readLine();
            }
            csvReader.close();
            return persons;
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void saveData(List<Person> persons) throws PersistenceException {
        try {
            FileWriter csvWriter = new FileWriter(SAVE_FILE_NAME);
            for (Person person : persons) {
                csvWriter.append(person.getFirstName()).append(",");
                csvWriter.append(person.getLastName()).append(",");
                csvWriter.append(String.valueOf(person.getAge()));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }
}
