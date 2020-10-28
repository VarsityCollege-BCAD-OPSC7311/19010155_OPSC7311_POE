package com.jadwat.healthapp;

public class LogWeightProgress {

    private double Weight;

    public LogWeightProgress() {
    }

    public LogWeightProgress(double weight) {
        Weight = weight;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public String ToString() {
        return "Weight: " + Weight;
    }
}
