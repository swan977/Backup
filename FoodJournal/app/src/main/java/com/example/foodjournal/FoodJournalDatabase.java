package com.example.foodjournal;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FoodJournal.class}, version = 1)
public abstract class FoodJournalDatabase extends RoomDatabase {
    public abstract FoodJournalDao foodJournalDao();
}
