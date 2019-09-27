package com.icdatofcus.loginwithfinger;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//
//import com.machinezoo.sourceafis.FingerprintMatcher;
//import com.machinezoo.sourceafis.FingerprintTemplate;

import com.testing.aramis.sourceafis.FingerprintMatcher;
import com.testing.aramis.sourceafis.FingerprintTemplate;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import asia.kanopi.fingerscan.Fingerprint;

public class SecondActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    TextView email, usage, acc, fing, fing_2, fing_3, fing_4;

    String email_String = "email";
    String usage_String = "usage_count";
    String accountbalance_String = "accountbalance";
    String fing_String = "left_thumb_fingerprint";
    String fing_2_String = "left_index_fingerprint";
    String fing_3_String = "right_thumb_fingerprint";
    String fing_4_String = "right_index_fingerprint";

    String Stringle = "stringle";


    String stringDatabaseFinger;
    String stringReaderFinger;

    private byte [] byteCompare;

    Fingerprint fingerprint;

    private FingerprintTemplate probeTemplate, candidateTemplate_One, candidateTemplate_Two,
            candidateTemplate_Three, candidateTemplate_Four;
    private FingerprintMatcher pleaseMatch = new FingerprintMatcher();


    private static final int NOTIFICATION_ID = 8899;

    private static final int RETRY_COUNT = 5;

    private static long mTime = 0;

    private NotificationManager mNotificationManager;

    private boolean mLocked = false;

    private static final int REQUEST_WRITE_PERMISSION = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            doFileWork();
            Toast.makeText(SecondActivity.this, "Granted", Toast.LENGTH_LONG).show();
        }else{
            //handle user denied permission, maybe dialog box to user
            Toast.makeText(SecondActivity.this, "Not Granted", Toast.LENGTH_LONG).show();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
//            doFileWork();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        {
        fingerprint = new Fingerprint();

        email = findViewById(R.id.email);
        usage = findViewById(R.id.usage);
        acc = findViewById(R.id.acc);
        fing = findViewById(R.id.fing);
        fing_2 = findViewById(R.id.fing_2);
        fing_3 = findViewById(R.id.fing_3);
        fing_4 = findViewById(R.id.fing_4);

        Bundle SixthParcel = getIntent().getExtras();

        try {
            email.setText(SixthParcel.getString(email_String));
        } catch (Exception ignored) {

        }


        try {
            usage.setText(SixthParcel.getString(usage_String));
        } catch (Exception ignored) {

        }

        try {
            acc.setText(SixthParcel.getString(accountbalance_String));
        } catch (Exception ignored) {

        }

        try {
            stringDatabaseFinger = SixthParcel.getString(fing_3_String);
        } catch (Exception ignored) {

        }


//        try {
//            stringReaderFinger =  SixthParcel.getString(Stringle);
//        } catch (Exception ignored) {
//
//        }


        try {
            byteCompare = SixthParcel.getByteArray("bite");
        } catch (Exception ignored) {

        }

//        try {
//            byteCompare =  SixthParcel.getByteArray("bite");
//        } catch (Exception ignored) {
//
//        }


//
//        try {
//            fing_3.setText(SixthParcel.getString(fing_3_String));
//        } catch (Exception ignored) {
//
//        }
//
//        try {
//            fing_4.setText(SixthParcel.getString(fing_4_String));
//        } catch (Exception ignored) {
//
//        }
    }
    }
/**
 *
 *  mLocked = isPortraitOrientationLocked();
 *
 *         mNotificationManager =
 *                 (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
 *
 *         // Listen system settings changes
 *         getApplicationContext().getContentResolver().registerContentObserver(
 *                 android.provider.Settings.System.CONTENT_URI, true,
 *                 new ContentObserver(null) {
 *                     @Override
 *                     public void onChange(boolean selfChange) {
 *                         super.onChange(selfChange);
 *                         systemSettingsChange();
 *                     }
 *                 });
    @Override
    protected void onStart() {
        super.onStart();
        long currentTime = System.currentTimeMillis();
        // Avoid toggling the setting twice in one second.
        if (currentTime > mTime + 1000) {
            mTime = currentTime;
            if (supportAccelerometerRotation())
                togglePortraitOrientationLock();
            else {
                Toast.makeText(this,
                        "Accel Not Supp",
                        Toast.LENGTH_SHORT).show();
            }
        }
        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        // Cancel the notification if activity is destroyed.
        // mNotificationManager.cancel(NOTIFICATION_ID);

        // Flush all IPC commands to make sure the notification is cancelled
        // before returning.
        Binder.flushPendingCommands();
        super.onDestroy();
    }

    private boolean supportAccelerometerRotation() {
        SensorManager sensorManager =
                (SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager == null)
            return false;

        Sensor accelerometer =
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Does not have accelerometer sensor.
        if (accelerometer == null)
            return false;

        // Try reading system Auto-Rotate setting.
        try {
            android.provider.Settings.System.getInt(getContentResolver(),
                    android.provider.Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            return false;
        }

        return true;
    }

    // Show popup message.
    private void showPopupMessage(boolean locked) {
        int message = locked ?
                R.string.portrait_orientation_locked :
                R.string.portrait_orientation_unlocked;
        Toast.makeText(this, message,
                Toast.LENGTH_SHORT).show();
    }

    // Show notification
    private void showNotification(boolean locked) {
        int messageId = locked ?
                R.string.portrait_orientation_locked :
                R.string.portrait_orientation_unlocked;

        Notification notification = new Notification(
                locked ? R.drawable.a_pin_b : R.drawable.a_username_b,
                getText(messageId),
                System.currentTimeMillis());

        // Notification is on going event, it will not be cleared.
        notification.flags |=
                Notification.FLAG_NO_CLEAR |
                        Notification.FLAG_ONGOING_EVENT;

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
//        notification.setLatestEventInfo((Context)this,
//                getText(R.string.app_name),
//                (CharSequence)getText(messageId),
//                contentIntent);
        notification.icon = locked ?
                R.drawable.a_pin_b : R.drawable.a_username_b;

        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    // Toggle system Auto-Rotate setting.
    private boolean togglePortraitOrientationLock() {
        boolean locked = isPortraitOrientationLocked();
        for (int count = 0; count < RETRY_COUNT; count++) {
            setPortraitOrientationLock(!locked);
            if (isPortraitOrientationLocked() != locked) {
                showPopupMessage (!locked);
                return true;
            }
        }
        return false;
    }

    // Get system portrait orientation lock.
    private boolean isPortraitOrientationLocked() {
        int value = 1;
        try {
            value = android.provider.Settings.System.getInt(
                    getContentResolver(),
                    android.provider.Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            value = 1;
        }
        return value == 0;
    }
    // Set system portrait orientation lock.
    private void setPortraitOrientationLock(boolean lock) {
        android.provider.Settings.System.putInt(getContentResolver(),
                android.provider.Settings.System.ACCELEROMETER_ROTATION,
                lock ? 0 : 1);
    }

    // Handle system settings change.
    private void systemSettingsChange() {
        boolean locked = isPortraitOrientationLocked();
        if (mLocked != locked) {
            mLocked = locked;
            showNotification(locked);
        }
    } */

    public void getTemp (View v) {

        byte [] data = Base64.decode(stringDatabaseFinger, Base64.DEFAULT);


        probeTemplate = new FingerprintTemplate().dpi(500).create(byteCompare);


        candidateTemplate_One = new FingerprintTemplate().dpi(500).create(data);

        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);

    }

    public void compare (View v) {

        double score = pleaseMatch.index(this.probeTemplate).match(this.candidateTemplate_One);

        boolean matches = score >= 40;

        if (matches) {
            Toast.makeText(SecondActivity.this, "Successfully Matched Dave, Congrats", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(SecondActivity.this, "Few more Changes Dave", Toast.LENGTH_SHORT).show();
        }






    }
}

