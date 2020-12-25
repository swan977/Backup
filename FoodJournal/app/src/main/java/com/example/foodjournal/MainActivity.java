package com.example.foodjournal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE1 = "com.example.testing.MESSAGE1";
    public static final String EXTRA_MESSAGE2= "com.example.testing.MESSAGE2";
    public static final String EXTRA_MESSAGE3= "com.example.testing.MESSAGE3";
    private static final int REQUEST_TAKE_PHOTO = 1;

    private int journalYear;
    private String journalMonth;
    private String journalDay;

    private String currentPhotoPath;
    private String fileName;

    private List<String> photoPathList;
    private static List<String> currentJournalList = new ArrayList();

    private int backButtonCount=0;

    static ImageView img1;
    static ImageView img2;
    static ImageView img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH)+1;
        final int day = c.get(Calendar.DAY_OF_MONTH);
        String sMonth=(month<10)?("0"+month):(""+month);
        String sDay=(day<10)?("0"+day):(""+day);

        ImageButton camera = findViewById(R.id.addBtn);
        ImageButton reminder = findViewById(R.id.reminderBtn);
        Button calendar = findViewById(R.id.calendarBtn);
         img1 = findViewById(R.id.img1);
         img2 = findViewById(R.id.img2);
         img3 = findViewById(R.id.img3);

        calendar.setText(sDay + "/" + sMonth + "/" + year);

        camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName + ".jpg");
                currentPhotoPath = imageFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.example.foodjournal.fileprovider", imageFile);

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        });

        reminder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Reminder.class);
                startActivity(intent);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        journalYear = year;
                        journalMonth = (month<=10)?("0"+(month+1)):(""+(month+1));
                        journalDay = (dayOfMonth<10)?("0"+dayOfMonth):(""+dayOfMonth);

                        String journalDate = journalYear+ journalMonth + journalDay;
                        currentJournalList.clear();

                        for (String s : photoPathList) {
                            if (s.contains(journalDate)) {
                                currentJournalList.add(s);
                            }
                        }

                        if(!currentJournalList.isEmpty()){
                            for(int i=0;i<currentJournalList.size();i++){
                                int photoTime= Integer.parseInt(currentJournalList.get(i).substring(currentJournalList.get(i).lastIndexOf("/")+1).substring(9,11));

                                ExifInterface exifInterface = null;
                                try{
                                    exifInterface = new ExifInterface(currentJournalList.get(i));
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

                                if(photoTime >=0 && photoTime <12){
                                    Bitmap bitmap = BitmapFactory.decodeFile(currentJournalList.get(i));
                                    img1.setImageBitmap(Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true));
                                }
                                else if (photoTime >=12 && photoTime <18){
                                    Bitmap bitmap = BitmapFactory.decodeFile(currentJournalList.get(i));
                                    img2.setImageBitmap(Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true));
                                }
                                else if (photoTime >=18 && photoTime <24){
                                    Bitmap bitmap = BitmapFactory.decodeFile(currentJournalList.get(i));
                                    img3.setImageBitmap(Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true));
                                }
                            }
                        }
                    }
                }, year, month-1, day);
                datePicker.show();
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateJournal.class);
                for (int i = 0; i < currentJournalList.size(); i++) {
                    int photoTime = Integer.parseInt(currentJournalList.get(i).substring(currentJournalList.get(i).lastIndexOf("/") + 1).substring(9, 11));
                    if (photoTime >= 0 && photoTime < 12) {
                        intent.putExtra(EXTRA_MESSAGE3, currentJournalList.get(i));
                        startActivity(intent);
                    }
                }
//                intent.putExtra(EXTRA_MESSAGE3, currentJournalList.get(0));
//                startActivity(intent);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateJournal.class);
                for (int i = 0; i < currentJournalList.size(); i++) {
                    int photoTime = Integer.parseInt(currentJournalList.get(i).substring(currentJournalList.get(i).lastIndexOf("/") + 1).substring(9, 11));
                    if (photoTime >= 12 && photoTime < 18) {
                        intent.putExtra(EXTRA_MESSAGE3, currentJournalList.get(i));
                        startActivity(intent);
                    }
                }
//                intent.putExtra(EXTRA_MESSAGE3, currentJournalList.get(0));
//                startActivity(intent);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateJournal.class);
                for (int i = 0; i < currentJournalList.size(); i++) {
                    int photoTime = Integer.parseInt(currentJournalList.get(i).substring(currentJournalList.get(i).lastIndexOf("/") + 1).substring(9, 11));
                    if (photoTime >= 18 && photoTime < 24) {
                        intent.putExtra(EXTRA_MESSAGE3, currentJournalList.get(i));
                        startActivity(intent);
                    }
                }
//                intent.putExtra(EXTRA_MESSAGE3, currentJournalList.get(0));
//                startActivity(intent);
            }
        });

        FoodJournalDatabase db = Room.databaseBuilder(getApplicationContext(), FoodJournalDatabase.class, "food-db").allowMainThreadQueries().build();
        photoPathList = db.foodJournalDao().getJournal();

        String currentDate = year + sMonth + sDay;
        currentJournalList.clear();

        for (String s : photoPathList) {
            if (s.contains(currentDate)) {
                currentJournalList.add(s);
            }
        }

        if(!currentJournalList.isEmpty()){
            for(int i=0;i<currentJournalList.size();i++){
                int photoTime= Integer.parseInt(currentJournalList.get(i).substring(currentJournalList.get(i).lastIndexOf("/")+1).substring(9,11));
//                int resIDImage = getResources().getIdentifier("img" + (i + 1), "id", getPackageName());
//                ImageView img = findViewById(resIDImage);

                ExifInterface exifInterface = null;
                try{
                    exifInterface = new ExifInterface(currentJournalList.get(i));
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


                if(photoTime >=0 && photoTime <12){
                    Bitmap bitmap = BitmapFactory.decodeFile(currentJournalList.get(i));
                    img1.setImageBitmap(Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true));
                }
                else if (photoTime >=12 && photoTime <18){
                    Bitmap bitmap = BitmapFactory.decodeFile(currentJournalList.get(i));
                    img2.setImageBitmap(Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true));
                }
                else if (photoTime >=18 && photoTime <24){
                    Bitmap bitmap = BitmapFactory.decodeFile(currentJournalList.get(i));
                    img3.setImageBitmap(Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true));
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Intent intent = new Intent(MainActivity.this, SaveJournal.class);
            intent.putExtra(EXTRA_MESSAGE1, currentPhotoPath);
            intent.putExtra(EXTRA_MESSAGE2, fileName);

            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
