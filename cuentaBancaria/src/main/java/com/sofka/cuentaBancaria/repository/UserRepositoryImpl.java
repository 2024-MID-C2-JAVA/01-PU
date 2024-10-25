package com.sofka.cuentaBancaria.repository;

import com.sofka.cuentaBancaria.config.DatabaseConnection;
import com.sofka.cuentaBancaria.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements CrudRepository<User> {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public UserRepositoryImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void create(User user) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO Users(user_name) VALUES (?)")) {
            stmt.setString(1, user.getName());
            stmt.executeUpdate();
        }finally {
            databaseConnection.close();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("UPDATE Users SET user_name = ? WHERE user_id = ?")) {
            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getUserId());
            stmt.executeUpdate();
        }finally {
            databaseConnection.close();
        }
    }

    @Override
    public Boolean delete(User user) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM Users WHERE user_id = ?")) {
            stmt.setInt(1, user.getUserId());
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        }finally {
            databaseConnection.close();
        }
        return false;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users")) {
            User user = new User();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
                users.add(user);
            }
        }finally {
            databaseConnection.close();
        }
        return users;
    }

    @Override
    public User getById(int id) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE user_id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user=new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
                return user;
            }
        }finally {
            databaseConnection.close();
        }
        return null;
    }
}
