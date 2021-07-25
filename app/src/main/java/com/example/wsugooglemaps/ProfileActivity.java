package com.example.wsugooglemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    // Declare map button variable
    private Button map;

    // Create the instance of the profile activity when it is launched
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize the map variable
        map = (Button) findViewById(R.id.map);
        map.setOnClickListener(this);
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