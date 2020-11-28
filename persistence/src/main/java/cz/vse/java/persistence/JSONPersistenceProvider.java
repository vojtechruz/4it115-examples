package cz.vse.java.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.vse.java.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONPersistenceProvider implements PersistenceProvider {
    private static final String SAVE_FILE_NAME = "persons.json";

    private Gson gson = new Gson();

    @Override
    public List<Person> loadData() throws PersistenceException {
        try {
            List<String> lines = Files.readAllLines(Paths.get(SAVE_FILE_NAME));
            String jsonRaw = String.join("\n", lines);
            Type listOfPersonsType = new TypeToken<List<Person>>() {}.getType();
            return gson.fromJson(jsonRaw, listOfPersonsType);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void saveData(List<Person> persons) throws PersistenceException {
        String json = gson.toJson(persons);
        try {
            Files.write(Paths.get(SAVE_FILE_NAME), json.getBytes());
        } catch (IOException e) {
            throw new PersistenceException(e);

        }
    }
}
