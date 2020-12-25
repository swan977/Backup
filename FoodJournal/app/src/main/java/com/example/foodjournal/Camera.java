package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera extends AppCompatActivity {
    public static final String EXTRA_MESSAGE1 = "com.example.testing.MESSAGE1";
    public static final String EXTRA_MESSAGE2= "com.example.testing.MESSAGE2";

    private static final int REQUEST_TAKE_PHOTO = 1;

    private String currentPhotoPath;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName + ".jpg");
        currentPhotoPath = imageFile.getAbsolutePath();
        Uri photoURI = FileProvider.getUriForFile(Camera.this, "com.example.foodjournal.fileprovider", imageFile);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Intent intent = new Intent(Camera.this, SaveJournal.class);
            intent.putExtra(EXTRA_MESSAGE1, currentPhotoPath);
            intent.putExtra(EXTRA_MESSAGE2, fileName);

            startActivity(intent);
        }
    }
}
