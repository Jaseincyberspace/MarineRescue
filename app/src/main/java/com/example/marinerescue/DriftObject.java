package com.example.marinerescue;

public class DriftObject {
    String Category;
    String SubCategory;
    String PrimaryDescriptor;
    String SecondaryDescriptor;
    double SpeedMultiplier;
    double SpeedModifier;
    double DivergenceAngle;

    public DriftObject(String category, String subCategory, String primaryDescriptor, String secondaryDescriptor, double speedMultiplier, double speedModifier, double divergenceAngle) {
        Category = category;
        SubCategory = subCategory;
        PrimaryDescriptor = primaryDescriptor;
        SecondaryDescriptor = secondaryDescriptor;
        SpeedMultiplier = speedMultiplier;
        SpeedModifier = speedModifier;
        DivergenceAngle = divergenceAngle;
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
}
