package cz.vse.java;

import cz.vse.java.persistence.PersistenceException;
import cz.vse.java.persistence.PersistenceProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Person> persons = new ArrayList<>();
    private PersistenceProvider persistenceProvider;

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
            this.persons = this.persistenceProvider.loadData();
            personsTable.getItems().clear();
            personsTable.getItems().addAll(persons);
        } catch (PersistenceException e) {
            showError(e);
        }
    }

    public void saveData(ActionEvent actionEvent) {
        try {
            this.persistenceProvider.saveData(this.persons);
        } catch (PersistenceException e) {
            showError(e);
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

    public void showError(Exception exception) {
        String message = "Error occurred: " + exception.getMessage();
        System.err.println(message);
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }

    public void setPersistenceProvider(PersistenceProvider persistenceProvider) {
        this.persistenceProvider = persistenceProvider;
    }
}
