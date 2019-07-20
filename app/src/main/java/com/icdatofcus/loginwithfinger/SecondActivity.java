package com.icdatofcus.loginwithfinger;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Objects;

public class SecondActivity extends AppCompatActivity {

    TextView email, usage, acc;

    String email_String = "email";
    String usage_String = "usage_count";
    String accountbalance_String = "accountbalance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        email = findViewById(R.id.email);
        usage = findViewById(R.id.usage);
        acc = findViewById(R.id.acc);

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



    }
}
