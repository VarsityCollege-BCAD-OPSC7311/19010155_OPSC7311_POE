package com.jadwat.healthapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private DatabaseReference ref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    ProgressBar pbCalories;
    Button btnCalc;
    EditText txtCalories;
    TextView tvProgress, tvDailyCal, tvCalConsumed, tvDateDisplay;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    public static String date;
    String emailDB = MainActivity.shortEmail;
    int caloriesFromDB, caloriesLeft, caloriesUsed;
    boolean check = false;

    LogCalorieIntake cal;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        pbCalories = v.findViewById(R.id.pbCalories);
        btnCalc = v.findViewById(R.id.btnCalc);
        txtCalories = v.findViewById(R.id.txtCalories);
        tvProgress = v.findViewById(R.id.tvProgress);
        tvDailyCal = v.findViewById(R.id.tvDailyCal);
        tvCalConsumed = v.findViewById(R.id.tvCalConsumed);
        tvDateDisplay = v.findViewById(R.id.tvCurrentDate);

        calendar = Calendar.getInstance();

        ref = FirebaseDatabase.getInstance().getReference("Users").child(emailDB).child("User Information");

        InitialDisplay();

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check) {
                    calcPro();
                } else {
                    calcPre();
                }
            }
        });

        myRef.child(emailDB).child("Weight Progress").child(date).setValue(UserInformationScreen.weight);

        return v;
    }

    private void calcPre() {
        caloriesUsed = Integer.parseInt(txtCalories.getText().toString());

        caloriesLeft = caloriesFromDB - caloriesUsed;

        tvProgress.setText(caloriesLeft + "");
        tvCalConsumed.setText(caloriesUsed + "");

        pbCalories.setProgress(caloriesUsed);

        check = true;
    }

    private void calcPro() {
        int calories = Integer.parseInt(txtCalories.getText().toString());

        caloriesLeft -= calories;

        caloriesUsed += calories;

        tvProgress.setText(caloriesLeft + "");
        tvCalConsumed.setText(caloriesUsed + "");

        pbCalories.setProgress(caloriesUsed);

        cal = new LogCalorieIntake(date, caloriesUsed);
        myRef.child(emailDB).child("Calorie Intake").child(date).setValue(cal);
    }


    private void InitialDisplay() {
        DisplayCalorieProgress();
        DisplayDate();
    }

    private void DisplayCalorieProgress() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                caloriesFromDB = Integer.parseInt(snapshot.child("caloriesGoal").getValue().toString());

                if (caloriesFromDB == 0) {
                    Toast.makeText(getContext(), "Error loading details, try again later", Toast.LENGTH_SHORT).show();
                } else {
                    tvProgress.setText(caloriesFromDB + "");
                    tvDailyCal.setText(caloriesFromDB + "");
                    check = false;
                    pbCalories.setMax(caloriesFromDB);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error loading details, try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void StoreCalorieIntake() {

    }

    //----------------------------------------code attribution----------------------------------------
    //Author:   Shay Ma
    //Link:     https://medium.com/@shayma_92409/display-the-current-date-android-studio-f582bf14f908
    private void DisplayDate() {
        dateFormat = new SimpleDateFormat("dd MMM yyyy");
        date = dateFormat.format(calendar.getTime());
        tvDateDisplay.setText(date);
    }
    //----------------------------------------------end-----------------------------------------------
}