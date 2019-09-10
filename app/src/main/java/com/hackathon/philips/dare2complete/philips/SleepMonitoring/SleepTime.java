package com.hackathon.philips.dare2complete.philips.SleepMonitoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hackathon.philips.dare2complete.philips.R;

public class SleepTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_time);


    }

    public void goBack(View view){
        finish();
    }
}
