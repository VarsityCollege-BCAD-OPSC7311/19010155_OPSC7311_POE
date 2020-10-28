package com.jadwat.healthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserInformationScreen extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    ToggleButton tbMetric, tbImperial, tbMale, tbFemale;
    EditText txtFeet, txtInches, txtCentimetre, txtPounds, txtKilogram, txtAge, txtGoalCalories, txtGoalWeight;
    SeekBar sbCalories, sbWeight;
    Button btnContinue, btnBack;
    String gender, measurement, email;
    int age;
    double heightInCm, heightInFeet, heightInInches, weightGoal, caloriesGoal;
    public static double weight;

    UserInformationMet UserInformationMet;
    UserInformationImp UserInformationImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_screen);

        tbMetric = findViewById(R.id.tbMetric);
        tbImperial = findViewById(R.id.tbImperial);
        tbMale = findViewById(R.id.tbMale);
        tbFemale = findViewById(R.id.tbFemale);

        txtFeet = findViewById(R.id.txtFeet);
        txtInches = findViewById(R.id.txtInches);
        txtCentimetre = findViewById(R.id.txtCentimetres);
        txtPounds = findViewById(R.id.txtPounds);
        txtKilogram = findViewById(R.id.txtKilograms);
        txtAge = findViewById(R.id.txtAge);
        txtGoalWeight = findViewById(R.id.txtGoalWeight);
        txtGoalCalories = findViewById(R.id.txtGoalCalories);

        sbWeight = findViewById(R.id.sbWeight);
        sbCalories = findViewById(R.id.sbCalories);

        btnContinue = findViewById(R.id.btnCont);
        btnBack = findViewById(R.id.btnBack);

        measurement = "Metric";


        tbImperial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tbMetric.setChecked(false);
                    txtFeet.setVisibility(View.VISIBLE);
                    txtInches.setVisibility(View.VISIBLE);
                    txtPounds.setVisibility(View.VISIBLE);

                    txtCentimetre.setVisibility(View.INVISIBLE);
                    txtKilogram.setVisibility(View.INVISIBLE);

                    txtCentimetre.getText().clear();
                    txtKilogram.getText().clear();

                    txtGoalWeight.setText("0 lbs");

                    measurement = "Imperial";

                    sbWeight.setMax(500);
                }
            }
        });

        tbMetric.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tbImperial.setChecked(false);
                    txtCentimetre.setVisibility(View.VISIBLE);
                    txtKilogram.setVisibility(View.VISIBLE);

                    txtFeet.setVisibility(View.INVISIBLE);
                    txtInches.setVisibility(View.INVISIBLE);
                    txtPounds.setVisibility(View.INVISIBLE);

                    txtFeet.getText().clear();
                    txtInches.getText().clear();
                    txtPounds.getText().clear();

                    txtGoalWeight.setText("0 kgs");

                    measurement = "Metric";

                    sbWeight.setMax(300);
                }
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


        sbWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (tbImperial.isChecked()) {
                    txtGoalWeight.setText("" + i + " lbs");
                    weightGoal = i;
                } else if (tbMetric.isChecked()) {
                    txtGoalWeight.setText("" + i + " kgs");
                    weightGoal = i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //No code to add here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //No code to add here
            }
        });

        sbCalories.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtGoalCalories.setText("" + i + " kcals");
                caloriesGoal = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //No code to add here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //No code to add here
            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contToMainScreen();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }


    private void contToMainScreen() {
        email = MainActivity.shortEmail;
        age = Integer.parseInt(txtAge.getText().toString());

        if (email != null | heightInFeet != 0 | heightInInches != 0 | heightInCm != 0 |
                weight != 0 | age != 0 | gender != null | weightGoal != 0 | caloriesGoal != 0) {

            if (tbImperial.isChecked()) {
                heightInFeet = Double.parseDouble(txtFeet.getText().toString());
                heightInInches = Double.parseDouble(txtInches.getText().toString());
                weight = Double.parseDouble(txtPounds.getText().toString());

                UserInformationImp = new UserInformationImp(email, heightInFeet, heightInInches, weight, age, gender, weightGoal, caloriesGoal, measurement);

                myRef.child(email).child("User Information").setValue(UserInformationImp);

                startActivity(new Intent(getApplicationContext(), UserMainScreen.class));
            } else if (tbMetric.isChecked()) {
                heightInCm = Double.parseDouble(txtCentimetre.getText().toString());
                weight = Double.parseDouble(txtKilogram.getText().toString());

                UserInformationMet = new UserInformationMet(email, heightInCm, weight, age, gender, weightGoal, caloriesGoal, measurement);

                myRef.child(email).child("User Information").setValue(UserInformationMet);

                startActivity(new Intent(getApplicationContext(), UserMainScreen.class));
            }
        } else {
            Toast.makeText(UserInformationScreen.this, "Please ensure all fields are filled", Toast.LENGTH_SHORT).show();
        }
    }
}