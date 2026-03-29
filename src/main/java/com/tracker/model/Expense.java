package com.tracker.model;

public class Expense {
    private int id;
    private String date;
    private String category;
    private double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public Expense(int id, String date, String category, double amount) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Expense{id=" + id + ", date='" + date + "', category='" + category + "', amount=" + amount + "}";
    }
}