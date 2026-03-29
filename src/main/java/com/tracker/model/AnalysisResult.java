package com.tracker.model;

public class AnalysisResult {
    private double predictedExpense;
    private String month;
    private double confidence;

    public AnalysisResult(double predictedExpense, String month, double confidence) {
        this.predictedExpense = predictedExpense;
        this.month = month;
        this.confidence = confidence;
    }

    public double getPredictedExpense() {
        return predictedExpense;
    }

    public void setPredictedExpense(double predictedExpense) {
        this.predictedExpense = predictedExpense;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}