package com.tracker.database;

import com.tracker.model.Expense;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:expenses.db";
    private Connection connection;

    public DatabaseHandler() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connected.");
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    public void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT NOT NULL, " +
                "category TEXT NOT NULL, " +
                "amount REAL NOT NULL)";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database initialized.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public boolean addExpense(Expense expense) {
        String insertSQL = "INSERT INTO expenses (date, category, amount) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, expense.getDate());
            pstmt.setString(2, expense.getCategory());
            pstmt.setDouble(3, expense.getAmount());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Expense added successfully: " + expense);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding expense: " + e.getMessage());
        }
        return false;
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT id, date, category, amount FROM expenses ORDER BY date DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                expenses.add(new Expense(
                    rs.getInt("id"),
                    rs.getString("date"),
                    rs.getString("category"),
                    rs.getDouble("amount")
                ));
            }
            
            System.out.println("Retrieved " + expenses.size() + " expenses.");
        } catch (SQLException e) {
            System.err.println("Error retrieving expenses: " + e.getMessage());
        }
        
        return expenses;
    }

    public boolean deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting expense: " + e.getMessage());
        }
        return false;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}