package com.jadwat.healthapp;

public class LogWeightProgress {

    private String Date;
    private double Weight;

    public LogWeightProgress() {
    }

    public LogWeightProgress(String date, double weight) {
        Date = date;
        Weight = weight;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
