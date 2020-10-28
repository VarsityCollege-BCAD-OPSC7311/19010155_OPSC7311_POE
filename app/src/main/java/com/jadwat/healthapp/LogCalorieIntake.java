package com.jadwat.healthapp;

public class LogCalorieIntake {
    private String Date;
    private int CaloriesConsumed;

    public LogCalorieIntake() {
    }

    public LogCalorieIntake(String date, int caloriesConsumed) {
        Date = date;
        CaloriesConsumed = caloriesConsumed;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCaloriesConsumed() {
        return CaloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        CaloriesConsumed = caloriesConsumed;
    }
}
