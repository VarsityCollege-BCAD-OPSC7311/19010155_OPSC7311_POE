package com.jadwat.healthapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CurrentLevelsFragment extends Fragment {

    private DatabaseReference refOne, refTwo, refThree;
    String emailDB = MainActivity.shortEmail;
    BarChart WeightChart, CalorieChart;
    BarData WeightData, CalorieIntakeData;
    BarDataSet WeightCurrentDataSet, WeightTargetDataSet, CalorieCurrentDataSet, CalorieTargetDataSet;
    String measurement;
    String currentDate = HomeFragment.date;
    double weightGoal, weightCurrent, calorieGoal, calorieCurrent;
    ArrayList WeightEntries, CalorieEntries;

    public CurrentLevelsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_levels, container, false);

        refOne = FirebaseDatabase.getInstance().getReference("Users").child(emailDB).child("Weight Progress");
        refTwo = FirebaseDatabase.getInstance().getReference("Users").child(emailDB).child("User Information");
        refThree = FirebaseDatabase.getInstance().getReference("Users").child(emailDB).child("Calorie Intake");
        WeightChart = v.findViewById(R.id.BarWeight);
        CalorieChart = v.findViewById(R.id.BarCalories);

        getWeightValues();
        getCalorieValues();

        return v;
    }

    private void getWeightValues() {

        try {
            refOne.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (int i = 0; i < snapshot.getChildrenCount(); i++) {
                        int dayNo = i + 1;
                        weightCurrent = Double.parseDouble(snapshot.child("Day " + dayNo).child("weight").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Error", error.getMessage());
                }
            });


            refTwo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    measurement = snapshot.child("measurement").getValue().toString();
                    calorieGoal = Integer.parseInt(snapshot.child("caloriesGoal").getValue().toString());

                    if (measurement.equals("Metric")) {
                        weightGoal = Double.parseDouble(snapshot.child("weightGoalMetric").getValue().toString());
                        getWeightEntries();
                    }
                    else if (measurement.equals("Imperial")) {
                        weightGoal = Double.parseDouble(snapshot.child("weightGoalImperial").getValue().toString());
                        getWeightEntries();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Error", error.getMessage());
                }
            });

        }
        catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }

    }

    private void getCalorieValues() {

        try {

            refThree.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    calorieCurrent = Integer.parseInt(snapshot.child(currentDate).child("caloriesConsumed").getValue().toString());

                    getCalorieEntries();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Error", error.getMessage());
                }
            });

        }
        catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }


    private void getWeightEntries() {
        WeightEntries = new ArrayList<>();

        WeightEntries.add(new BarEntry(1f, (float)weightCurrent));
        WeightEntries.add(new BarEntry(2f, (float)weightGoal));

        LoadBarWeight();
    }

    private void getCalorieEntries() {
        CalorieEntries = new ArrayList<>();

        CalorieEntries.add(new BarEntry(1f, (float)calorieCurrent));
        CalorieEntries.add(new BarEntry(2f, (float)calorieGoal));

        LoadBarCalories();
    }

    private void LoadBarWeight() {

        WeightCurrentDataSet = new BarDataSet(WeightEntries, "Current Weight");
        WeightCurrentDataSet.setColors(Color.alpha(1));
        WeightTargetDataSet = new BarDataSet(WeightEntries, "Target Weight");
        WeightTargetDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        WeightData = new BarData(WeightCurrentDataSet, WeightTargetDataSet);

        WeightChart.setData(WeightData);
        WeightCurrentDataSet.setValueTextColor(Color.BLACK);
        WeightTargetDataSet.setValueTextColor(Color.BLACK);

        WeightCurrentDataSet.setValueTextSize(16f);
        WeightTargetDataSet.setValueTextSize(16f);

        String[] xLabels = new String[]{""};
        XAxis xAxis = WeightChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        WeightChart.getAxisLeft().setAxisMaximum(200);
        WeightChart.getAxisLeft().setLabelCount(4);

        WeightChart.getDescription().setText("");
        WeightChart.animateY(500);
        WeightChart.setFitBars(true);
    }

    private void LoadBarCalories() {

        CalorieCurrentDataSet = new BarDataSet(CalorieEntries, "Calories Consumed");
        CalorieCurrentDataSet.setColors(Color.alpha(1));
        CalorieTargetDataSet = new BarDataSet(CalorieEntries, "Target Calorie Intake");
        CalorieTargetDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        CalorieIntakeData = new BarData(CalorieCurrentDataSet, CalorieTargetDataSet);

        CalorieChart.setData(CalorieIntakeData);
        CalorieCurrentDataSet.setValueTextColor(Color.BLACK);
        CalorieTargetDataSet.setValueTextColor(Color.BLACK);

        CalorieCurrentDataSet.setValueTextSize(16f);
        CalorieTargetDataSet.setValueTextSize(16f);

        String[] xLabels = new String[]{""};
        XAxis xAxis = CalorieChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        CalorieChart.getDescription().setText("");
        CalorieChart.animateY(500);
        CalorieChart.setFitBars(true);
    }

}