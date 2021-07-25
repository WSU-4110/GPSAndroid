package com.example.wsugooglemaps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        // Initialize variables
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
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.login:
                userLogin();
                break;

        }
    }
     // method for User Login
    private void userLogin() {
        // Get User credentials and convert it string
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Check if email field is empty
        if (email.isEmpty()){
            editTextEmail.setError("Email is Required!");
            editTextEmail.requestFocus();
            return;
         }
        // Check if email is valid
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email address!");
            editTextPassword.requestFocus();
            return;
        }

        // Check if password field is empty
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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    // Redirect to user profile
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Failed.", Toast.LENGTH_LONG).show();

                }
            }
        });
} }