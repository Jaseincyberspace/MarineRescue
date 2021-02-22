package com.example.marinerescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SpeedDistanceTimeCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_distance_time_calculator);

        final EditText ET_speed = findViewById(R.id.ET_speed);
        final EditText ET_distance = findViewById(R.id.ET_distance);
        final EditText ET_time = findViewById(R.id.ET_time);
        final Button B_findSpeed = findViewById(R.id.B_findSpeed);

        B_findSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                B_findSpeed.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorClickedButton));
                float speed = 0;
                float distance = 0;
                float time = 0;

                if (B_findSpeed.getText().equals(getResources().getString(R.string.reset))) {
                    ET_speed.getText().clear();
                    ET_distance.getText().clear();
                    ET_time.getText().clear();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            B_findSpeed.setText(getResources().getString(R.string.calculate));
                        }
                    }, 200);
                }
                else if (!ET_speed.getText().toString().equals("") && !ET_distance.getText().toString().equals("")) {
                    if (validateFloat("ET_speed") && validateFloat("ET_distance")) {
                        // Calculate Time
                        ET_time.setText(String.valueOf(calculateTime(Float.parseFloat(ET_speed.getText().toString()), Float.parseFloat(ET_distance.getText().toString()))));
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                B_findSpeed.setText(getResources().getString(R.string.reset));
                            }
                        }, 200);
                    }

                }
                else if (!ET_speed.getText().toString().equals("") && !ET_time.getText().toString().equals("")) {
                    if (validateFloat("ET_speed") && validateFloat("ET_time")) {
                        // Calculate Distance
                        ET_distance.setText(String.valueOf(calculateDistance(Float.parseFloat(ET_speed.getText().toString()), Float.parseFloat(ET_time.getText().toString()))));
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                B_findSpeed.setText(getResources().getString(R.string.reset));
                            }
                        }, 200);
                    }
                }
                else if (!ET_distance.getText().toString().equals("") && !ET_time.getText().toString().equals("")) {
                    if (validateFloat("ET_distance") && validateFloat("ET_time")) {
                        // Calculate Speed
                        ET_speed.setText(String.valueOf(calculateSpeed(Float.parseFloat(ET_distance.getText().toString()), Float.parseFloat(ET_time.getText().toString()))));
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                B_findSpeed.setText(getResources().getString(R.string.reset));
                            }
                        }, 200);
                    }
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.enter_two_of_three), Toast.LENGTH_LONG);
                    toast.setGravity(1, 0,700);
                    toast.show();
                }

                // Reset button background colour
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        B_findSpeed.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorButton));
                    }
                }, 180);
            }
        }); // End onClick listener

        // Hide on-screen keyboard when EditText fields lose focus
        ET_speed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });// End setOnFocusChangeListener

        ET_distance.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });// End setOnFocusChangeListener

        ET_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });// End setOnFocusChangeListener

    }// End onCreate Method

    public boolean validateFloat (String viewName) {
        EditText ET_speed = findViewById(R.id.ET_speed);
        EditText ET_distance = findViewById(R.id.ET_distance);
        EditText ET_time = findViewById(R.id.ET_time);

        if (viewName == "ET_speed") {
            try {
                Float.parseFloat(ET_speed.getText().toString());
                return true;
            }
            catch(Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.enter_speed), Toast.LENGTH_LONG);
                toast.setGravity(1, 0,700);
                toast.show();
                return false;
            }
        }
        else if (viewName == "ET_distance") {
            try {
                Float.parseFloat(ET_distance.getText().toString());
                return true;
            }
            catch(Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.enter_distance), Toast.LENGTH_LONG);
                toast.setGravity(1, 0,700);
                toast.show();
                return false;
            }
        }
        else if (viewName == "ET_time") {
            try {
                Float.parseFloat(ET_time.getText().toString());
                return true;
            }
            catch(Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.enter_time), Toast.LENGTH_LONG);
                toast.setGravity(1, 0,700);
                toast.show();
                return false;
            }
        }
        return true;
    }// End validateFloat method

    public float calculateSpeed (float distance, float time) {
        float speed = distance / time;
        return speed;
    }// End calculateSpeed method

    public float calculateDistance (float speed, float time) {
        float distance = speed * time;
        return distance;
    }// End calculateDistance method

    public float calculateTime (float speed, float distance) {
        float time = distance / speed;
        return time;
    }// End calculateTime method

    // Method to hide the on-screen keyboard when edit text fields lose their focus
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}// End SpeedDistanceTime class
