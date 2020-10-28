package com.jadwat.healthapp;

public class UserInformationImp {
    private String Email;
    private double HeightInFeet;
    private double HeightInInches;
    private double WeightInPounds;
    private int Age;
    private String Gender;
    private double WeightGoalImperial;
    private double CaloriesGoal;
    private String Measurement;

    public UserInformationImp() {
    }

    public UserInformationImp(String email, double heightInFeet, double heightInInches, double weightInPounds, int age, String gender, double weightGoalImperial, double caloriesGoal, String measurement) {
        Email = email;
        HeightInFeet = heightInFeet;
        HeightInInches = heightInInches;
        WeightInPounds = weightInPounds;
        Age = age;
        Gender = gender;
        WeightGoalImperial = weightGoalImperial;
        CaloriesGoal = caloriesGoal;
        Measurement = measurement;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public double getHeightInFeet() {
        return HeightInFeet;
    }

    public void setHeightInFeet(double heightInFeet) {
        HeightInFeet = heightInFeet;
    }

    public double getHeightInInches() {
        return HeightInInches;
    }

    public void setHeightInInches(double heightInInches) {
        HeightInInches = heightInInches;
    }

    public double getWeightInPounds() {
        return WeightInPounds;
    }

    public void setWeightInPounds(double weightInPounds) {
        WeightInPounds = weightInPounds;
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

    public double getWeightGoalImperial() {
        return WeightGoalImperial;
    }

    public void setWeightGoalImperial(double weightGoalImperial) {
        WeightGoalImperial = weightGoalImperial;
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
