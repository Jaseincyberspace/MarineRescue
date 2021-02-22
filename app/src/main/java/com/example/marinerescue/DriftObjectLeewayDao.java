package com.example.marinerescue;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@androidx.room.Dao
public interface DriftObjectLeewayDao {
    // Add object
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addDriftObjectLeeway(DriftObjectLeeway driftObjectLeeway);
    // Get all objects
    @Query("SELECT * FROM DriftObjectLeeway") public List<DriftObjectLeeway> getAllDriftObjects();
    // Get objects in a particular category
    @Query("SELECT * FROM DriftObjectLeeway WHERE Category = :category") public List<DriftObjectLeeway> getObjectsInCategory(String category);
    // Get objects in a particular sub-category
    @Query("SELECT * FROM DriftObjectLeeway WHERE SubCategory = :subCategory") public List<DriftObjectLeeway> getObjectsInSubCategory(String subCategory);
    // Get objects that share a particular primary description
    @Query("SELECT * FROM DriftObjectLeeway WHERE PrimaryDescriptor = :primaryDescriptor") public List<DriftObjectLeeway> getObjectsWithDescription(String primaryDescriptor);
    // Get a particular object based on its category, sub-category, descriptor and secondary descriptor (This returns a single object)
    @Query("SELECT * FROM DriftObjectLeeway WHERE (Category = :category) AND (subCategory = :subCategory) AND (primaryDescriptor = :primaryDescriptor) AND (secondaryDescriptor = :secondaryDescriptor) LIMIT 1")
    public DriftObjectLeeway getDriftObjectLeeway(String category, String subCategory, String primaryDescriptor, String secondaryDescriptor);
    // Delete an object based on it's ID
    @Query("DELETE FROM DriftObjectLeeway WHERE DriftObjectLeewayID = :driftObjectLeewayID") public void deleteDriftObjectLeeway(int driftObjectLeewayID);
    // Delete all objects from database
    @Query("DELETE FROM DriftObjectLeeway") public void deleteAllDriftObjects();

}// End DriftObjectLeewayDao interface
