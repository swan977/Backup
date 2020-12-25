package com.example.foodjournal;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;
import static com.example.foodjournal.LocationTracking.fusedLocationClientBreakfast;
import static com.example.foodjournal.LocationTracking.fusedLocationClientDinner;
import static com.example.foodjournal.LocationTracking.fusedLocationClientLunch;
import static com.example.foodjournal.LocationTracking.nearTarget;

public class ActivityRecognition extends BroadcastReceiver {

    private long period;
    private static int latestStepValue = 0;
    private static int currentStepValue = 0;

    public static int threadFlag;
    public static SensorManager sensorManagerBreakfast;
    public static SensorManager sensorManagerLunch;
    public static SensorManager sensorManagerDinner;
    public static SensorEventListener mySensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            latestStepValue = (int) sensorEvent.values[0];
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    };

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"Start step tracking",Toast.LENGTH_SHORT).show();

        final Context ctxt = context;
        final String mealTime = intent.getStringExtra(LocationTracking.INTENT_EXTRA_MESSAGE);

        if (mealTime.compareTo("breakfast") == 0){
            sensorManagerBreakfast=(SensorManager) context.getSystemService(SENSOR_SERVICE);
            Sensor countSensor = sensorManagerBreakfast.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorManagerBreakfast.registerListener(mySensorEventListener,countSensor,sensorManagerBreakfast.SENSOR_DELAY_NORMAL);
        }
        else if (mealTime.compareTo("lunch") == 0){
            sensorManagerLunch=(SensorManager) context.getSystemService(SENSOR_SERVICE);
            Sensor countSensor = sensorManagerLunch.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorManagerLunch.registerListener(mySensorEventListener,countSensor,sensorManagerLunch.SENSOR_DELAY_NORMAL);
        }
        else if (mealTime.compareTo("dinner") == 0){
            sensorManagerDinner=(SensorManager) context.getSystemService(SENSOR_SERVICE);
            Sensor countSensor = sensorManagerDinner.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorManagerDinner.registerListener(mySensorEventListener,countSensor,sensorManagerDinner.SENSOR_DELAY_NORMAL);
        }

        new Thread() {
            @Override
            public void run(){
                threadFlag = 1;
                period = System.currentTimeMillis() + (20 * 1000);
                while(System.currentTimeMillis() < period){
                    if(threadFlag == 0) {
                        break;
                    }
                    if ( currentStepValue == latestStepValue & nearTarget){
                        continue;
                    }
                    else {
                        period = System.currentTimeMillis() + (20 * 1000);
                        currentStepValue = latestStepValue;
                    }
                }
                if(threadFlag == 1) {
                    if (mealTime.compareTo("breakfast") == 0) {
                        fusedLocationClientBreakfast.removeLocationUpdates(LocationTracking.mLocationCallback);
                        fusedLocationClientBreakfast=null;
                        sensorManagerBreakfast.unregisterListener(mySensorEventListener);
                        sensorManagerBreakfast=null;
                    }
                    else if (mealTime.compareTo("lunch") == 0){
                        fusedLocationClientLunch.removeLocationUpdates(LocationTracking.mLocationCallback);
                        fusedLocationClientLunch=null;
                        sensorManagerLunch.unregisterListener(mySensorEventListener);
                        sensorManagerLunch=null;
                    }
                    else if (mealTime.compareTo("dinner") == 0){
                        fusedLocationClientDinner.removeLocationUpdates(LocationTracking.mLocationCallback);
                        fusedLocationClientDinner=null;
                        sensorManagerDinner.unregisterListener(mySensorEventListener);
                        sensorManagerDinner=null;
                    }
                    Vibrator v = (Vibrator) ctxt.getSystemService(VIBRATOR_SERVICE);
                    v.vibrate(500);

                    Intent notificationIntent = new Intent(ctxt, Camera.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(ctxt, 10, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(ctxt)
                            .setSmallIcon(R.drawable.notificationicon)
                            .setContentTitle("Food Journal Reminder")
                            .setContentText("It's time to log your food intake!")
                            .setContentIntent(contentIntent)
                            .setAutoCancel(true);
                    NotificationManagerCompat manager = NotificationManagerCompat.from(ctxt);
                    manager.notify(0, builder.build());
                }
            }
        }.start();
    }
}
