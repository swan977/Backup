package com.example.foodjournal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.foodjournal.ActivityRecognition.sensorManagerBreakfast;
import static com.example.foodjournal.ActivityRecognition.sensorManagerDinner;
import static com.example.foodjournal.ActivityRecognition.sensorManagerLunch;
import static com.example.foodjournal.LocationTracking.fusedLocationClientBreakfast;
import static com.example.foodjournal.LocationTracking.fusedLocationClientDinner;
import static com.example.foodjournal.LocationTracking.fusedLocationClientLunch;

public class StopService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String mealTime=intent.getStringExtra(Reminder.INTENT_EXTRA_MESSAGE);

        if (mealTime.compareTo("breakfast") == 0){
            if(fusedLocationClientBreakfast != null){
                LocationTracking.threadFlag = 0; //to stop thread
                fusedLocationClientBreakfast.removeLocationUpdates(LocationTracking.mLocationCallback);
                fusedLocationClientBreakfast=null;
            }
            if(sensorManagerBreakfast != null){
                ActivityRecognition.threadFlag = 0;
                sensorManagerBreakfast.unregisterListener(ActivityRecognition.mySensorEventListener);
                sensorManagerBreakfast=null;
            }
        }
        else if (mealTime.compareTo("lunch") == 0){
            if(fusedLocationClientLunch != null){
                LocationTracking.threadFlag = 0;
                fusedLocationClientLunch.removeLocationUpdates(LocationTracking.mLocationCallback);
                fusedLocationClientLunch=null;
            }
            if(sensorManagerLunch != null){
                ActivityRecognition.threadFlag = 0;
                sensorManagerLunch.unregisterListener(ActivityRecognition.mySensorEventListener);
                sensorManagerLunch=null;
            }
        }
        else if (mealTime.compareTo("dinner") == 0){
            if(fusedLocationClientDinner != null){
                LocationTracking.threadFlag = 0;
                fusedLocationClientDinner.removeLocationUpdates(LocationTracking.mLocationCallback);
                fusedLocationClientDinner=null;
            }
            if(sensorManagerDinner != null){
                ActivityRecognition.threadFlag = 0;
                sensorManagerDinner.unregisterListener(ActivityRecognition.mySensorEventListener);
                sensorManagerDinner=null;
            }
        }

    }
}

