package com.jadwat.healthapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import java.util.List;

public class WeightProgressFragment extends Fragment {

    private DatabaseReference ref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    List<String> weightList;
    ListView lstWeight;
    EditText txtAddWeight;
    Button AddWeight;
    ArrayAdapter adapter;
    LogWeightProgress wei;
    String emailDB = MainActivity.shortEmail;
    LogWeightProgress logWeight;

    public WeightProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weight_progress, container, false);

        lstWeight = v.findViewById(R.id.lstWeight);
        AddWeight = v.findViewById(R.id.btnAddWeight);
        txtAddWeight = v.findViewById(R.id.txtAddWeight);

        LoadWeight();

        AddWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddToDatabase();
            }
        });

        return v;
    }

    private void LoadWeight() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                wei = new LogWeightProgress();
                weightList = new ArrayList<String>();

                for (DataSnapshot weightDB : snapshot.getChildren()) {
                    wei = weightDB.getValue(LogWeightProgress.class);
                    weightList.add(wei.ToString());
                }
                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, weightList);

                lstWeight.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void AddToDatabase() {
        double weight = Double.parseDouble(txtAddWeight.getText().toString());

        myRef.child(emailDB).child("Weight Progress").child(HomeFragment.date).setValue(weight);
        LoadWeight();
    }


}