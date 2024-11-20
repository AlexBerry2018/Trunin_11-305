package com.itis.repos.implement;

import com.itis.models.User;
import com.itis.repos.interfaces.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "12345";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/users";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);
        List<User> users = userRepository.findAll();
        users.forEach(u -> System.out.println(u.getFirstName()));
        userRepository.findAllByGroup("Almetyevsk").forEach(u -> System.out.println(u.getFirstName()));
        userRepository.update(new User(7L, "NewName", "MewSurname", 99, "NewEmail", "NewGroup", "NewCity"));
    }



}
