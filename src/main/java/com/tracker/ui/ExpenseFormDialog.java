package com.tracker.ui;

import com.tracker.database.DatabaseHandler;
import com.tracker.model.Expense;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseFormDialog extends JDialog {
    private JTextField dateField;
    private JComboBox<String> categoryComboBox;
    private JTextField amountField;
    private DatabaseHandler dbHandler;
    private boolean saved = false;

    public ExpenseFormDialog(JFrame parent, DatabaseHandler dbHandler) {
        super(parent, "Add New Expense", true);
        this.dbHandler = dbHandler;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(41, 128, 185));
        JLabel titleLabel = new JLabel("Add New Expense");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(dateLabel);
        
        dateField = new JTextField();
        dateField.setText(getTodayDate());
        dateField.setFont(new Font("Arial", Font.PLAIN, 16));
        dateField.setPreferredSize(new Dimension(200, 35));
        formPanel.add(dateField);
        
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(categoryLabel);
        
        String[] categories = {"Food", "Travel", "Bills", "Other"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryComboBox.setPreferredSize(new Dimension(200, 35));
        formPanel.add(categoryComboBox);
        
        JLabel amountLabel = new JLabel("Amount (₹):");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(amountLabel);
        
        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        amountField.setPreferredSize(new Dimension(200, 35));
        formPanel.add(amountField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 25, 10));
        buttonPanel.setPreferredSize(new Dimension(450, 80));
        
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.setOpaque(true);
        saveButton.addActionListener(e -> saveExpense());
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setOpaque(true);
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    private void saveExpense() {
        try {
            String date = dateField.getText().trim();
            String category = (String) categoryComboBox.getSelectedItem();
            String amountText = amountField.getText().trim();
            
            if (date.isEmpty() || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter date in YYYY-MM-DD format!", 
                    "Invalid Date", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter an amount!", 
                    "Missing Amount", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double amount;
            try {
                amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Amount must be greater than 0!", 
                        "Invalid Amount", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid number for amount!", 
                    "Invalid Amount", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Expense expense = new Expense(date, category, amount);
            
            boolean success = dbHandler.addExpense(expense);
            
            if (success) {
                saved = true;
                JOptionPane.showMessageDialog(this, 
                    "Expense added successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to add expense. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "An error occurred: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}