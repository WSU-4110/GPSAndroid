package com.example.wsugooglemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    // Declare map button variable
    private Button map;

    // declare the logout button variable
    private Button logout;

    // Create the instance of the profile activity when it is launched
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize the map variable
        map = (Button) findViewById(R.id.map);
        map.setOnClickListener(this);

        // Initialize the logout button variable
        logout = (Button) findViewById(R.id.logout);

        // When Logout button is clicked
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call the function to signout
                FirebaseAuth.getInstance().signOut();
                // send the user to the Login activity
                // back to the login screen
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        // When button is clicked go to MainActivity which is the Map itself
        // use a switch case to launch the main activity if the button is clicked
        switch (v.getId()){
            case R.id.map:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }
}