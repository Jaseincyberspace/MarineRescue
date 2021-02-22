package com.example.marinerescue;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Entity(tableName ="DriftObjectLeeway")
public class DriftObjectLeeway {
    // Table columns
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "DriftObjectLeewayID")
    private int DriftObjectLeewayID;

    @ColumnInfo(name = "Category")
    private String Category;

    @ColumnInfo(name = "SubCategory")
    private String SubCategory;

    @ColumnInfo(name = "PrimaryDescriptor")
    private String PrimaryDescriptor;

    @ColumnInfo(name = "SecondaryDescriptor")
    private String SecondaryDescriptor;

    @ColumnInfo(name = "SpeedMultiplier")
    private double SpeedMultiplier;

    @ColumnInfo(name = "SpeedModifier") // Measured in knots
    private double SpeedModifier;

    @ColumnInfo(name = "DivergenceAngle") // Measured in degrees
    private double DivergenceAngle;

    public int getDriftObjectLeewayID() {
        return DriftObjectLeewayID;
    }

    public void setDriftObjectLeewayID(int driftObjectLeewayID) {
        DriftObjectLeewayID = driftObjectLeewayID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }

    public String getPrimaryDescriptor() {
        return PrimaryDescriptor;
    }

    public void setPrimaryDescriptor(String primaryDescriptor) {
        PrimaryDescriptor = primaryDescriptor;
    }

    public String getSecondaryDescriptor() {
        return SecondaryDescriptor;
    }

    public void setSecondaryDescriptor(String secondaryDescriptor) {
        SecondaryDescriptor = secondaryDescriptor;
    }

    public double getSpeedMultiplier() {
        return SpeedMultiplier;
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        SpeedMultiplier = speedMultiplier;
    }

    public double getSpeedModifier() {
        return SpeedModifier;
    }

    public void setSpeedModifier(double speedModifier) {
        SpeedModifier = speedModifier;
    }

    public double getDivergenceAngle() {
        return DivergenceAngle;
    }

    public void setDivergenceAngle(double divergenceAngle) {
        DivergenceAngle = divergenceAngle;
    }
}// End DriftObjectLeeway class