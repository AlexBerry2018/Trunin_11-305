package com.itis.repos.implement;
import com.itis.models.User;
import com.itis.repos.interfaces.UserRepository;

import java.sql.*;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    private static final String SQL_SELECT_FROM_USERS = "select * from users";
    private static final String SQL_DELETE_FROM_USERS = "delete from users where id = ?";
    private static final String SQL_INSERT_INTO_USERS = "insert into users (id, name, surname, age, email, \"group\", city) " + "values (?, ?, ?, ?, ?, ?, ?)";
    public UsersRepositoryJdbcImpl(Connection connection) {this.connection = connection;}

    private List<User> createListFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString("surname"),
                    resultSet.getInt("age"),
                    resultSet.getString("email"),
                    resultSet.getString("group"),
                    resultSet.getString("city")
            );
            result.add(user);
        }

        return result;
    }
    @Override
    public List<User> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_USERS);

        return createListFromResultSet(resultSet);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            List<User> users = findAll();
            User targetUser = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
            return Optional.of(targetUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_USERS);
            if(entity.getId() != null && entity.getFirstName() != null
                    && entity.getLastName() != null && entity.getAge() != null) {
                statement.setLong(1, entity.getId());
                statement.setString(2, entity.getFirstName());
                statement.setString(3, entity.getLastName());
                statement.setInt(4, entity.getAge());
                statement.setString(5, entity.getEmail());
                statement.setString(6, entity.getGroup());
                statement.setString(7, entity.getCity());

                statement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addSome(int count){
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < count; i++) {
            try {
                System.out.println("Enter id: ");
                long id = Long.parseLong(scanner.nextLine());
                System.out.println("Enter name: ");
                String name = scanner.nextLine();
                System.out.println("Enter surname: ");
                String surname = scanner.nextLine();
                System.out.println("Enter age: ");
                int age = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter email: ");
                String email = scanner.nextLine();
                System.out.println("Enter group: ");
                String group = scanner.nextLine();
                System.out.println("Enter city: ");
                String city = scanner.nextLine();
                User entity = new User(id, name, surname, age, email, group, city);
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_USERS);
                if(entity.getId() != null && entity.getFirstName() != null
                        && entity.getLastName() != null && entity.getAge() != null) {
                    statement.setLong(1, entity.getId());
                    statement.setString(2, entity.getFirstName());
                    statement.setString(3, entity.getLastName());
                    statement.setInt(4, entity.getAge());
                    statement.setString(5, entity.getEmail());
                    statement.setString(6, entity.getGroup());
                    statement.setString(7, entity.getCity());
                    statement.execute();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where email = " + email);

        if(resultSet == null){
            System.out.println("User not found");
            return null;
        }

        System.out.println("User with email "+email+" found");
        return Optional.of(new User(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString("surname"),
                resultSet.getInt("age"),
                resultSet.getString("email"),
                resultSet.getString("group"),
                resultSet.getString("city")
        ));
    }

    @Override
    public void update(User entity) throws SQLException {
        Optional<User> targetUser = findById(entity.getId());
        if (targetUser.isEmpty()) {
            System.out.println("User with id " + entity.getId() + " not found");
            throw new RuntimeException("User with id " + entity.getId() + " not found");
        }
        PreparedStatement statement = connection.prepareStatement("update users set name = ?, surname = ?, age = ?, email = ?, \"group\" = ?, city = ? where id = ?");
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setInt(3, entity.getAge());
        statement.setString(4, entity.getEmail());
        statement.setString(5, entity.getGroup());
        statement.setString(6, entity.getCity());
        statement.setLong(7, targetUser.get().getId());
        statement.execute();

    }

    @Override
    public void remove(User entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        PreparedStatement deleteQuery = connection.prepareStatement(SQL_DELETE_FROM_USERS);
        if(id == 0) {
            throw new SQLException("Users with that ID not found!");
        }
        deleteQuery.setLong(1, id);
        System.out.println("Deleted user with id " + id);
    }

    @Override
    public List<User> findAllByAge(Integer age) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where age = " + age);
        return createListFromResultSet(resultSet);
    }

    @Override
    public List<User> findAllByCity(String city) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where city = " +  "'" + city + "'");
        return createListFromResultSet(resultSet);
    }

    @Override
    public List<User> findAllByGroup(String group) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where \"group\" = " +  "'" + group + "'");
        return createListFromResultSet(resultSet);
    }
}
