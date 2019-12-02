package cz.vse.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static final String SAVE_FILE_NAME = "persons.json";
    private List<Person> persons = new ArrayList<>();
    private Gson gson = new Gson();

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField age;
    @FXML
    private TableView<Person> personsTable;

    public void loadData(ActionEvent actionEvent) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(SAVE_FILE_NAME));
            String jsonRaw = String.join("\n", lines);
            Type listOfPersonsType = new TypeToken<List<Person>>() {}.getType();
            persons = gson.fromJson(jsonRaw, listOfPersonsType);

            personsTable.getItems().clear();
            personsTable.getItems().addAll(persons);
        } catch (IOException e) {
            showError("Error while saving data: " + e.getMessage());
        }
    }

    public void saveData(ActionEvent actionEvent) {
        String json = gson.toJson(persons);
        try {
            Files.write(Paths.get(SAVE_FILE_NAME), json.getBytes());
        } catch (IOException e) {
            showError("Error while saving data: " + e.getMessage());
        }
    }

    public void addPerson(ActionEvent actionEvent) {
        Person person = new Person();
        person.setFirstName(firstName.getText());
        person.setLastName(lastName.getText());
        person.setAge(Integer.parseInt(age.getText()));
        persons.add(person);

        firstName.setText("");
        lastName.setText("");
        age.setText("");
        firstName.requestFocus();

        this.personsTable.getItems().add(person);
    }

    public void showError(String error) {
        System.err.println(error);
        Alert alert = new Alert(AlertType.ERROR, error, ButtonType.OK);
        alert.show();
    }
}
