package com.sofka.cuentaBancaria.repository;

import com.sofka.cuentaBancaria.config.DatabaseConnection;
import com.sofka.cuentaBancaria.model.UserBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserBalanceRepositoryImpl implements CrudRepository<UserBalance> {

    private final DatabaseConnection databaseConnection;

    @Autowired
    public UserBalanceRepositoryImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void create(UserBalance userBalance) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO User_balance(user_id,balance) VALUES (?,?)")) {
            stmt.setInt(1, userBalance.getUserId());
            stmt.setBigDecimal(2, userBalance.getBalance());
            stmt.executeUpdate();
        }finally {
            databaseConnection.close();
        }
    }

    @Override
    public void update(UserBalance userBalance) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("UPDATE User_balance SET balance=? WHERE user_id=?")) {
            stmt.setBigDecimal(1, userBalance.getBalance());
            stmt.setInt(2, userBalance.getUserId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Boolean delete(UserBalance userBalance) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("DELETE * FROM User_balance WHERE user_id=?")) {
            stmt.setInt(1, userBalance.getUserId());
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        }finally {
            databaseConnection.close();
        }
        return false;
    }

    @Override
    public List<UserBalance> getAll() throws SQLException {
        List<UserBalance> userBalances = new ArrayList<UserBalance>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User_balance")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserBalance userBalance = new UserBalance();
                userBalance.setUserId(rs.getInt("user_id"));
                userBalance.setBalance(rs.getBigDecimal("balance"));
                userBalances.add(userBalance);
            }
        }finally {
            databaseConnection.close();
        }
        return userBalances;
    }

    @Override
    public UserBalance getById(int id) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User_balance WHERE user_id=?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserBalance userBalance = new UserBalance();
                userBalance.setUserId(rs.getInt("user_id"));
                userBalance.setBalance(rs.getBigDecimal("balance"));
                return userBalance;
            }
        }finally {
            databaseConnection.close();
        }
        return null;
    }
}
