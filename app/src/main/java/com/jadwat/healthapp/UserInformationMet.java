package com.jadwat.healthapp;


public class UserInformationMet {


    private String Email;
    private double HeightInCm;
    private double WeightInKilos;
    private int Age;
    private String Gender;
    private double WeightGoalMetric;
    private double CaloriesGoal;
    private String Measurement;

    public UserInformationMet() {
    }

    public UserInformationMet(String email, double heightInCm, double weightInKilos, int age, String gender, double weightGoalMetric, double caloriesGoal, String measurement) {
        Email = email;
        HeightInCm = heightInCm;
        WeightInKilos = weightInKilos;
        Age = age;
        Gender = gender;
        WeightGoalMetric = weightGoalMetric;
        CaloriesGoal = caloriesGoal;
        Measurement = measurement;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public double getHeightInCm() {
        return HeightInCm;
    }

    public void setHeightInCm(double heightInCm) {
        HeightInCm = heightInCm;
    }

    public double getWeightInKilos() {
        return WeightInKilos;
    }

    public void setWeightInKilos(double weightInKilos) {
        WeightInKilos = weightInKilos;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public double getWeightGoalMetric() {
        return WeightGoalMetric;
    }

    public void setWeightGoalMetric(double weightGoalMetric) {
        WeightGoalMetric = weightGoalMetric;
    }

    public double getCaloriesGoal() {
        return CaloriesGoal;
    }

    public void setCaloriesGoal(double caloriesGoal) {
        CaloriesGoal = caloriesGoal;
    }

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }
}
