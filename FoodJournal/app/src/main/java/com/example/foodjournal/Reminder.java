package com.example.foodjournal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Reminder extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    public static final String INTENT_EXTRA_MESSAGE ="com.example.foodjournal.MESSAGE";

    private static int breakfastHour = 9;
    private static int breakfastMinute = 0;
    private static int lunchHour = 13;
    private static int lunchMinute = 0;
    private static int dinnerHour = 19;
    private static int dinnerMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        final SharedPreferences sharedPreferencesOfButton = getSharedPreferences("clockState",MODE_PRIVATE);
        final SharedPreferences sharedPreferencesOfSwitch = getSharedPreferences("switchState",MODE_PRIVATE);
        final SharedPreferences.Editor clockEditor = sharedPreferencesOfButton.edit();
        final SharedPreferences.Editor switchEditor = sharedPreferencesOfSwitch.edit();

        final Switch sw1 = findViewById(R.id.switch1);
        final Switch sw2 = findViewById(R.id.switch2);
        final Switch sw3 = findViewById(R.id.switch3);
        final Button bt1 = findViewById(R.id.button1);
        final Button bt2 = findViewById(R.id.button2);
        final Button bt3 = findViewById(R.id.button3);

        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw1.isChecked()){
                    if (ActivityCompat.checkSelfPermission(Reminder.this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Reminder.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                    }
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        onGPS();
                    }
                    switchEditor.putBoolean("breakfastState",true);
                    switchEditor.apply();
                    setReminder(true,"breakfast");
                }
                else{
                    switchEditor.putBoolean("breakfastState",false);
                    switchEditor.apply();
                    setReminder(false,"breakfast");
                }
            }
        });
        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw2.isChecked()){
                    if (ActivityCompat.checkSelfPermission(Reminder.this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Reminder.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                    }
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        onGPS();
                    }
                    switchEditor.putBoolean("lunchState",true);
                    switchEditor.apply();
                    setReminder(true,"lunch");
                }
                else{
                    switchEditor.putBoolean("lunchState",false);
                    switchEditor.apply();
                    setReminder(false,"lunch");
                }
            }
        });
        sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw3.isChecked()){
                    if (ActivityCompat.checkSelfPermission(Reminder.this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Reminder.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                    }
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        onGPS();
                    }
                    switchEditor.putBoolean("dinnerState",true);
                    switchEditor.apply();
                    setReminder(true,"dinner");
                }
                else{
                    switchEditor.putBoolean("dinnerState",false);
                    switchEditor.apply();
                    setReminder(false,"dinner");
                }
            }
        });

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog breakfastTimePicker = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        breakfastHour=hourOfDay;
                        breakfastMinute=minute;
                        bt1.setText(String.format("%02d:%02d", breakfastHour, breakfastMinute) + " - " + String.format("%02d:%02d", breakfastHour + 1, breakfastMinute));
                        clockEditor.putInt("breakfastHour", breakfastHour);
                        clockEditor.putInt("breakfastMinute",breakfastMinute);
                        clockEditor.apply();
                        if(sw1.isChecked()){
                            setReminder(false,"breakfast");
                            setReminder(true,"breakfast");
                        }
                    }
                }, hour, minute, true);
                breakfastTimePicker.show();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog lunchTimePicker = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        lunchHour = hourOfDay;
                        lunchMinute = minute;
                        bt2.setText(String.format("%02d:%02d", lunchHour, lunchMinute) + " - " + String.format("%02d:%02d", lunchHour + 1, lunchMinute));
                        clockEditor.putInt("lunchHour", lunchHour);
                        clockEditor.putInt("lunchMinute",lunchMinute);
                        clockEditor.apply();
                        if(sw2.isChecked()){
                            setReminder(false,"lunch");
                            setReminder(true,"lunch");
                        }
                    }
                }, hour, minute, true);
                lunchTimePicker.show();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog dinnerTimePicker = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        dinnerHour=hourOfDay;
                        dinnerMinute=minute;
                        bt3.setText(String.format("%02d:%02d", dinnerHour, dinnerMinute) + " - " + String.format("%02d:%02d", dinnerHour + 1, dinnerMinute));
                        clockEditor.putInt("dinnerHour", dinnerHour);
                        clockEditor.putInt("dinnerMinute",dinnerMinute);
                        clockEditor.apply();
                        if(sw3.isChecked()){
                            setReminder(false,"dinner");
                            setReminder(true,"dinner");
                        }
                    }
                }, hour, minute, true);
                dinnerTimePicker.show();
            }
        });

        sw1.setChecked(sharedPreferencesOfSwitch.getBoolean("breakfastState",false));
        sw2.setChecked(sharedPreferencesOfSwitch.getBoolean("lunchState",false));
        sw3.setChecked(sharedPreferencesOfSwitch.getBoolean("dinnerState",false));

        breakfastHour = sharedPreferencesOfButton.getInt("breakfastHour",breakfastHour);
        breakfastMinute = sharedPreferencesOfButton.getInt("breakfastMinute",breakfastMinute);
        bt1.setText(String.format("%02d:%02d", breakfastHour, breakfastMinute) + " - " + String.format("%02d:%02d", breakfastHour + 1, breakfastMinute));
        lunchHour = sharedPreferencesOfButton.getInt("lunchHour",lunchHour);
        lunchMinute = sharedPreferencesOfButton.getInt("lunchMinute",lunchMinute);
        bt2.setText(String.format("%02d:%02d", lunchHour, lunchMinute) + " - " + String.format("%02d:%02d", lunchHour + 1, lunchMinute));
        dinnerHour = sharedPreferencesOfButton.getInt("dinnerHour",dinnerHour);
        dinnerMinute = sharedPreferencesOfButton.getInt("dinnerMinute",dinnerMinute);
        bt3.setText(String.format("%02d:%02d", dinnerHour, dinnerMinute) + " - " + String.format("%02d:%02d", dinnerHour + 1, dinnerMinute));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }

    public void onGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS setting");
        builder.setMessage("Enable GPS for location-based reminder.");
        builder.setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(),"The reminder would not function without GPS",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setReminder(boolean set,String mealTime){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if(mealTime.compareTo("breakfast") == 0){
            Intent startIntentBreakfast = new Intent(this, LocationTracking.class);
            Intent endIntentBreakfast = new Intent(this, StopService.class);
            startIntentBreakfast.putExtra(INTENT_EXTRA_MESSAGE,"breakfast");
            endIntentBreakfast.putExtra(INTENT_EXTRA_MESSAGE,"breakfast");
            PendingIntent startPendingIntentBreakfast = PendingIntent.getBroadcast(this, 0, startIntentBreakfast, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent endPendingIntentBreakfast = PendingIntent.getBroadcast(this, 1, endIntentBreakfast, PendingIntent.FLAG_UPDATE_CURRENT);

            if (set){
                Calendar startCalendarBreakfast = Calendar.getInstance();
                startCalendarBreakfast.setTimeInMillis(System.currentTimeMillis());
                startCalendarBreakfast.set(Calendar.HOUR_OF_DAY, breakfastHour);
                startCalendarBreakfast.set(Calendar.MINUTE, breakfastMinute);
                Calendar endCalendarBreakfast = Calendar.getInstance();
                endCalendarBreakfast.setTimeInMillis(System.currentTimeMillis());
                endCalendarBreakfast.set(Calendar.HOUR_OF_DAY, breakfastHour + 1);
                endCalendarBreakfast.set(Calendar.MINUTE, breakfastMinute);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startCalendarBreakfast.getTimeInMillis(), 24 * 60 * 60 * 1000, startPendingIntentBreakfast);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, endCalendarBreakfast.getTimeInMillis(), 24 * 60 * 60 * 1000, endPendingIntentBreakfast);
            }
            else{
                alarmManager.cancel(startPendingIntentBreakfast);
                alarmManager.cancel(endPendingIntentBreakfast);
                sendBroadcast(endIntentBreakfast);
            }
        }
        if(mealTime.compareTo("lunch") == 0){
            Intent startIntentLunch = new Intent(this, LocationTracking.class);
            Intent endIntentLunch = new Intent(this, StopService.class);
            startIntentLunch.putExtra(INTENT_EXTRA_MESSAGE,"lunch");
            endIntentLunch.putExtra(INTENT_EXTRA_MESSAGE,"lunch");
            PendingIntent startPendingIntentLunch = PendingIntent.getBroadcast(this, 2, startIntentLunch, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent endPendingIntentLunch = PendingIntent.getBroadcast(this, 3, endIntentLunch, PendingIntent.FLAG_UPDATE_CURRENT);

            if (set){
                Calendar startCalendarLunch = Calendar.getInstance();
                startCalendarLunch.setTimeInMillis(System.currentTimeMillis());
                startCalendarLunch.set(Calendar.HOUR_OF_DAY, lunchHour);
                startCalendarLunch.set(Calendar.MINUTE, lunchMinute);
                Calendar endCalendarLunch = Calendar.getInstance();
                endCalendarLunch.setTimeInMillis(System.currentTimeMillis());
                endCalendarLunch.set(Calendar.HOUR_OF_DAY, lunchHour + 1);
                endCalendarLunch.set(Calendar.MINUTE, lunchMinute);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startCalendarLunch.getTimeInMillis(), 24 * 60 * 60 * 1000, startPendingIntentLunch);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, endCalendarLunch.getTimeInMillis(), 24 * 60 * 60 * 1000, endPendingIntentLunch);
            }
            else{
                alarmManager.cancel(startPendingIntentLunch);
                alarmManager.cancel(endPendingIntentLunch);
                sendBroadcast(endIntentLunch);
            }

        }
        if(mealTime.compareTo("dinner") == 0){
            Intent startIntentDinner = new Intent(this, LocationTracking.class);
            Intent endIntentDinner = new Intent(this, StopService.class);
            startIntentDinner.putExtra(INTENT_EXTRA_MESSAGE,"dinner");
            endIntentDinner.putExtra(INTENT_EXTRA_MESSAGE,"dinner");
            PendingIntent startPendingIntentDinner = PendingIntent.getBroadcast(this, 4, startIntentDinner, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent endPendingIntentDinner = PendingIntent.getBroadcast(this, 5, endIntentDinner, PendingIntent.FLAG_UPDATE_CURRENT);

            if (set){
                Calendar startCalendarDinner= Calendar.getInstance();
                startCalendarDinner.setTimeInMillis(System.currentTimeMillis());
                startCalendarDinner.set(Calendar.HOUR_OF_DAY, dinnerHour);
                startCalendarDinner.set(Calendar.MINUTE, dinnerMinute);
                Calendar endCalendarDinner = Calendar.getInstance();
                endCalendarDinner.setTimeInMillis(System.currentTimeMillis());
                endCalendarDinner.set(Calendar.HOUR_OF_DAY, dinnerHour + 1);
                endCalendarDinner.set(Calendar.MINUTE, dinnerMinute);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startCalendarDinner.getTimeInMillis(), 24 * 60 * 60 * 1000, startPendingIntentDinner);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, endCalendarDinner.getTimeInMillis(), 24 * 60 * 60 * 1000, endPendingIntentDinner);
            }
            else{
                alarmManager.cancel(startPendingIntentDinner);
                alarmManager.cancel(endPendingIntentDinner);
                sendBroadcast(endIntentDinner);
            }
        }
    }
}
