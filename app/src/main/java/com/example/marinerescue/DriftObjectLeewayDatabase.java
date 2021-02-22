package com.example.marinerescue;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {DriftObjectLeeway.class}, version = 2, exportSchema = false)
public abstract class DriftObjectLeewayDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "DriftObjectLeewayDatabase";
    public abstract DriftObjectLeewayDao driftObjectLeewayDao();
    private static DriftObjectLeewayDatabase driftObjectLeewayDatabase;


}