package com.hybrid.tech.mylocationv4;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyLocation extends Activity {
public static final String EXTRA="message";
    Intent intent;
    String messageText;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        intent = getIntent();
        messageText = intent.getStringExtra(EXTRA);
        tv=(TextView)findViewById(R.id.textView);
        tv.setText(messageText);

    }
}
