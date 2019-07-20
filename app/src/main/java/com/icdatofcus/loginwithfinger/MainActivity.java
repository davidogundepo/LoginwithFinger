package com.icdatofcus.loginwithfinger;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.testing.aramis.sourceafis.FingerprintMatcher;
import com.testing.aramis.sourceafis.FingerprintTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import asia.kanopi.fingerscan.Fingerprint;
import asia.kanopi.fingerscan.Status;

public class MainActivity extends AppCompatActivity {

    EditText regNumber;
    TextView fingerUpdate, ScannerStatus;

    AlertDialog.Builder quantumElevation;

    Fingerprint fingerprint;

    public static JSONObject god;
    public static JSONArray may;

    String LeftThumbFinger,LeftIndexFinger,RightThumbFinger,RightIndexFinger;

    public static String emailString;
    public static String accountbalanceString;

    private FingerprintTemplate probeTemplate, candidateTemplate_One, candidateTemplate_Two,
            candidateTemplate_Three, candidateTemplate_Four;
    private FingerprintMatcher pleaseMatch = new FingerprintMatcher();

    Bitmap bitmapLeftThumb;
    ImageView LeftThumbImage;
    private byte[] bytesObjectLeftThumb, byteAnotherObject;
    String LeftThumbFirst;
    String LeftThumbFake = "ff";
//    TextView ConfirmFingerMatch, ScannerStatus;
    String login_with_finger = "http://128.0.1.2/fingerprint_login_okay.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LeftThumbImage = findViewById(R.id.leftThumb_fp);

        quantumElevation = new AlertDialog.Builder(MainActivity.this);

        fingerprint = new Fingerprint();

        regNumber = findViewById(R.id.regNumber);
        fingerUpdate = findViewById(R.id.fingerUpdate);
        ScannerStatus = findViewById(R.id.scannerStatus);
    }


    Handler printFingerHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte [] image;
            String errorMessage = "empty";
            int status = msg.getData().getInt("status");
            Intent intent = new Intent();
            intent.putExtra("status", status);


            if (status == Status.SUCCESS) {



                image = msg.getData().getByteArray("img");
                bitmapLeftThumb = BitmapFactory.decodeByteArray(image, 0, image.length);
                LeftThumbImage.setImageBitmap(bitmapLeftThumb);
                intent.putExtra("img", image);
                fingerUpdate.setText("Fingerprint captured Left Thumb");

//                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                bitmapLeftThumb.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                bytesObjectLeftThumb = baos.toByteArray();
                LeftThumbFirst = Base64.encodeToString(bytesObjectLeftThumb, Base64.DEFAULT);

                probeTemplate = new FingerprintTemplate()
                        .dpi(500).create(bytesObjectLeftThumb);

//                probeLeftThumbTemplate = new FingerprintTemplate()
//                        .dpi(500).create(bytesObjectLeftThumb);



//                BigInteger biggi = new BigInteger(bytesObjectLeftThumb);


//                Strin.setText(LeftThumbFirst);

//                Strin.setText("okay");



//                sb = new StringBuilder(bytesObjectLeftThumb.length * Byte.SIZE);
//                for( int i = 0; i < Byte.SIZE * bytesObjectLeftThumb.length; i++ ) {
//                    sb.append((bytesObjectLeftThumb[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
//                    sb.toString();
//                }

            } else {
                errorMessage = msg.getData().getString("errorMessage");
                intent.putExtra("errorMessage", errorMessage);
            }
            // setResult(RESULT_OK, intent);
            //  finish();
        }
    };

    Handler updateFingerHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int status = msg.getData().getInt("status");

            switch (status) {
                case Status.INITIALISED:
                    ScannerStatus.setText("Setting up reader");
                    break;
                case Status.SCANNER_POWERED_ON:
                    ScannerStatus.setText("Reader powered on");
                    break;
                case Status.READY_TO_SCAN:
                    ScannerStatus.setText("Ready to scan Left Thumb Finger");
                    break;
                case Status.FINGER_DETECTED:
                    ScannerStatus.setText("Finger Detected");
                    break;
                case Status.RECEIVING_IMAGE:
                    ScannerStatus.setText("Receiving Image");
                    break;
                case Status.FINGER_LIFTED:
                    ScannerStatus.setText("Finger has been lifted off reader");
                    break;
                case Status.SCANNER_POWERED_OFF:
                    ScannerStatus.setText("Reader is off");
                    break;
                case Status.SUCCESS:
                    ScannerStatus.setText("Fingerprint Successfully Captured");
                    break;
                case Status.ERROR:
                    ScannerStatus.setText("Error");
                    break;
                default:
                    ScannerStatus.setText(String.valueOf(status));
                    break;
            }

        }
    };

    public void fetchFromDB (View v) {

        StringRequest Minutae = new StringRequest(Request.Method.POST, login_with_finger,
                response -> {
                    try {
                        may = new JSONArray(response);
                        god = may.getJSONObject(0);
                        String code = god.getString("code");
                        if ("login_failed".equals(code)) {
                            quantumElevation.setTitle("There's a mismatch somewhere");
                            exhibitElevation(god.getString("message"));
//                            LeftThumbFinger = god.getString("message");
                            Toast.makeText(getApplicationContext(), "Don't work okay",Toast.LENGTH_LONG).show();
                        } else {
                            emailString = god.getString("email");
                            accountbalanceString = god.getString("accountbalance");
                            LeftThumbFinger = god.getString("left_thumb_fingerprint");
                            LeftIndexFinger = god.getString("left_index_fingerprint");
                            RightThumbFinger = god.getString("right_thumb_fingerprint");
                            RightIndexFinger = god.getString("right_index_fingerprint");
//

                            Toast.makeText(MainActivity.this, emailString ,Toast.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this, accountbalanceString ,Toast.LENGTH_LONG).show();
//                            startActivity(serverTent);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            try {
                AlertDialog.Builder weightBuilder = new AlertDialog.Builder(MainActivity.this);
                weightBuilder.setTitle("Connection disconnected");
                weightBuilder.setMessage("You can do it. \n Swipe down from the very top and restart the wifi from its icon");
                weightBuilder.setPositiveButton("Okay", (dialog1, which1) -> dialog1.dismiss());
                weightBuilder.create().show();
                error.printStackTrace();
            } catch (Exception ignored) {

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();

                params.put("reg", regNumber.getText().toString());
                params.put("any_fingerprint", LeftThumbFake);

                return params;
            }
        };
        MyCountlesston.getmInstance(MainActivity.this).addToRequestQueue(Minutae);
//        Minutae.setRetryPolicy(new DefaultRetryPolicy(
//                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
//                0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//        ));


//        Toast.makeText(getApplicationContext(), emailString ,Toast.LENGTH_LONG).show();


//        byte [] cake = Base64.decode(LeftThumbFinger, Base64.DEFAULT);
//        byte [] muffin = Base64.decode(LeftIndexFinger, Base64.DEFAULT);
//        byte [] mash = Base64.decode(RightThumbFinger, Base64.DEFAULT);
//        byte [] nugget = Base64.decode(RightIndexFinger, Base64.DEFAULT);
//
//        candidateTemplate_One = new FingerprintTemplate().dpi(500).create(cake);
//        candidateTemplate_Two = new FingerprintTemplate().dpi(500).create(muffin);
//        candidateTemplate_Three = new FingerprintTemplate().dpi(500).create(mash);
//        candidateTemplate_Four = new FingerprintTemplate().dpi(500).create(nugget);

    }

    public void getFinger (View v) {
        fingerprint.scan(this, printFingerHandler, updateFingerHandler);

//        Toast.makeText(getApplicationContext(), emailString ,Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), accountbalanceString ,Toast.LENGTH_LONG).show();

//        fingerUpdate.setText(LeftThumbFinger);

//        if (LeftThumbFinger.isEmpty()) {
//            Toast.makeText(getApplicationContext(), emailString ,Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(getApplicationContext(), accountbalanceString, Toast.LENGTH_LONG).show();
//        }
    }

    public void exhibitElevation(String ping) {
        quantumElevation.setMessage(ping);
        quantumElevation.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                regNumber.setText("");

            }
        });
        AlertDialog alertDialog = quantumElevation.create();
        alertDialog.show();
    }

}
