package com.sofka.cuentaBancaria.repository;

import com.sofka.cuentaBancaria.config.DatabaseConnection;
import com.sofka.cuentaBancaria.model.TransactionHistory;
import com.sofka.cuentaBancaria.model.TypeOfTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionHistoryRepositoryImpl implements CrudRepository<TransactionHistory> {

    private DatabaseConnection databaseConnection;

    @Autowired
    public TransactionHistoryRepositoryImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void create(TransactionHistory transactionHistory) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO Transaction_history(user_id,total,type_of_transaction) VALUES (?,?,?)")) {
            stmt.setInt(1, transactionHistory.getUserId());
            stmt.setBigDecimal(2, transactionHistory.getTotal());
            stmt.setString(3, transactionHistory.getTypeOfTransaction().toString());
            stmt.executeUpdate();
        }finally {
            databaseConnection.close();
        }
    }

    @Override
    public void update(TransactionHistory transactionHistory) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("UPDATE Transaction_history SET total=?,type_of_transaction=? WHERE user_id=?")) {
            stmt.setBigDecimal(1, transactionHistory.getTotal());
            stmt.setString(2, transactionHistory.getTypeOfTransaction().toString());
            stmt.setInt(3, transactionHistory.getUserId());
            stmt.executeUpdate();
        }finally {
            databaseConnection.close();
        }
    }

    @Override
    public Boolean delete(TransactionHistory transactionHistory) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM Transaction_history WHERE user_id=?")) {
            stmt.setInt(1, transactionHistory.getUserId());
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        }finally {
            databaseConnection.close();
        }
        return false;
    }

    private TransactionHistory createTransaction(ResultSet rs) throws SQLException {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionId(rs.getInt("transaction_id"));
        transactionHistory.setUserId(rs.getInt("user_id"));
        transactionHistory.setTypeOfTransaction(TypeOfTransaction.valueOf(rs.getString("type_of_transaction")));
        transactionHistory.setTotal(rs.getBigDecimal("total"));
        return transactionHistory;
    }

    @Override
    public List<TransactionHistory> getAll() throws SQLException {
        List<TransactionHistory> transactions = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction_history")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(createTransaction(rs));
            }
        }finally {
            databaseConnection.close();
        }
        return transactions;
    }

    @Override
    public TransactionHistory getById(int id) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction_history WHERE user_id=?")) {
             stmt.setInt(1, id);
             ResultSet rs = stmt.executeQuery();
             if (rs.next()) {
                 return createTransaction(rs);
             }
        }finally {
            databaseConnection.close();
        }
        return null;
    }
}
