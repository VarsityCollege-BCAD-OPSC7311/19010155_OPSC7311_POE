package com.jadwat.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public EditText email, password;
    Button login, register;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    public static String shortEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginMethod();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationScreen.class));
            }
        });
    }

    private void loginMethod() {
        final String enteredEmail = email.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();

        if (enteredEmail != null || enteredPassword != null) {
            mAuth.signInWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Logged in " + mAuth.getCurrentUser().getEmail() + " successfully", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), UserInformationScreen.class));
                        ShortenEmail();
                    } else {
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Please ensure all fields are filled", Toast.LENGTH_SHORT).show();
        }
    }

    private void ShortenEmail() {
        String e = email.getText().toString().trim();

        //-----------------------------------------code attribution------------------------------------------
        //Author:   Eric Conner
        //Link:     https://stackoverflow.com/questions/5695623/remove-characters-from-a-string-in-java
        e = e.replace(".com", "");
        //-----------------------------------------------end-------------------------------------------------

        shortEmail = e;
    }

    //@Override
    //protected void onStart() {
    //    super.onStart();

    //    if (currentUser != null) {
    //        Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show();

    //        startActivity(new Intent(getApplicationContext(), UserMainScreen.class));
    //        ShortenEmail();
    //    } else {
    //        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    //    }
    //}
}