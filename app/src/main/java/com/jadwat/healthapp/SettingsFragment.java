package com.jadwat.healthapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SettingsFragment extends Fragment {

    DatabaseReference ref;
    TextView Email;
    EditText txtAge, txtHeight, txtHeightFt, txtHeightIn, txtWeightGoal, txtCaloriesGoal;
    Button Edit, Save, Logout;
    ToggleButton tbMale, tbFemale;
    String email, gender, measurement;
    String emailDB = MainActivity.shortEmail;
    int age, calories;
    double height, heightInches, weightGoal;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Email = v.findViewById(R.id.tvSettingsEmail);
        txtAge = v.findViewById(R.id.txtSettingsAge);
        tbMale = v.findViewById(R.id.tbSettingsMale);
        tbFemale = v.findViewById(R.id.tbSettingsFemale);
        txtHeight = v.findViewById(R.id.txtSettingsHeightMet);
        txtWeightGoal = v.findViewById(R.id.txtSettingsWeightGoal);
        txtCaloriesGoal = v.findViewById(R.id.txtSettingsCaloriesGoal);
        txtHeightFt = v.findViewById(R.id.txtSettingsHeightFt);
        txtHeightIn = v.findViewById(R.id.txtSettingsHeightIn);
        Edit = v.findViewById(R.id.btnEdit);
        Save = v.findViewById(R.id.btnSave);
        Logout = v.findViewById(R.id.btnLogout);
        ref = FirebaseDatabase.getInstance().getReference("Users").child(emailDB).child("User Information");

        LoadInfo();
        DisableComponents();

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnableComponents();
            }
        });

        tbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    gender = tbMale.getText().toString();

                    tbFemale.setChecked(false);
                }
            }
        });

        tbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    gender = tbFemale.getText().toString();

                    tbMale.setChecked(false);
                }
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeInfo();
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return v;
    }

    private void LoadInfo() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                email = snapshot.child("email").getValue().toString();
                age = Integer.parseInt(snapshot.child("age").getValue().toString());
                gender = snapshot.child("gender").getValue().toString();
                calories = Integer.parseInt(snapshot.child("caloriesGoal").getValue().toString());
                measurement = snapshot.child("measurement").getValue().toString();

                if (gender.equals("Male")) {
                    tbMale.setChecked(true);
                }
                else if(gender.equals("Female")) {
                    tbFemale.setChecked(true);
                }

                Email.setText(email);
                txtAge.setText(age + "");
                txtCaloriesGoal.setText(calories + "");

                if (measurement.equals("Metric")) {
                    txtHeight.setVisibility(View.VISIBLE);
                    txtHeightFt.setVisibility(View.INVISIBLE);
                    txtHeightIn.setVisibility(View.INVISIBLE);

                    height = Double.parseDouble(snapshot.child("heightInCm").getValue().toString());
                    weightGoal = Double.parseDouble(snapshot.child("weightGoalMetric").getValue().toString());

                    txtHeight.setText(height + "");
                    txtWeightGoal.setText(weightGoal + "");

                    
                } else if (measurement.equals("Imperial")) {
                    txtHeightFt.setVisibility(View.VISIBLE);
                    txtHeightIn.setVisibility(View.VISIBLE);
                    txtHeight.setVisibility(View.INVISIBLE);
                    txtHeight.setText("");

                    height = Double.parseDouble(snapshot.child("heightInFeet").getValue().toString());
                    heightInches = Double.parseDouble(snapshot.child("heightInInches").getValue().toString());
                    weightGoal = Double.parseDouble(snapshot.child("weightGoalImperial").getValue().toString());

                    txtHeightFt.setText(height + "");
                    txtHeightIn.setText(heightInches + "");
                    txtWeightGoal.setText(weightGoal + "");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private void ChangeInfo() {
        if (measurement.equals("Metric")) {
            height = Double.parseDouble(txtHeight.getText().toString());
            age = Integer.parseInt(txtAge.getText().toString());
            weightGoal = Double.parseDouble(txtWeightGoal.getText().toString());
            calories = Integer.parseInt(txtCaloriesGoal.getText().toString());

            ref.child("heightInCm").setValue(height);
            ref.child("age").setValue(age);
            ref.child("gender").setValue(gender);
            ref.child("weightGoalMetric").setValue(weightGoal);
            ref.child("caloriesGoal").setValue(calories);


        } else if (measurement.equals("Imperial")) {
            height = Double.parseDouble(txtHeightFt.getText().toString());
            heightInches = Double.parseDouble(txtHeightIn.getText().toString());
            age = Integer.parseInt(txtAge.getText().toString());
            weightGoal = Double.parseDouble(txtWeightGoal.getText().toString());
            calories = Integer.parseInt(txtCaloriesGoal.getText().toString());

            ref.child("heightInFeet").setValue(height);
            ref.child("heightInInches").setValue(heightInches);
            //ref.child("weightInPounds").setValue(weight);
            ref.child("age").setValue(age);
            ref.child("gender").setValue(gender);
            ref.child("weightGoalImperial").setValue(weightGoal);
            ref.child("caloriesGoal").setValue(calories);
        }
    }

    private void DisableComponents() {
        txtAge.setEnabled(false);
        txtHeight.setEnabled(false);
        txtWeightGoal.setEnabled(false);
        txtCaloriesGoal.setEnabled(false);
        txtHeightFt.setEnabled(false);
        txtHeightIn.setEnabled(false);

        tbMale.setEnabled(false);
        tbFemale.setEnabled(false);

        txtHeightFt.setVisibility(View.INVISIBLE);
        txtHeightIn.setVisibility(View.INVISIBLE);

        Save.setEnabled(false);
        Save.setVisibility(View.INVISIBLE);
    }

    private void EnableComponents() {
        txtAge.setEnabled(true);
        txtWeightGoal.setEnabled(true);
        txtCaloriesGoal.setEnabled(true);
        Save.setEnabled(true);
        Save.setVisibility(View.VISIBLE);

        tbMale.setEnabled(true);
        tbFemale.setEnabled(true);

        if (measurement.equals("Metric")) {
            txtHeight.setEnabled(true);
        } else if (measurement.equals("Imperial")) {
            txtHeightFt.setEnabled(true);
            txtHeightIn.setEnabled(true);
        }
    }
}