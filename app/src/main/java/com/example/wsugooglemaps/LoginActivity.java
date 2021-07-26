package com.example.wsugooglemaps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    // Declare variables from login activity xml layout page
    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button login;
    private FirebaseAuth mAuth;

    // create Instance State for launching activity and utilizing the links and buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Initialize all the variables
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.Email);
        editTextPassword = (EditText) findViewById(R.id.Password);

        mAuth = FirebaseAuth.getInstance();
    }



    // Create onClick method to start layout when clicked in APP
    @Override
    public void onClick(View v) {
        // switch case for when register is clicked on or when login is clicked on
        switch (v.getId()){
            case R.id.register:
                // start register user activity if register is clicked
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.login:
                // call user login method if login is clicked
                userLogin();
                break;

        }
    }
     // method for User Login
    private void userLogin() {
        // Get User credentials and convert it string
        // Use trim function to eliminate leading and trailing spaces
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Check if email field is empty
        // Output that an email is required if it is
        if (email.isEmpty()){
            editTextEmail.setError("Email is Required!");
            editTextEmail.requestFocus();
            return;
         }
        // Check if email is valid
        // If email is invalid output text saying it is required
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email address!");
            editTextPassword.requestFocus();
            return;
        }

        // Check if password field is empty
        // Output text that it is required if it is empty
        if(password.isEmpty()){
            editTextPassword.setError("Password is Required!");
            editTextPassword.requestFocus();
            return;
        }

        // Check password length
        if(password.length() < 6){
            editTextPassword.setError("The minimum password length is 6 characters.");
            editTextPassword.requestFocus();
            return;
        }
        // call mAuth object to run sign in task
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                // If statement ot check if task was successful
                if(task.isSuccessful()) {
                    // Redirect to user profile
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                }
                else{
                    // Else output text that Login failed
                    Toast.makeText(LoginActivity.this, "Login Failed.", Toast.LENGTH_LONG).show();

                }
            }
        });
} }