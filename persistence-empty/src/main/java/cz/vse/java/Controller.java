package cz.vse.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Person> persons = new ArrayList<>();

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField age;

    public void loadData(ActionEvent actionEvent) {
        // TODO Implement me!
    }

    public void saveData(ActionEvent actionEvent) {
        // TODO implement me!
    }

    public void addPerson(ActionEvent actionEvent) {
        Person person = new Person();
        person.setFirstName(firstName.getText());
        person.setLastName(lastName.getText());
        person.setAge(Integer.parseInt(age.getText()));
        this.persons.add(person);
    }
}
