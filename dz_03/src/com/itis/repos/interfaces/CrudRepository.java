package com.itis.repos.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    List<T> findAll() throws SQLException;

    Optional<T> findById(Long id);

    void save(T entity);
    void update(T entity) throws SQLException;
    void remove(T entity) throws SQLException;
    void removeById(Long id) throws SQLException;



}
