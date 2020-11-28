package cz.vse.java;

import cz.vse.java.persistence.PersistenceException;
import cz.vse.java.persistence.PersistenceProvider;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private Button addPerson;
    @FXML
    private TableView<Person> personsTable;


    public void init() {
        // Age should be integer only
        addPerson.setDisable(true);
        age.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    validateInputs();
                    if (newValue.equals("")) {
                        return;
                    }

                    //Check if new value is integer, if not, revert to old val
                    try {
                        Integer.parseInt(newValue);
                    } catch (NumberFormatException e) {
                        age.setText(oldValue);
                    }
                }
        );

        firstName.setOnKeyTyped(event -> {
            validateInputs();
        });
        lastName.setOnKeyTyped(event -> {
            validateInputs();
        });
    }

    private void validateInputs() {
        if(firstName.getText().equals("") ||
                lastName.getText().equals("") ||
                age.getText().equals("")) {
            addPerson.setDisable(true);
            addPerson.setTooltip(new Tooltip("Please fill all the fields first"));
        } else {
            addPerson.setTooltip(null);
            addPerson.setDisable(false);
        }

    }

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
        if(age.getText() != null && !age.getText().equals("")) {
            person.setAge(Integer.parseInt(age.getText()));
        }
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
