package cz.vse.java.persistence;

import cz.vse.java.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSafePersistenceProvider implements PersistenceProvider {

    private static final String SQL_SELECT = "SELECT * from PERSONS";
    private static final String SQL_INSERT = "INSERT INTO PERSONS (FIRST_NAME, LAST_NAME, AGE) VALUES (?,?,?)";
    private static final String CONNECTION_STRING = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "heslo";

    @Override
    public List<Person> loadData() throws PersistenceException {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            List<Person> persons = new ArrayList<>();
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
                persons.add(person);
            }
            return persons;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void saveData(List<Person> persons) throws PersistenceException {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {

            for (Person person : persons) {
                preparedStatement.setString(1, person.getFirstName());
                preparedStatement.setString(2, person.getLastName());
                preparedStatement.setInt(3, person.getAge());
                preparedStatement.addBatch();
            }
            int[] result = preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
