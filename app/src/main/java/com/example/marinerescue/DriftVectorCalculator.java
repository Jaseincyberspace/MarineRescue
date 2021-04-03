package com.example.marinerescue;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.beanutils.NestedNullException;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.marinerescue.MainActivity.driftObjectLeewayDatabase;

public class DriftVectorCalculator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String TAG = "DriftVectorCalculatorActivity";

    // Record spinner selections
    public String windDirection;
    public String category;
    public String subCategory;
    public String primaryDescriptor;
    public String secondaryDescriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drift_vector_calculator);

        // Find Views
        final EditText ET_windSpeed = findViewById(R.id.ET_windSpeed);
        final Spinner SP_windDirection = (Spinner) findViewById(R.id.SP_windDirection);
        final EditText ET_currentSpeed = findViewById(R.id.ET_currentSpeed);
        final EditText ET_currentDirection = findViewById(R.id.ET_currentDirection);
        final EditText ET_elapsedTimeHrs = findViewById(R.id.ET_elapsedTimeHrs);
        final EditText ET_elapsedTimeMins = findViewById(R.id.ET_elapsedTimeMins);
        final Spinner SP_category = (Spinner) findViewById(R.id.SP_category);
        final Spinner SP_subCategory = (Spinner) findViewById(R.id.SP_subCategory);
        final Spinner SP_description = (Spinner) findViewById(R.id.SP_description);
        final Spinner SP_secondaryDescription = (Spinner) findViewById(R.id.SP_secondaryDescription);
        Button B_calculateDriftVector = findViewById(R.id.B_calculateDriftVector);

        // Generate a list of driftObjects from the database
        final List<DriftObjectLeeway> DriftObjects = MainActivity.driftObjectLeewayDatabase.driftObjectLeewayDao().getAllDriftObjects();

        // SPINNER HANDLER - Deals with the windDirection spinner and the categories spinner, the subsequent ones are dealt with in the onItemSelected method
        // windDirection Spinner click listener
        SP_windDirection.setOnItemSelectedListener(this);

        // Generate dropdown elements
        List<String> windDirections = new ArrayList<String>();
        windDirections.add("N");
        windDirections.add("NNE");
        windDirections.add("NE");
        windDirections.add("ENE");
        windDirections.add("E");
        windDirections.add("ESE");
        windDirections.add("SE");
        windDirections.add("SSE");
        windDirections.add("S");
        windDirections.add("SSW");
        windDirections.add("SW");
        windDirections.add("WSW");
        windDirections.add("W");
        windDirections.add("WNW");
        windDirections.add("NW");
        windDirections.add("NNW");

        // Create spinner adapter
        ArrayAdapter<String> windDirectionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, windDirections);
        // Dropdown layout style (There are different spinner styles you can choose from, or you can create your own custom dropdown layout)
        windDirectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter to spinner
        SP_windDirection.setAdapter(windDirectionAdapter);

        // categories Spinner click listener
        SP_category.setOnItemSelectedListener(this);

        // Generate dropdown list elements
        List<String> categories = new ArrayList<String>();
        for(DriftObjectLeeway driftObject : DriftObjects) {
            if(!categories.contains(driftObject.getCategory())) {
                categories.add(driftObject.getCategory());
            }
        }
        // Create spinner adapter
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Dropdown layout style (There are different spinner styles you can choose from, or you can create your own custom dropdown layout)
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attach adapter to spinner
        SP_category.setAdapter(categoriesAdapter);

        // Hide on-screen keyboard when EditText fields lose focus
        ET_windSpeed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });// End setOnFocusChangeListener

        ET_currentSpeed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });// End setOnFocusChangeListener

        ET_currentDirection.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });// End setOnFocusChangeListener

        ET_elapsedTimeHrs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });// End setOnFocusChangeListener

        ET_elapsedTimeMins.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });// End setOnFocusChangeListener

        // Button Handler
        B_calculateDriftVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotalDriftVector();
            }
        });// End onClick method

    }// End onCreate method

    // Calculate total drift vector, search area radius and divergence angle
    public String calculateTotalDriftVector() {
        // Find views
        final EditText ET_windSpeed = findViewById(R.id.ET_windSpeed);
        final Spinner SP_windDirection = (Spinner) findViewById(R.id.SP_windDirection);
        final EditText ET_currentSpeed = findViewById(R.id.ET_currentSpeed);
        final EditText ET_currentDirection = findViewById(R.id.ET_currentDirection);
        final EditText ET_elapsedTimeHrs = findViewById(R.id.ET_elapsedTimeHrs);
        final EditText ET_elapsedTimeMins = findViewById(R.id.ET_elapsedTimeMins);
        final Spinner SP_category = (Spinner) findViewById(R.id.SP_category);
        final Spinner SP_subCategory = (Spinner) findViewById(R.id.SP_subCategory);
        final Spinner SP_description = (Spinner) findViewById(R.id.SP_description);
        final Spinner SP_secondaryDescription = (Spinner) findViewById(R.id.SP_secondaryDescription);

        // If any of these fields are not updated by the user then the value stays at -1 and it will fail validation.
        double windSpeed = -1;
        double windVectorAngle = -1;
        double currentSpeed = -1;
        double currentVectorAngle = -1;
        double decimalHours = -1;

        // Set vars for the type of drift object selected by the user. (Defaults to 'Person In Water)
        DriftObjectLeeway driftObject = driftObjectLeewayDatabase.driftObjectLeewayDao().getDriftObjectLeeway(category, subCategory, primaryDescriptor, secondaryDescriptor);
        double speedMultiplier = driftObject.getSpeedMultiplier();
        double speedModifier = driftObject.getSpeedModifier();
        double divergenceAngle = driftObject.getDivergenceAngle();
        double leeway = 0;

        // VALIDATE USER INPUT:
        // Wind direction is a spinner so there is no invalid value possible.
        // Wind angle has 180 degrees either added to or subtracted from it to make it the angle the wind is blowing 'to' rather than blowing 'from'
        switch (windDirection) {
            case "N":
                windVectorAngle = 0 + 180;
                break;
            case "NNE":
                windVectorAngle = 22.5 + 180;
                break;
            case "NE":
                windVectorAngle = 45 + 180;
                break;
            case "ENE":
                windVectorAngle = 67.5 + 180;
                break;
            case "E":
                windVectorAngle = 90 + 180;
                break;
            case "ESE":
                windVectorAngle = 112.5 + 180;
                break;
            case "SE":
                windVectorAngle = 135 + 180;
                break;
            case "SSE":
                windVectorAngle = 157.5 + 180;
                break;
            case "S":
                windVectorAngle = 180 + 180;
                break;
            case "SSW":
                windVectorAngle = 202.5 - 180;
                break;
            case "SW":
                windVectorAngle = 225 - 180;
                break;
            case "WSW":
                windVectorAngle = 247.5 - 180;
                break;
            case "W":
                windVectorAngle = 270 - 180;
                break;
            case "WNW":
                windVectorAngle = 292.5 - 180;
                break;
            case "NW":
                windVectorAngle = 315 - 180;
                break;
            case "NNW":
                windVectorAngle = 337.5 - 180;
                break;
        }// End switch


        try {
            windSpeed = Double.parseDouble(ET_windSpeed.getText().toString());
            leeway = ((windSpeed * speedMultiplier) + speedModifier);
        } catch (NumberFormatException e) {
            Log.w(TAG, "Wind speed value is invalid");
        }
        try {
            currentSpeed = Double.parseDouble(ET_currentSpeed.getText().toString());
        }
        catch (NumberFormatException e) {
            Log.w(TAG, "Current speed value is invalid");
        }
        try {
            currentVectorAngle = Double.parseDouble(ET_currentDirection.getText().toString());
        }
        catch (NumberFormatException e) {
            Log.w(TAG, "Current direction value is invalid");
        }
        try {
            /* Formula to convert elapsed time to decimal hours:
             * decimalHours = (hours) + (minutes / 60)
             * e.g. (1 hour 45 minutes) = 1 + (45/60)
             */
            decimalHours = Double.parseDouble(ET_elapsedTimeHrs.getText().toString()) + ((Double.parseDouble(ET_elapsedTimeMins.getText().toString()) / 60));
        }
        catch (NumberFormatException e) {
            Log.w(TAG, "Elapsed time value is invalid");
        }
        if(windSpeed == -1) {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_enter_a_wind_speed), Toast.LENGTH_LONG);
            toast.show();
        }
        else if(currentSpeed == -1) {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_enter_current_speed), Toast.LENGTH_LONG);
            toast.show();
        }
        else if(currentVectorAngle == -1) {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_enter_current_direction), Toast.LENGTH_LONG);
            toast.show();
        }
        else if(decimalHours == -1) {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_enter_elapsed_time), Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            // If it gets to here it has passed validation so a total drift vector is calculated and composed into a string...

            // CALCULATIONS:
            // Set magnitude and angle for each of the two vectors
            double windVectorMagnitude = (leeway) * decimalHours;
            windVectorAngle = windVectorAngle;

            double currentVectorMagnitude = currentSpeed * decimalHours;
            currentVectorAngle = currentVectorAngle;

            // Convert to cartesian points
            double windVector_X = windVectorMagnitude * Math.cos(windVectorAngle * Math.PI/180);
            double currentVector_X = currentVectorMagnitude * Math.cos(currentVectorAngle * Math.PI/180);
            double windVector_Y = windVectorMagnitude * Math.sin(windVectorAngle * Math.PI/180);
            double currentVector_Y = currentVectorMagnitude * Math.sin(currentVectorAngle * Math.PI/180);

            // Add components together to find the sum of the vectors in cartesian form
            double totalVector_X = windVector_X + currentVector_X;
            double totalVector_Y = windVector_Y + currentVector_Y;

            // Convert the resultant vector back to polar form. This is the Total Drift Vector (TDV)
            double TDV_Magnitude = Math.sqrt(Math.pow(totalVector_X, 2) + Math.pow(totalVector_Y, 2));
            double TDV_Angle = Math.atan2(totalVector_Y, totalVector_X);
            TDV_Angle = TDV_Angle * (180/Math.PI);

            // Deal with negative values
            if(TDV_Angle < 0) {
                TDV_Angle = TDV_Angle + 360;
            }

            // Calculate the size of the search area (Radius (in nautical miles) with it's centre point at the end of the TDV)
            double searchArea = 0;
            // if the total drift vector is less than 8 nautical miles then the search area is a circle with a radius of 6 nautical miles.
            if(TDV_Magnitude < 8) {
                searchArea = 6;
            }
            // if the total drift vector is greater than 8 nautical miles then the search area is a circle with a radius of: (TDV_magnitude/8) + 6 nautical miles.
            else {
                searchArea = (TDV_Magnitude/8) + 6;
            }

            // Generate strings that will be displayed in a dialog box with the Display Total Drift Vector (TDV) and the input factors that were used to calculate it.
            // TDV_Magnitude = Math.round(TDV_Magnitude);
            DecimalFormat formatter = new DecimalFormat("#.##");
            String formattedTDVMagnitude = formatter.format(TDV_Magnitude);
            String formattedTDVAngle = formatter.format(TDV_Angle);
            String formattedLeeway = formatter.format(leeway);
            String formattedSearchArea = formatter.format(searchArea);

            String driftObjectCharacteristics;
            if(primaryDescriptor.contentEquals("N/A")) {
                driftObjectCharacteristics = category + ", " + subCategory;
            }
            else if(secondaryDescriptor.contentEquals("N/A")) {
                driftObjectCharacteristics = category + ", " + subCategory + ", " + primaryDescriptor;
            }
            else {
                driftObjectCharacteristics = category + ", " + subCategory + ", " + primaryDescriptor + ", " + secondaryDescriptor;
            }

            String elapsedTimeHrs = ET_elapsedTimeHrs.getText().toString();
            String elapsedTimeMins = ET_elapsedTimeMins.getText().toString();

            // Display the dialog box
            dialogBoxDisplayDriftVector(formattedTDVMagnitude, formattedTDVAngle, String.valueOf(divergenceAngle), formattedSearchArea, driftObjectCharacteristics, String.valueOf(windSpeed),
                    windDirection, String.valueOf(currentSpeed), String.valueOf(currentVectorAngle), formattedLeeway, elapsedTimeHrs, elapsedTimeMins);

            return formattedTDVMagnitude + "" + formattedTDVAngle + "" + formattedSearchArea + "" + String.valueOf(divergenceAngle);
        }
        return "failed to validate";
    }

    // Deal with Spinner selections
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item save the selection to a string
        String item = parent.getItemAtPosition(position).toString();

        // The spinner dropdown lists are cascaded so that the dropdown list of a the second spinner is only populated when a selection has been made from the first one:
        // Generate a list of windDirections
        List<String> windDirections = new ArrayList<String>();
        windDirections.add("N");
        windDirections.add("NNE");
        windDirections.add("NE");
        windDirections.add("ENE");
        windDirections.add("E");
        windDirections.add("ESE");
        windDirections.add("SE");
        windDirections.add("SSE");
        windDirections.add("S");
        windDirections.add("SSW");
        windDirections.add("SW");
        windDirections.add("WSW");
        windDirections.add("W");
        windDirections.add("WNW");
        windDirections.add("NW");
        windDirections.add("NNW");

        // Generate a list of driftObjects from the database
        List<DriftObjectLeeway> DriftObjects = MainActivity.driftObjectLeewayDatabase.driftObjectLeewayDao().getAllDriftObjects();
        // Generate a list of categories
        List<String> categoriesList = new ArrayList<String>();
        for(DriftObjectLeeway driftObject : DriftObjects) {
            categoriesList.add(driftObject.getCategory());
        }
        // Generate a list of sub-categories
        List<String> subCategoriesList = new ArrayList<String>();
        for(DriftObjectLeeway driftObject : DriftObjects) {
            subCategoriesList.add(driftObject.getSubCategory());
        }

        // Generate a list of descriptions
        List<String> descriptionsList = new ArrayList<String>();
        for(DriftObjectLeeway driftObject : DriftObjects) {
            descriptionsList.add(driftObject.getPrimaryDescriptor());
        }

        // Generate a list of secondary descriptions
        List<String> secondaryDescriptionsList = new ArrayList<String>();
        for(DriftObjectLeeway driftObject : DriftObjects) {
            secondaryDescriptionsList.add(driftObject.getSecondaryDescriptor());
        }

        // If the spinner item selected relates to windDirection then save the selection
        if(item.contentEquals("N") || item.contentEquals("NNE") || item.contentEquals("NE") || item.contentEquals("ENE") || item.contentEquals("E") || item.contentEquals("ESE")
                || item.contentEquals("SE") || item.contentEquals("SSE") || item.contentEquals("S") || item.contentEquals("SSW") || item.contentEquals("SW") || item.contentEquals("WSW") ||
                item.contentEquals("W") || item.contentEquals("WNW") || item.contentEquals("NW") || item.contentEquals("NNW")){
            // Find views
            Spinner SP_windDirection = findViewById(R.id.SP_windDirection);

            // Save the windDirection selection
            windDirection = (String) SP_windDirection.getItemAtPosition(SP_windDirection.getSelectedItemPosition());

        }// End windDirection spinner code
        // Else if the spinner item selected relates to categories then populate the dropdown for the subCategory spinner
        else if(categoriesList.contains(item)) {
            // Find views
            Spinner SP_category = findViewById(R.id.SP_category);
            Spinner SP_subCategory = findViewById(R.id.SP_subCategory);

            // Save the category selection
            category = (String) SP_category.getItemAtPosition(SP_category.getSelectedItemPosition());

            // Spinner click listener
            SP_subCategory.setOnItemSelectedListener(this);

            // Generate a list of driftObjects within the selected item category from the database
            List<DriftObjectLeeway> DriftObjectsInCategory = driftObjectLeewayDatabase.driftObjectLeewayDao().getObjectsInCategory(item);

            // Generate dropdown list elements
            List<String> subCategories = new ArrayList<String>();
            for(DriftObjectLeeway driftObject : DriftObjectsInCategory) {
                if(!subCategories.contains(driftObject.getSubCategory())) {
                    subCategories.add(driftObject.getSubCategory());
                }
            }

            // Create spinner adapter
            ArrayAdapter<String> subCategoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subCategories);
            // Dropdown layout style (There are different spinner styles you can choose from, or you can create your own custom dropdown layout)
            subCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Attach adapter to spinner
            SP_subCategory.setAdapter(subCategoriesAdapter);
        }// End sub-category spinner code
        // Else if the spinner item selected relates to sub-categories then populate the dropdown for the description spinner
        else if(subCategoriesList.contains(item)) {
            // Find view
            Spinner SP_subCategory = findViewById(R.id.SP_subCategory);
            Spinner SP_description = findViewById(R.id.SP_description);

            // save the sub-category selection
            subCategory = (String) SP_subCategory.getItemAtPosition(SP_subCategory.getSelectedItemPosition());

            // Spinner click listener
            SP_description.setOnItemSelectedListener(this);

            // Generate a list of driftObjects within the selected item sub-category from the database
            List<DriftObjectLeeway> DriftObjectsInSubCategory = driftObjectLeewayDatabase.driftObjectLeewayDao().getObjectsInSubCategory(item);

            // Generate dropdown list elements
            List<String> descriptions = new ArrayList<String>();
            for(DriftObjectLeeway driftObject : DriftObjectsInSubCategory) {
                if(!descriptions.contains(driftObject.getPrimaryDescriptor())) {
                    descriptions.add(driftObject.getPrimaryDescriptor());
                }
            }

            // Create spinner adapter
            ArrayAdapter<String> descriptionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, descriptions);
            // Dropdown layout style (There are different spinner styles you can choose from, or you can create your own custom dropdown layout)
            descriptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Attach adapter to spinner
            SP_description.setAdapter(descriptionsAdapter);

            int subCategoryPosition = SP_subCategory.getSelectedItemPosition();

        }// End description spinner code
        // Else if the spinner item selected relates to descriptions then populate the dropdown for the secondary description spinner
        else if(descriptionsList.contains(item)) {
            // Find view
            Spinner SP_description = findViewById(R.id.SP_description);
            Spinner SP_secondaryDescription = findViewById(R.id.SP_secondaryDescription);

            // Save the description selection
            primaryDescriptor = (String) SP_description.getItemAtPosition(SP_description.getSelectedItemPosition());

            // Spinner click listener
            SP_secondaryDescription.setOnItemSelectedListener(this);

            // Generate a list of driftObjects from the database within the selected item description field
            List<DriftObjectLeeway> DriftObjectsWithDescription = driftObjectLeewayDatabase.driftObjectLeewayDao().getObjectsWithDescription(item);

            // Generate dropdown list elements
            List<String> secondaryDescriptions = new ArrayList<String>();
            for(DriftObjectLeeway driftObject : DriftObjectsWithDescription) {
                if(!secondaryDescriptions.contains(driftObject.getSecondaryDescriptor())) {
                    secondaryDescriptions.add(driftObject.getSecondaryDescriptor());
                }
            }

            // Create spinner adapter
            ArrayAdapter<String> secondaryDescriptionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, secondaryDescriptions);
            // Dropdown layout style (There are different spinner styles you can choose from, or you can create your own custom dropdown layout)
            secondaryDescriptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Attach adapter to spinner
            SP_secondaryDescription.setAdapter(secondaryDescriptionsAdapter);

            int secondaryDescription = SP_secondaryDescription.getSelectedItemPosition();

            // If primary descriptor is "N/A" then the secondary descriptor is also "N/A" so it should be saved as well.
            if(secondaryDescriptionsList.contains(item)) {
                // Save the secondaryDescription selection
                secondaryDescriptor = (String) SP_secondaryDescription.getItemAtPosition(SP_secondaryDescription.getSelectedItemPosition());
            }
        }// End secondary description spinner code
        // If primary descriptor is not "N/A" then Save the secondary descriptor value.
        else if(secondaryDescriptionsList.contains(item)) {
            // Find views
            Spinner SP_secondaryDescription = findViewById(R.id.SP_secondaryDescription);

            // Save the secondaryDescription selection
            secondaryDescriptor = (String) SP_secondaryDescription.getItemAtPosition(SP_secondaryDescription.getSelectedItemPosition());
        }// End saving secondarydescription selection
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Sorry, I don't know which drop down menu you selected.", Toast.LENGTH_LONG);
            toast.show();
        }
    }// End onItemSelected method

    // Do nothing if no spinner item is selected
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO: Auto-generated method stub
    }

    public void dialogBoxDisplayDriftVector(String distance, String bearing, String divergenceAngle, String searchAreaSize, String driftObject, String windSpeed,
                                            String windDirection, String currentSpeed, String currentDirection, String leeway, String elapsedTimeHrs, String elapsedTimeMins) {
        // Instantiate alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set custom layout for dialog box
        LayoutInflater layoutInflater = getLayoutInflater();
        View driftVectorDialogLayout = layoutInflater.inflate(R.layout.dialog_display_drift_vector, null);
        builder.setView(driftVectorDialogLayout);

        // Find Views
        final TextView TV_distance = driftVectorDialogLayout.findViewById(R.id.TV_distance);
        final TextView TV_bearing = driftVectorDialogLayout.findViewById(R.id.TV_bearing);
        final TextView TV_divergence = driftVectorDialogLayout.findViewById(R.id.TV_divergence);
        final TextView TV_searchAreaSize = driftVectorDialogLayout.findViewById(R.id.TV_searchAreaSize);
        final TextView TV_driftObject = driftVectorDialogLayout.findViewById(R.id.TV_driftObject);
        final TextView TV_windSpeed = driftVectorDialogLayout.findViewById(R.id.TV_windSpeed);
        final TextView TV_windDirection = driftVectorDialogLayout.findViewById(R.id.TV_windDirection);
        final TextView TV_currentSpeed = driftVectorDialogLayout.findViewById(R.id.TV_currentSpeed);
        final TextView TV_currentDirection = driftVectorDialogLayout.findViewById(R.id.TV_currentDirection);
        final TextView TV_leeway = driftVectorDialogLayout.findViewById(R.id.TV_leeway);
        final TextView TV_elapsedTimeHrs = driftVectorDialogLayout.findViewById(R.id.TV_elapsedTimeHrs);
        final TextView TV_elapsedTimeMins = driftVectorDialogLayout.findViewById(R.id.TV_elapsedTimeMins);
        final ImageButton B_closeDialogBox = driftVectorDialogLayout.findViewById(R.id.B_closeDialogBox);

        // Populate views
        TV_distance.setText(distance);
        TV_bearing.setText(bearing);
        TV_divergence.setText(divergenceAngle);
        TV_searchAreaSize.setText(searchAreaSize);
        TV_driftObject.setText(driftObject);
        TV_windSpeed.setText(windSpeed);
        TV_windDirection.setText(windDirection);
        TV_currentSpeed.setText(currentSpeed);
        TV_currentDirection.setText(currentDirection);
        TV_leeway.setText(leeway);
        TV_elapsedTimeHrs.setText(elapsedTimeHrs);
        TV_elapsedTimeMins.setText(elapsedTimeMins);

        // Create dialog box
        final AlertDialog dialog = builder.create();

        // Display dialog box on screen
        dialog.show();

        // Button Handlers
        B_closeDialogBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog box
                dialog.dismiss();
            }
        });

        // Close dialog when user clicks outside of dialog box
        dialog.setCanceledOnTouchOutside(true);

    }// End dialog box method

    // Method to hide the on-screen keyboard as it will not be required (and gets in the way) while making spinner selections
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}// End DriftVectorCalculator class
