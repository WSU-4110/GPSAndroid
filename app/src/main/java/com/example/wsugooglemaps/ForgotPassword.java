package com.example.wsugooglemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

import org.jetbrains.annotations.NotNull;

// Class for Forgot Password activity
public class ForgotPassword extends AppCompatActivity {

    // Declare Variables
    private Button resetPasswordB;
    private EditText enterEmail;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize variables
        resetPasswordB = (Button) findViewById(R.id.resetPassword);
        enterEmail = (EditText) findViewById(R.id.enterEmail);
        auth = FirebaseAuth.getInstance();

        // Set for activity to start on click
        resetPasswordB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call reset password method
                resetPassword();
            }
        });

    }

    // Reset password method
    private void resetPassword() {
        // Get text input convert to string and into variable
        // trim function to eliminate trailing or leading spaces
        String email = enterEmail.getText().toString().trim();

        // Validation statements for email
        // Check if email is empty
        // Output to screen that it is required
        if(email.isEmpty()){
        enterEmail.setError("Email address is required!");
        enterEmail.requestFocus();
        return;
    }
        // Check if valid email is entered
        // If email pattern is not a match for a valid email then throw error
        // Output to screen to user
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            enterEmail.setError("Please enter a valid email address.");
            enterEmail.requestFocus();
            return;
        }

        // call Firebase Auth object to send email to user from Firebase database
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                // if the email is sent let user know
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Please check your email to reset your password", Toast.LENGTH_LONG).show();
                }
                // Else if it doesn't output Error and let user know to try again
                else {
                    Toast.makeText(ForgotPassword.this, "ERROR. Please Try Again.", Toast.LENGTH_LONG).show();
                }

            }
        });
} }