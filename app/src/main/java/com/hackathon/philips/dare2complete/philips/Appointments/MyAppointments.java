package com.hackathon.philips.dare2complete.philips.Appointments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hackathon.philips.dare2complete.philips.R;

public class MyAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
    }

    public void goBack(View view){
        finish();
    }

    public void viewDetails(View view){

        Intent intent = new Intent(MyAppointments.this, AppointmentDetails.class);

        switch (view.getId()){
            case R.id.eye_btn:
               intent.putExtra("category", "eye");
               break;

            case R.id.teeth_btn:
                intent.putExtra("category", "teeth");
                break;

            case R.id.general_btn:
                intent.putExtra("category", "general");
                break;

            case R.id.skin_btn:
                intent.putExtra("category", "skin");
                break;

            case R.id.heart_btn:
                intent.putExtra("category", "heart");
                break;
        }
        startActivity(intent);
    }
}

