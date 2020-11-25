package com.jadwat.healthapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WeightProgressFragment extends Fragment {

    private DatabaseReference ref, ref2;
    String emailDB = MainActivity.shortEmail;
    EditText txtAddWeight;
    ListView List;
    Button btnAddWeight;
    String currentDate = HomeFragment.date;
    String dates, measurement;
    double weight;
    int numOfDays;

    public WeightProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weight_progress, container, false);

        List = v.findViewById(R.id.WeightListView);
        txtAddWeight = v.findViewById(R.id.txtAddWeight);
        btnAddWeight = v.findViewById(R.id.btnAddWeight);
        ref = FirebaseDatabase.getInstance().getReference("Users").child(emailDB).child("Weight Progress");
        ref2 = FirebaseDatabase.getInstance().getReference("Users").child(emailDB).child("User Information");

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                measurement = snapshot.child("measurement").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", error.getMessage());
            }
        });

        LoadInfo();

        btnAddWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddWeight(numOfDays);
            }
        });

        return v;
    }

    private void LoadInfo() {

        final ArrayList<String> arrayList = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (int i = 0; i < snapshot.getChildrenCount(); i++) {

                    String disp;
                    int dayNo = i + 1;
                    dates = snapshot.child("Day " + dayNo).child("date").getValue().toString();
                    weight = Double.parseDouble(snapshot.child("Day " + dayNo).child("weight").getValue().toString());

                    if (measurement.equals("Imperial")) {
                        disp = "Day " + dayNo + "\n" +
                                "Date: " + dates + "\n" +
                                "Weight: " + weight + " lbs";
                        arrayList.add(disp);
                    }
                    else if (measurement.equals("Metric")) {
                        disp = "Day " + dayNo + "\n" +
                                "Date: " + dates + "\n" +
                                "Weight: " + weight + " kg";
                        arrayList.add(disp);
                    }
                    numOfDays = Integer.parseInt(String.valueOf(snapshot.getChildrenCount()));

                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                List.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", error.getMessage());
            }
        });

    }

    private void AddWeight(int num) {

        if (dates.equals(currentDate)) {
            String newWeight = txtAddWeight.getText().toString();
            ref.child("Day " + num).child("date").setValue(currentDate);
            ref.child("Day " + num).child("weight").setValue(newWeight);
        }
        else {
            int newNum = num + 1;
            String newWeight = txtAddWeight.getText().toString();
            ref.child("Day " + newNum).child("date").setValue(currentDate);
            ref.child("Day " + newNum).child("weight").setValue(newWeight);
        }
        LoadInfo();
    }
}


