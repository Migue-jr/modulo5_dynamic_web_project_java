package cl.alkewallet.billeteradigital.service;

import cl.alkewallet.billeteradigital.model.User;
import cl.alkewallet.billeteradigital.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletService {

    public User getUser(String userId) {
        User user = null;
        String query = "SELECT * FROM users WHERE userId = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("userId"),
                        resultSet.getString("userName"),
                        resultSet.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void deposit(String userId, double amount) {
        String update = "UPDATE users SET balance = balance + ? WHERE userId = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setDouble(1, amount);
            statement.setString(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void withdraw(String userId, double amount) {
        String update = "UPDATE users SET balance = balance - ? WHERE userId = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setDouble(1, amount);
            statement.setString(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transfer(String fromUserId, String toUserId, double amount) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Withdraw from the sender
            String withdrawQuery = "UPDATE users SET balance = balance - ? WHERE userId = ?";
            try (PreparedStatement withdrawStmt = connection.prepareStatement(withdrawQuery)) {
                withdrawStmt.setDouble(1, amount);
                withdrawStmt.setString(2, fromUserId);
                withdrawStmt.executeUpdate();
            }

            // Deposit to the receiver
            String depositQuery = "UPDATE users SET balance = balance + ? WHERE userId = ?";
            try (PreparedStatement depositStmt = connection.prepareStatement(depositQuery)) {
                depositStmt.setDouble(1, amount);
                depositStmt.setString(2, toUserId);
                depositStmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}