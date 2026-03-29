package com.tracker;

import com.tracker.database.DatabaseHandler;
import com.tracker.ui.DashboardPanel;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Could not set Look and Feel: " + e.getMessage());
            }
            
            DatabaseHandler dbHandler = new DatabaseHandler();
            dbHandler.initialize();
            
            JFrame frame = new JFrame("Smart Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setMinimumSize(new Dimension(700, 500));
            
            DashboardPanel dashboard = new DashboardPanel(frame, dbHandler);
            frame.add(dashboard);
            
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    dbHandler.close();
                    System.exit(0);
                }
            });
            
            frame.setVisible(true);
            System.out.println("Smart Expense Tracker Started!");
        });
    }
}