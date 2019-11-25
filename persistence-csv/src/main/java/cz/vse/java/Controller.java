package cz.vse.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.omg.CORBA.Environment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static final String SAVE_FILE_NAME = "persons.csv";
    private List<Person> persons = new ArrayList<>();

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField age;
    @FXML
    private TableView<Person> personsTable;

    public void loadData(ActionEvent actionEvent) {
        persons = new ArrayList<>();

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

            personsTable.getItems().clear();
            personsTable.getItems().addAll(persons);
        } catch (IOException e) {
            showError("Error loading the data: "+e.getMessage());
        }
    }

    public void saveData(ActionEvent actionEvent) {
        try {
            FileWriter csvWriter = new FileWriter(Controller.SAVE_FILE_NAME);
            for (Person person : persons) {
                csvWriter.append(person.getFirstName()).append(",");
                csvWriter.append(person.getLastName()).append(",");
                csvWriter.append(String.valueOf(person.getAge()));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            showError("Error saving the data: "+e.getMessage());
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
