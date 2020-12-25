package com.example.foodjournal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FoodJournal {

    @PrimaryKey @NonNull
    public String photoName;

    public String photoFood;

    public String photoDesc;

    public String photoPath;

    public String getPhotoName() {
        return photoName;
    }
    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoFood() {
        return photoFood;
    }
    public void setPhotoFood(String photoFood) {
        this.photoFood = photoFood;
    }

    public String getPhotoDesc() {
        return photoDesc;
    }
    public void setPhotoDesc(String photoDesc) {
        this.photoDesc = photoDesc;
    }

    public String getPhotoPath() {
        return photoPath;
    }
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
