package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class SaveJournal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_journal);

        final EditText nametext = findViewById(R.id.ConfirmFoodName);
        final EditText desctext = findViewById(R.id.ConfirmFoodDesc);
        Button save = findViewById(R.id.Save);

        Intent intent = getIntent();
        final String currentPhotoPath = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        final String fileName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);

        ImageView img = findViewById(R.id.SaveImage);

//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds=true;
//        BitmapFactory.decodeFile(currentPhotoPath,bmOptions);
//        bmOptions.inSampleSize=Math.min(bmOptions.outWidth/200,bmOptions.outHeight/200);
//        bmOptions.inJustDecodeBounds=false;

        ExifInterface exifInterface = null;
        try{
            exifInterface = new ExifInterface(currentPhotoPath);
        }
        catch (IOException e){}
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
        Matrix matrix = new Matrix();
        if(orientation == ExifInterface.ORIENTATION_ROTATE_90)
            matrix.setRotate(90);
        else if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            matrix.setRotate(180);
        else if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            matrix.setRotate(270);

//        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath,bmOptions);
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        img.setImageBitmap(Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nametext.getText().toString();
                String desc = desctext.getText().toString();

                FoodJournal food = new FoodJournal();
                food.setPhotoName(fileName);
                food.setPhotoFood(name);
                food.setPhotoDesc(desc);
                food.setPhotoPath(currentPhotoPath);

                FoodJournalDatabase db = Room.databaseBuilder(getApplicationContext(), FoodJournalDatabase.class, "food-db").allowMainThreadQueries().build();
                db.foodJournalDao().addJournal(food);

                Intent intent = new Intent(SaveJournal.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
