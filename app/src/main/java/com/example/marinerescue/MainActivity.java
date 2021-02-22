package com.example.marinerescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    // Instantiate Leeway database
    public static DriftObjectLeewayDatabase driftObjectLeewayDatabase;
    public static ArrayList<DriftObject> driftObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Format Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Construct the Leeway database.
        Migration migration_1_2 = new Migration(1,2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL("DROP TABLE IF EXISTS 'DriftObjectLeeway'");
                database.execSQL("CREATE TABLE IF NOT EXISTS 'DriftObjectLeeway'('DriftObjectLeewayID' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 'Category' TEXT, 'SubCategory' TEXT, 'PrimaryDescriptor' TEXT,'SecondaryDescriptor' TEXT, 'SpeedMultiplier' REAL NOT NULL, 'SpeedModifier' REAL NOT NULL, 'DivergenceAngle' REAL NOT NULL)");
            }
        };

        // TODO: Learn how to deal with multiple threads and change this so that the database does not run on the main thread
        driftObjectLeewayDatabase = Room.databaseBuilder(getApplicationContext(), DriftObjectLeewayDatabase.class, "DriftObjectLeewayDB").allowMainThreadQueries().addMigrations(migration_1_2).fallbackToDestructiveMigration().build();

        // Clear any old objects from the database
        // driftObjectLeewayDatabase.driftObjectLeewayDao().deleteAllDriftObjects();

        // Get drift object leeway data from .csv file and create driftObjects in DriftObjectLeeway database.
        getDriftObjects();

        // Create a list of drift objects from .csv file
        generateDriftObjects();

        // Find Views
        final TextView TV_leeway = findViewById(R.id.TV_leeway);
        final Button B_calcSpeedDistanceTime = findViewById(R.id.B_calcSpeedDistanceTime);
        final Button B_calcDriftVector = findViewById(R.id.B_calcDriftVector);

        // Button Handlers
        B_calcSpeedDistanceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                B_calcSpeedDistanceTime.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorClickedButton));
                Intent i = new Intent(getApplicationContext(), SpeedDistanceTimeCalculator.class);
                startActivity(i);
            }
        });// End onClickListener

        B_calcDriftVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                B_calcDriftVector.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorClickedButton));
                Intent i = new Intent(getApplicationContext(), DriftVectorCalculator.class);
                startActivity(i);
            }
        });// End onClickListener

    } // End onCreate method

    private void getDriftObjects() {

        InputStream inputStream = getResources().openRawResource(R.raw.leeway_table);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String row = "";

        String driftObjectsInDatabase = "";

        try {
            // Skip over the first line which holds column titles
            reader.readLine();
            while((row = reader.readLine()) != null) {

                // Split line into columns using commas as delimiters
                String[] columns = row.split(",");

                // Read the data on each line
                DriftObjectLeeway driftObject = new DriftObjectLeeway();
                driftObject.setCategory(columns[0]);
                driftObject.setSubCategory(columns[1]);
                driftObject.setPrimaryDescriptor(columns[2]);
                driftObject.setSecondaryDescriptor(columns[3]);
                driftObject.setSpeedMultiplier(Double.parseDouble(columns[4]));
                driftObject.setSpeedModifier(Double.parseDouble(columns[5]));
                driftObject.setDivergenceAngle(Double.parseDouble(columns[6]));
                driftObjectLeewayDatabase.driftObjectLeewayDao().addDriftObjectLeeway(driftObject);

                driftObjectsInDatabase += driftObject.getDriftObjectLeewayID() + "\n";
            }
            Log.wtf("MainActivityLog",driftObjectsInDatabase);
        }
        catch (Exception e) {
            Log.wtf("MainActivity", "Error reading csv file on line " + row, e);
        }
    }

    private void generateDriftObjects() {

        InputStream inputStream = getResources().openRawResource(R.raw.leeway_table);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String row = "";

        try {
            // Skip over the first line which holds column titles
            reader.readLine();
            while((row = reader.readLine()) != null) {

                // Split line into columns using commas as delimiters
                String[] columns = row.split(",");

                // Read the data on each line
                DriftObject driftObject = new DriftObject(columns[0], columns[1], columns[2], columns[3], Double.parseDouble(columns[4]), Double.parseDouble(columns[5]), Double.parseDouble(columns[6]));
                driftObjects.add(driftObject);

            }
        }
        catch (Exception e) {
            Log.wtf("MainActivity", "Error reading csv file on line " + row, e);
        }
    }

} // End MainActivity class
