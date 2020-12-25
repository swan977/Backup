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

import java.io.IOException;

public class UpdateJournal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_journal);

        final EditText nameText = findViewById(R.id.UpdateFoodName);
        final EditText descText = findViewById(R.id.UpdateFoodDesc);
        Button update = findViewById(R.id.Update);
        Button delete = findViewById(R.id.Delete);

        Intent intent = getIntent();
        final String currentPhotoPath = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
        ImageView img = findViewById(R.id.UpdateImage);

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

        final FoodJournalDatabase db = Room.databaseBuilder(getApplicationContext(), FoodJournalDatabase.class, "food-db").allowMainThreadQueries().build();
        nameText.setText(db.foodJournalDao().getPhotoFood(currentPhotoPath));
        descText.setText(db.foodJournalDao().getPhotoDesc(currentPhotoPath));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String desc = descText.getText().toString();

                db.foodJournalDao().updateJournal(name,desc,currentPhotoPath);

                Intent intent = new Intent(UpdateJournal.this, MainActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.foodJournalDao().deleteJournal(currentPhotoPath);

                Intent intent = new Intent(UpdateJournal.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
