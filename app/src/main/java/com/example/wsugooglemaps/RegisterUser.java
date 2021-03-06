package com.example.wsugooglemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
// create RegisterUser class
public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    // Create variables for layout items
    private TextView banner, registerUser;
    private EditText editTextName, editTextAge, editTextEmailAddress, editTextPassword;



    // Connected Firebase and declared instance of Firebase Auth
    private FirebaseAuth mAuth;

    // Create instance of the register user activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // initialize the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // initialize layout variables
        banner =(TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.Name);
        editTextAge = (EditText) findViewById(R.id.Age);
        editTextEmailAddress = (EditText) findViewById(R.id.EmailAddress);
        editTextPassword = (EditText) findViewById(R.id.Password2);

    }

    // When the login or register options are clicked function
    @Override
    public void onClick(View v) {
        // Create switch cases for Login and Register activity
        switch(v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;

        }

    }
    // create registerUser method
    private void registerUser() {
        // Convert user input to string into the corresponding variable
        String email = editTextEmailAddress.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();
        String Name = editTextName.getText().toString().trim();
        String Age = editTextAge.getText().toString().trim();

        // Validation if statements for if inputs are empty
        if (Name.isEmpty()) {
            editTextName.setError("Full Name is Required!");
            editTextName.requestFocus();
            return;
        }
        //age is required to be inputted
        if (Age.isEmpty()){
            editTextAge.setError("Age is Required!");
            editTextAge.requestFocus();
            return;

        }
        //Email address is required
        if (email.isEmpty()){
            editTextEmailAddress.setError("Email is Required!");
            editTextEmailAddress.requestFocus();
            return;
        }

        // Check for valid email
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmailAddress.setError("Please enter a valid email address!");
            editTextEmailAddress.requestFocus();
            return;
        }
        /* password required and will be saved and authenticated */
        if(Password.isEmpty()){
            editTextPassword.setError("Password is Required!");
            editTextPassword.requestFocus();
            return;

        }

        // Check for password length, cannot be less than 6 character. Firebase requires this.
        if (Password.length() < 6){
            editTextPassword.setError("Password must be a minimum of 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        // Call mAuth object for firebase authentication
        mAuth.createUserWithEmailAndPassword(email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {

                        // Check if user was registered successfully
                        if(task.isSuccessful()){
                            // create user object to store in database
                            User user = new User(Name, Age, email);

                            // Call Firebase Database object
                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://gps-android-ff91d-default-rtdb.firebaseio.com/");
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {

                                    // Check if user registration is successful or not
                                    // Output message letting user know
                                    if(task.isSuccessful()) {
                                        Toast.makeText(RegisterUser.this, "User Registration Successful!", Toast.LENGTH_LONG).show();

                                        // Redirect to login layout
                                    }
                                    else{
                                        // Output message if user registration failed
                                        Toast.makeText(RegisterUser.this, "User Registration Failed. Please Try Again.", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        } else{
                            // Output if the registration fails
                            Toast.makeText(RegisterUser.this, "User Registration Failed. Please Try Again.", Toast.LENGTH_LONG).show();

                        }

                    }
                });










        }

}