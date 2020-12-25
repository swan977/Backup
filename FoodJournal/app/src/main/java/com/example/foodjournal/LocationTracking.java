package com.example.foodjournal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationTracking extends BroadcastReceiver {

    public static final String INTENT_EXTRA_MESSAGE ="com.example.finaltest.MESSAGE";

    private long period;
    public static boolean nearTarget = false;

    public static int threadFlag;
    public static FusedLocationProviderClient fusedLocationClientBreakfast;
    public static FusedLocationProviderClient fusedLocationClientLunch;
    public static FusedLocationProviderClient fusedLocationClientDinner;
    public static LocationCallback mLocationCallback;
    private  LocationRequest mLocationRequest = new LocationRequest()
            .setInterval(20*1000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private Location targetLocation;

    @Override
    public void onReceive(Context context, Intent intent) {

        final Context ctxt=context;
        final String mealTime = intent.getStringExtra(Reminder.INTENT_EXTRA_MESSAGE);

        targetLocation = new Location("restaurant location");
        targetLocation.setLatitude(5.340359);
        targetLocation.setLongitude(100.479287);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    nearTarget = nearTarget(location,ctxt);
                }
            }
        };

        if (mealTime.compareTo("breakfast") == 0){
            fusedLocationClientBreakfast = LocationServices.getFusedLocationProviderClient(context);
            fusedLocationClientBreakfast.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
        else if (mealTime.compareTo("lunch") == 0){
            fusedLocationClientLunch = LocationServices.getFusedLocationProviderClient(context);
            fusedLocationClientLunch.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
        else if (mealTime.compareTo("dinner") == 0){
            fusedLocationClientDinner = LocationServices.getFusedLocationProviderClient(context);
            fusedLocationClientDinner.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }

        new Thread() {
            @Override
            public void run(){
                threadFlag = 1;
                period = System.currentTimeMillis() + (60*1000);
                while(System.currentTimeMillis() < period){
                    if(threadFlag == 0) {
                        break;
                    }
                    if ( nearTarget ){
                        continue;
                    }
                    else {
                        period = System.currentTimeMillis() + (60*1000);
                    }
                }
                if(threadFlag == 1){
                    if(mealTime.compareTo("breakfast") == 0){
                        Intent locationIntentBreakfast = new Intent(ctxt, ActivityRecognition.class);
                        locationIntentBreakfast.putExtra(INTENT_EXTRA_MESSAGE,"breakfast");
                        ctxt.sendBroadcast(locationIntentBreakfast);
                    }
                    else if(mealTime.compareTo("lunch") == 0){
                        Intent locationIntentLunch = new Intent(ctxt, ActivityRecognition.class);
                        locationIntentLunch.putExtra(INTENT_EXTRA_MESSAGE,"lunch");
                        ctxt.sendBroadcast(locationIntentLunch);
                    }
                    else if(mealTime.compareTo("dinner") == 0){
                        Intent locationIntentDinner = new Intent(ctxt, ActivityRecognition.class);
                        locationIntentDinner.putExtra(INTENT_EXTRA_MESSAGE,"dinner");
                        ctxt.sendBroadcast(locationIntentDinner);
                    }
                }
            }
        }.start();
    }

    public boolean nearTarget(Location loc,Context ctxt){
        float rad = 50.00f;
        float distance = loc.distanceTo(targetLocation);

        if( distance < rad ){
            Toast.makeText(ctxt,"near",Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(ctxt,"far",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}

