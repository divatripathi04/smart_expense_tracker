package com.tracker.service;

import com.tracker.model.Expense;
import java.util.*;

public class PredictionService {
    
    public double predictNextMonth(List<Expense> expenses) {
        try {
            Map<String, Double> monthlyTotals = groupExpensesByMonth(expenses);
            
            if (monthlyTotals.size() < 2) {
                System.out.println("Need at least 2 months of data for prediction.");
                return 0.0;
            }
            
            List<String> sortedMonths = new ArrayList<>(monthlyTotals.keySet());
            Collections.sort(sortedMonths);
            
            int n = sortedMonths.size();
            double[] x = new double[n];
            double[] y = new double[n];
            
            for (int i = 0; i < n; i++) {
                x[i] = i;
                y[i] = monthlyTotals.get(sortedMonths.get(i));
            }
            
            double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
            
            for (int i = 0; i < n; i++) {
                sumX += x[i];
                sumY += y[i];
                sumXY += x[i] * y[i];
                sumX2 += x[i] * x[i];
            }
            
            double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
            double intercept = (sumY - slope * sumX) / n;
            double prediction = Math.max(0, slope * n + intercept);
            
            System.out.println("Prediction: ₹" + String.format("%.2f", prediction));
            return prediction;
            
        } catch (Exception e) {
            System.err.println("Prediction error: " + e.getMessage());
            return 0.0;
        }
    }
    
    private Map<String, Double> groupExpensesByMonth(List<Expense> expenses) {
        Map<String, Double> monthlyTotals = new HashMap<>();
        
        for (Expense expense : expenses) {
            String date = expense.getDate();
            if (date != null && date.length() >= 7) {
                String month = date.substring(0, 7);
                monthlyTotals.put(month, 
                    monthlyTotals.getOrDefault(month, 0.0) + expense.getAmount());
            }
        }
        return monthlyTotals;
    }
    
    public double calculateTotal(List<Expense> expenses) {
        double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }
    
    public double calculateAverage(List<Expense> expenses) {
        if (expenses == null || expenses.isEmpty()) {
            return 0.0;
        }
        return calculateTotal(expenses) / expenses.size();
    }
}