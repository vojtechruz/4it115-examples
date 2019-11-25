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

    private static final String SAVE_FILE_NAME = "persons.bin";
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
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(SAVE_FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            persons = (List<Person>) in.readObject();
            personsTable.getItems().clear();
            personsTable.getItems().addAll(persons);

            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException ex) {
            showError("Error while loading data: " + ex.getMessage());
        }
    }

    public void saveData(ActionEvent actionEvent) {
        try {
            FileOutputStream file = new FileOutputStream("persons.bin");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(persons);

            out.close();
            file.close();
        } catch (IOException ex) {
            showError("Error while loading data: " + ex.getMessage());
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
