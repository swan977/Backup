package com.example.foodjournal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodJournalDao {

    @Insert
    void addJournal(FoodJournal food);

    @Query("SELECT photoPath FROM FoodJournal")
    List<String> getJournal ();

    @Query("UPDATE FoodJournal SET photoFood = :photo_Food, photoDesc = :photo_Desc WHERE photoPath = :photo_Path")
    void updateJournal(String photo_Food, String photo_Desc, String photo_Path);

    @Query("DELETE FROM FoodJournal WHERE photoPath = :photo_Path")
    void deleteJournal(String photo_Path);

    @Query("SELECT photoFood FROM FoodJournal WHERE photoPath = :photo_Path")
    String getPhotoFood(String photo_Path);

    @Query("SELECT photoDesc FROM FoodJournal WHERE photoPath = :photo_Path")
    String getPhotoDesc(String photo_Path);
}
