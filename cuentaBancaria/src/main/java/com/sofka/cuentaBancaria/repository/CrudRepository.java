package com.sofka.cuentaBancaria.repository;

import java.sql.SQLException;
import java.util.List;

@org.springframework.stereotype.Repository
interface CrudRepository<T> {
    void create(T t) throws SQLException;
    void update(T t) throws SQLException;
    Boolean delete(T t) throws SQLException;
    List<T> getAll() throws SQLException;
    T getById(int id) throws SQLException;
}
