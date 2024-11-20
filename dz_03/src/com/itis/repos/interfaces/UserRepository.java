package com.itis.repos.interfaces;

import com.itis.models.User;
import com.itis.repos.interfaces.CrudRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {
    void addSome(int count);

    Optional<User> findByEmail(String email) throws SQLException;

    List<User> findAllByAge(Integer age) throws SQLException;
    List<User> findAllByCity(String city) throws SQLException;
    List<User> findAllByGroup(String group) throws SQLException;

}
