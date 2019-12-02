package cz.vse.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static final String SQL_SELECT = "SELECT * from PERSONS";
    private static final String SQL_INSERT = "INSERT INTO PERSONS (FIRST_NAME, LAST_NAME, AGE) VALUES (?,?,?)";
    private static final String CONNECTION_STRING = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "heslo";

    private List<Person> persons = new ArrayList<>();

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField age;
    @FXML
    private TableView<Person> personsTable;

    public Controller() {

    }

    public void loadData(ActionEvent actionEvent) {
        this.personsTable.getItems().clear();

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String firstName = resultSet.getString("FIRST_NAME");
                String lastName = resultSet.getString("LAST_NAME");
                int age = resultSet.getInt("AGE");

                Person person = new Person();
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setAge(age);
                this.persons.add(person);
                this.personsTable.getItems().add(person);
            }

        } catch (SQLException e) {
            System.err.format("Error reading persons: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public void saveData(ActionEvent actionEvent) {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {

            for (Person person : this.persons) {
                preparedStatement.setString(1, person.getFirstName());
                preparedStatement.setString(2, person.getLastName());
                preparedStatement.setInt(3, person.getAge());
                preparedStatement.addBatch();
            }
            int[] result = preparedStatement.executeBatch();
            System.out.println(result);

        } catch (SQLException e) {
            System.err.format("Error reading persons: %s\n%s", e.getSQLState(), e.getMessage());
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
