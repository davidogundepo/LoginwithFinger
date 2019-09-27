package com.icdatofcus.loginwithfinger;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.testing.aramis.sourceafis.FingerprintMatcher;
import com.testing.aramis.sourceafis.FingerprintTemplate;
//import com.machinezoo.sourceafis.FingerprintMatcher;
//import com.machinezoo.sourceafis.FingerprintTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import asia.kanopi.fingerscan.Fingerprint;
import asia.kanopi.fingerscan.Status;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView lottie;

    EditText regNumber;
    TextView fingerUpdate, ScannerStatus;

    AlertDialog.Builder quantumElevation;

    Fingerprint fingerprint;

    public byte [] RTF, RIF;



    String LeftThumbFinger,LeftIndexFinger,RightThumbFinger,RightIndexFinger;

    public String emailString;
    public String accountbalanceString;
    public String usageString;
    public String fingString;

    private FingerprintTemplate probeTemplate, candidateTemplate_One, candidateTemplate_Two,
            candidateTemplate_Three, candidateTemplate_Four;
    private FingerprintMatcher pleaseMatch = new FingerprintMatcher();

    Button AccessingNext;

    Bitmap bitmapRightIndex;
    ImageView RightIndexImage;
    private byte[] bytesObjectRightIndex, byteAnotherObject;
    String RightIndexFirst;
    String LeftThumbFake = "ff";
//    TextView ConfirmFingerMatch, ScannerStatus;
    String login_with_finger = "http://128.0.1.2/fingerprint_login_okay.php";
    String turnUp_url = "http://128.0.1.2/my_login_okay.php";

//    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    LocationManager locationManager;

    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

//        fingerprint.scan(this, printFingerHandler, updateFingerHandler);

        RightIndexImage = findViewById(R.id.rightIndex_fp);

        quantumElevation = new AlertDialog.Builder(MainActivity.this);

        fingerprint = new Fingerprint();

        regNumber = findViewById(R.id.regNumber);
        fingerUpdate = findViewById(R.id.fingerUpdate);
        ScannerStatus = findViewById(R.id.scannerStatus);

        AccessingNext = findViewById(R.id.accessNext);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        }


//        lottie = findViewById(R.id.lottieMe);

    }

//    static {
//        ImageIO.scanForPlugins();
//    }

    @Override
    protected void onStart() {
//        fingerprint.scan(this, printFingerHandler, updateFingerHandler);
        super.onStart();
    }

    @Override
    protected void onStop() {
        fingerprint.turnOffReader();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//
//            locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//
//            locationManager.removeUpdates((LocationListener) MainActivity.this);
//        }
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
                bitmapRightIndex = BitmapFactory.decodeByteArray(image, 0, image.length);
                RightIndexImage.setImageBitmap(bitmapRightIndex);
                intent.putExtra("img", image);
                fingerUpdate.setText("Fingerprint captured");

//                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

//                Drawable drawable = getResources().getDrawable(R.drawable.index_right);
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.index_right);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmapRightIndex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                bytesObjectRightIndex = baos.toByteArray();
                RightIndexFirst = Base64.encodeToString(bytesObjectRightIndex, Base64.DEFAULT);

//                probeTemplate = new FingerprintTemplate()
//                        .dpi(500).create(bytesObjectLeftThumb);

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

            }
            else {
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
                    ScannerStatus.setText("Ready to scan Finger");
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
                    ScannerStatus.setText("No Fingerprint Reader Inserted");
                    break;
                default:
                    ScannerStatus.setText(String.valueOf(status));
                    break;
            }

        }
    };

    public void fetchFromDB (View v) {

        ProgressDialog pDialog = new ProgressDialog(this);
//            pDialog.setContentView(pDialog);
            pDialog.setTitle("Authenticating...");
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

//        Drawable drawable = getResources().getDrawable(R.drawable.index_right);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fp_coloured_four);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        bytesObjectRightIndex = baos.toByteArray();
        RightIndexFirst = Base64.encodeToString(bytesObjectRightIndex, Base64.DEFAULT);

        probeTemplate = new FingerprintTemplate().dpi(500).create(bytesObjectRightIndex);


        double score = pleaseMatch.index(this.probeTemplate).match(this.probeTemplate);
        boolean matches = score >= 40;

        if (matches) {
            Toast.makeText(MainActivity.this, "Successfully Matched Zoe Wong, Congrats", Toast.LENGTH_SHORT).show();

            Intent serverTent = new Intent(this, SecondActivity.class);
            startActivity(serverTent);
            pDialog.hide();
        }

        else {
            Toast.makeText(MainActivity.this, "Few many Changes Dave", Toast.LENGTH_SHORT).show();
//            fingerprint.scan(this, printFingerHandler, updateFingerHandler);
            pDialog.hide();
        }


//        if (RightIndexImage.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.index_right).getConstantState())
//        {
//
//            Toast toast = Toast.makeText(this, "Please, capture your fingerprint", Toast.LENGTH_LONG);
//            toast.show();
//        }
//        else if (regNumber.getText().toString().isEmpty()) {
//            Toast toast = Toast.makeText(this, "Please, your Registration Number is required", Toast.LENGTH_LONG);
//            toast.show();
//        }
//        else {
//
////            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
////
////            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getLayoutInflater();
////            final View layout = inflater.inflate(R.layout.lottie_layout, (ViewGroup) findViewById(R.id.lottieLayout));
////            builder.setView(layout);
////            Dialog dialog = builder.create();
////            dialog.show();
////            lottie = layout.findViewById(R.id.lottieLayout);
//
//            ProgressDialog pDialog = new ProgressDialog(this);
////            pDialog.setContentView(pDialog);
//            pDialog.setTitle("Authenticating...");
//            pDialog.setMessage("Loading...");
//            pDialog.setCancelable(false);
//            pDialog.show();
//
//
//            StringRequest Minutae = new StringRequest(Request.Method.POST, turnUp_url,
//                    response -> {
//                        try {
//                            pDialog.hide();
////                            dialog.dismiss();
//
//                            JSONArray may = new JSONArray(response);
//                            JSONObject god = may.getJSONObject(0);
//                            String code = god.getString("code");
//                            if ("login_failed".equals(code)) {
//                                quantumElevation.setTitle("There's a mismatch somewhere");
//                                exhibitElevation(god.getString("message"));
////                            LeftThumbFinger = god.getString("message");
//                                Toast.makeText(getApplicationContext(), "Don't work okay", Toast.LENGTH_LONG).show();
//                            }
//                            else if ("short_balance".equals(code)) {
//                                quantumElevation.setTitle("Short Balance");
//                                exhibitElevation(god.getString("short_balance_message"));
//                            }
//                            else {
//
//
//
////                            fingString = god.getString("left_thumb_fingerprint");
////                            LeftThumbFinger = god.getString("left_thumb_fingerprint");
////                            LeftIndexFinger = god.getString("left_index_fingerprint");
//                                RightThumbFinger = god.getString("right_thumb_fingerprint");
//                                RightIndexFinger = god.getString("right_index_fingerprint");
//
//                                RTF = Base64.decode(RightThumbFinger, Base64.DEFAULT);
//                                RIF = Base64.decode(RightIndexFinger, Base64.DEFAULT);
//
////                                probeTemplate = new FingerprintTemplate().dpi(500).create(bytesObjectRightIndex);
//
//
////                                candidateTemplate_One = new FingerprintTemplate().dpi(500).create(RTF);
//                                candidateTemplate_Two = new FingerprintTemplate().dpi(500).create(RIF);
//
//
//                                double score = pleaseMatch.index(this.candidateTemplate_One).match(this.candidateTemplate_One);
//                                double goal = pleaseMatch.index(this.candidateTemplate_One).match(this.candidateTemplate_Two);
//
//                                boolean matches = score >= 40;
//                                boolean leagues = goal >= 40;
//
//                                if (matches || leagues) {
//
//                                    emailString = god.getString("email");
//                                    accountbalanceString = god.getString("accountbalance");
//                                    usageString = god.getString("usage_count");
//
//                                    final String UsernameContent = emailString;
//
//                                    String usernameString_B = UsernameContent.substring(UsernameContent.indexOf(""), UsernameContent.indexOf("@"));
//
//
//                                    String usernameString = UsernameContent.substring(UsernameContent.indexOf(".")+1,UsernameContent.indexOf("@"));
//                                    String Ai = usernameString.substring(0,1).toUpperCase();
//                                    String kay = usernameString.substring(1).toLowerCase();
//                                    final String Username = Ai+kay;
//
//
//                                    Toast.makeText(MainActivity.this, "Successfully Matched " + emailString + ", Congrats", Toast.LENGTH_LONG).show();
//
//                                    Intent serverTent = new Intent(this, SecondActivity.class);
//                                    Bundle ICDAT = new Bundle();
//                                    ICDAT.putString("usage_count",Username);
//                                    ICDAT.putString("email", usernameString_B);
//                                    ICDAT.putString("accountbalance", god.getString("accountbalance"));
//                                    ICDAT.putString("sex", god.getString("sex"));
//                                    ICDAT.putString("department", god.getString("department"));
//                                    ICDAT.putString("level", god.getString("level"));
//                                    ICDAT.putString("d_n_m", god.getString("d_n_m"));
//                                    ICDAT.putString("image", god.getString("image"));
//                                    ICDAT.putString("last_seen_accept", god.getString("last_seen_accept"));
//
////                                    ICDAT.putString("right_index_fingerprint", god.getString("right_index_fingerprint"));
////                                    ICDAT.putString("right_index_fingerprint", god.getString("right_index_fingerprint"));
//                                    serverTent.putExtras(ICDAT);
//                                    startActivity(serverTent);
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Few more Changes Dave", Toast.LENGTH_SHORT).show();
//                                    fingerprint.scan(this, printFingerHandler, updateFingerHandler);
//                                }
//
////
////
////
////                            Toast.makeText(MainActivity.this, emailString ,Toast.LENGTH_LONG).show();
////                            Toast.makeText(MainActivity.this, accountbalanceString ,Toast.LENGTH_LONG).show();
////                            startActivity(serverTent);
//
//
//                            }
//
//                        } catch (JSONException e) {
//                            pDialog.hide();
////                            dialog.dismiss();
//                            e.printStackTrace();
//                            Toast.makeText(MainActivity.this, "I'm so sorry, your Info couldn't be retrieved from the Server", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }, error -> {
//                try {
//                    pDialog.hide();
////                    dialog.dismiss();
//                    AlertDialog.Builder weightBuilder = new AlertDialog.Builder(MainActivity.this);
//                    weightBuilder.setTitle("Connection disconnected");
//                    weightBuilder.setMessage("You can do it. \n Swipe down from the very top and restart the wifi from its icon");
//                    weightBuilder.setPositiveButton("Okay", (dialog1, which1) -> dialog1.dismiss());
//                    weightBuilder.create().show();
//                    error.printStackTrace();
//                } catch (Exception ignored) {
//
//                }
//
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//
//
//                    Map<String, String> params = new HashMap<>();
//
//                    params.put("reg", regNumber.getText().toString());
////                params.put("any_fingerprint", LeftThumbFake);
//
//                    return params;
//                }
//            };
//            MyCountlesston.getmInstance(MainActivity.this).addToRequestQueue(Minutae);
//
////            Minutae.setRetryPolicy(new DefaultRetryPolicy(20000, 3, 1.0f));
//
//
////        Minutae.setRetryPolicy(new DefaultRetryPolicy(
////                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
////                0,
////                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
////        ));
//        }
    }

    public void getFinger (View v) {
//        fingerprint.scan(this, printFingerHandler, updateFingerHandler);

//        Toast.makeText(getApplicationContext(), emailString ,Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), accountbalanceString ,Toast.LENGTH_LONG).show();
//
//        fingerUpdate.setText(emailString);
//        ScannerStatus.setText(usageString);

//        fingerUpdate.setText(LeftThumbFinger);

//        if (LeftThumbFinger.isEmpty()) {
//            Toast.makeText(getApplicationContext(), emailString ,Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(getApplicationContext(), accountbalanceString, Toast.LENGTH_LONG).show();
//        }
    }

    public void comFing (View v) {


//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byteCompare = baos.toByteArray();


        probeTemplate = new FingerprintTemplate().dpi(500).create(bytesObjectRightIndex);


        candidateTemplate_One = new FingerprintTemplate().dpi(500).create(RTF);


        double score = pleaseMatch.index(this.probeTemplate).match(this.candidateTemplate_One);

        boolean matches = score >= 40;

        if (matches) {
            Toast.makeText(MainActivity.this, "Successfully Matched Dave, Congrats", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Few more Changes Dave", Toast.LENGTH_SHORT).show();
        }

    }

    public void exhibitElevation(String ping) {
        quantumElevation.setMessage(ping);
        quantumElevation.setPositiveButton("Okay", (dialog, which) -> regNumber.setText(""));
        AlertDialog alertDialog = quantumElevation.create();
        alertDialog.show();
    }

}
