package com.tracker.ui;

import com.tracker.database.DatabaseHandler;
import com.tracker.model.Expense;
import com.tracker.service.PredictionService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel {
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JLabel predictionLabel;
    private JButton addExpenseButton;
    private JButton refreshButton;
    private DatabaseHandler dbHandler;
    private PredictionService predictionService;
    private JFrame parentFrame;

    public DashboardPanel(JFrame parentFrame, DatabaseHandler dbHandler) {
        this.parentFrame = parentFrame;
        this.dbHandler = dbHandler;
        this.predictionService = new PredictionService();
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        initComponents();
        refreshData();
    }

    private void initComponents() {
        JPanel summaryPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        summaryPanel.setBackground(new Color(236, 240, 241));
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        totalLabel = new JLabel("Total Expenses: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(new Color(52, 73, 94));
        
        predictionLabel = new JLabel("Predicted Next Month: ₹0.00");
        predictionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        predictionLabel.setForeground(new Color(41, 128, 185));
        
        summaryPanel.add(totalLabel);
        summaryPanel.add(predictionLabel);
        
        String[] columnNames = {"ID", "Date", "Category", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        expenseTable = new JTable(tableModel);
        expenseTable.setFont(new Font("Arial", Font.PLAIN, 14));
        expenseTable.setRowHeight(30);
        expenseTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        expenseTable.getTableHeader().setBackground(new Color(52, 73, 94));
        expenseTable.getTableHeader().setForeground(Color.WHITE);
        expenseTable.setSelectionBackground(new Color(52, 152, 219));
        expenseTable.setSelectionForeground(Color.WHITE);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < expenseTable.getColumnCount(); i++) {
            expenseTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        expenseTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        expenseTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        expenseTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        expenseTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.setPreferredSize(new Dimension(800, 90));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setPreferredSize(new Dimension(160, 45));
        addExpenseButton.setBackground(new Color(46, 204, 113));
        addExpenseButton.setForeground(Color.WHITE);
        addExpenseButton.setFont(new Font("Arial", Font.BOLD, 16));
        addExpenseButton.setFocusPainted(false);
        addExpenseButton.setBorderPainted(false);
        addExpenseButton.setOpaque(true);
        addExpenseButton.addActionListener(e -> openAddExpenseDialog());
        
        refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(160, 45));
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setOpaque(true);
        refreshButton.addActionListener(e -> refreshData());
        
        buttonPanel.add(addExpenseButton);
        buttonPanel.add(refreshButton);
        
        add(summaryPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void openAddExpenseDialog() {
        ExpenseFormDialog dialog = new ExpenseFormDialog(parentFrame, dbHandler);
        dialog.setVisible(true);
        
        if (dialog.isSaved()) {
            refreshData();
        }
    }

    public void refreshData() {
        try {
            List<Expense> expenses = dbHandler.getAllExpenses();
            
            tableModel.setRowCount(0);
            
            double totalExpenses = 0.0;
            for (Expense expense : expenses) {
                Object[] row = {
                    expense.getId(),
                    expense.getDate(),
                    expense.getCategory(),
                    String.format("₹%.2f", expense.getAmount())
                };
                tableModel.addRow(row);
                totalExpenses += expense.getAmount();
            }
            
            totalLabel.setText("Total Expenses: ₹" + String.format("%.2f", totalExpenses));
            
            double prediction = predictionService.predictNextMonth(expenses);
            predictionLabel.setText("Predicted Next Month: ₹" + String.format("%.2f", prediction));
            
            if (expenses.isEmpty()) {
                predictionLabel.setText("Predicted Next Month: No data available");
            }
            
            System.out.println("Dashboard refreshed: " + expenses.size() + " expenses loaded.");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading data: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public JTable getExpenseTable() {
        return expenseTable;
    }
}