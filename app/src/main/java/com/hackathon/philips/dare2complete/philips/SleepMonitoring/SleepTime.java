package com.hackathon.philips.dare2complete.philips.SleepMonitoring;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.philips.dare2complete.philips.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SleepTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_time);

        getWeeklyData();

    }

    public void getWeeklyData(){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        java.util.Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("EEEE", Locale.getDefault());
        String day = format.format(date);

        String[] Days = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int[] ids = {R.id.sleep_sun, R.id.sleep_mon, R.id.sleep_tue, R.id.sleep_wed, R.id.sleep_thu, R.id.sleep_fri, R.id.sleep_sat};

        TextView dayView;
        int i = 0;
        float sleep_total = 0;
        int day_total = 0;
        SharedPreferences preferences = getSharedPreferences("SleepData", MODE_PRIVATE);

        while (i < 7){
            float time = preferences.getFloat("sleep_" + Days[i], 0);
            dayView = (TextView)findViewById(ids[i]);
            if (time != 0){
                dayView.setText(String.valueOf(time));
                sleep_total+= time;
                day_total++;
            }
            else {
                dayView.setText("NA");
            }
            i++;
        }

        float avg_sleep = sleep_total/day_total;

        TextView avg_time = (TextView)findViewById(R.id.t_avg);
        avg_time.setText(String.valueOf(avg_sleep));
    }

    public void goBack(View view){
        finish();
    }
}
