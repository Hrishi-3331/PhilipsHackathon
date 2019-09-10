package com.hackathon.philips.dare2complete.philips;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.philips.dare2complete.philips.Appointments.MyAppointments;
import com.hackathon.philips.dare2complete.philips.Objects.Feed;
import com.hackathon.philips.dare2complete.philips.Prescription.MyPrescriptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/** API Format : http://api.airvisual.com/v2/nearest_city?lat=21.1263&lon=79.0516&key=61e49232-d2fc-487d-a1db-0be0aa6321b0
 *
 * data -> city
 *      -> state
 *      -> country
 *      -> current
 *          -> pollution
 *              -> aqius (air quality index)
 *              -> mainus (main pollutants)
 * **/

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private NavigationView mNavigation;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private static TextView ws, hu, aqi, level, mainus, city;
    private TextView footsteps, calories, sleep;
    private static ImageView weather_image;
    private SensorManager sensorManager;
    private RecyclerView feedView;
    private LinearLayoutManager manager;
    private DatabaseReference mRef;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mDrawer = (DrawerLayout) findViewById(R.id.mDrawer);
        mNavigation = (NavigationView) findViewById(R.id.mNavigation);
        mToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawer, toolbar, R.string.open, R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        View header = mNavigation.getHeaderView(0);
        final ImageView user_image = (ImageView) header.findViewById(R.id.user_header_image);
        TextView name = (TextView) header.findViewById(R.id.user_name);
        TextView email = (TextView) header.findViewById(R.id.header_email);

        email.setText(mUser.getEmail());
        name.setText(mUser.getDisplayName());

        ws = (TextView)findViewById(R.id.ws);
        hu = (TextView)findViewById(R.id.hu);
        aqi = (TextView)findViewById(R.id.aqi);
        level = (TextView)findViewById(R.id.aqi_level);
        mainus = (TextView)findViewById(R.id.mpi);
        city = (TextView)findViewById(R.id.location);
        weather_image = (ImageView)findViewById(R.id.weather_icon);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        footsteps = (TextView)findViewById(R.id.footsteps);
        calories = (TextView)findViewById(R.id.calories);

        feedView = (RecyclerView)findViewById(R.id.main_feed);
        manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, true);
        manager.setStackFromEnd(true);
        feedView.setLayoutManager(manager);
        feedView.hasFixedSize();

        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.prescriptions:
                        startActivity(new Intent(MainActivity.this, MyPrescriptions.class));
                        break;

                    default:
                        break;
                }
                mDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        FetchData data = new FetchData();
        data.execute();

        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    footsteps.setText(String.valueOf((int)event.values[0]));
                 }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, countSensor, SensorManager.SENSOR_DELAY_UI);

        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;

                case R.id.navigation_profile:
                    return true;

                case R.id.navigation_appointments:
                    startActivity(new Intent(MainActivity.this, MyAppointments.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Feed, FeedViewHolder> adapter = new FirebaseRecyclerAdapter<Feed, FeedViewHolder>(Feed.class, R.layout.feed_layout, FeedViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(FeedViewHolder viewHolder, Feed model, int position) {
                viewHolder.setmView(model.getTitle(), model.getImage(), model.getContent());
                viewHolder.implementListener(MainActivity.this, getRef(position).getKey());
            }
        };

        feedView.setAdapter(adapter);
        navigation.setSelectedItemId(R.id.navigation_home);

    }

    private static class FetchData extends AsyncTask<Void, Void, Void> {

        private String cityd;
        private String country;
        private String icon;
        private double wsd;
        private double hud;
        private double aqiusd;
        private String mainusd;

        @Override
        protected Void doInBackground(Void... voids) {
            StringBuilder response = new StringBuilder();
            URL download_url = null;
            try {
                download_url = new URL("http://api.airvisual.com/v2/nearest_city?lat=21.1263&lon=79.0516&key=61e49232-d2fc-487d-a1db-0be0aa6321b0");
                HttpURLConnection connection = (HttpURLConnection) download_url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream iStream = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(iStream));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String responseJSON = response.toString();

            try {
                JSONObject obj = new JSONObject(responseJSON);
                JSONObject data = obj.getJSONObject("data");
                cityd = data.getString("city");
                country = data.getString("country");
                JSONObject weather = data.getJSONObject("current").getJSONObject("weather");
                icon = weather.getString("ic");
                wsd = weather.getDouble("ws");
                hud = weather.getDouble("hu");
                JSONObject pollution = data.getJSONObject("current").getJSONObject("pollution");
                aqiusd = pollution.getDouble("aqius");
                mainusd = pollution.getString("mainus");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ws.setText(String.valueOf(wsd) + " mph");
            hu.setText(String.valueOf(hud));
            aqi.setText(String.valueOf(aqiusd));
            mainus.setText(mainusd);
            city.setText(cityd + ", " + country);

            weather_image.setImageResource(R.drawable.a01d);
            int image_resource;

            switch (icon){
                case "01d":
                    image_resource = R.drawable.a01d;
                    break;

                case "01n":
                    image_resource = R.drawable.a01n;
                    break;

                case "02d":
                    image_resource = R.drawable.a02d;
                    break;

                case "02n":
                    image_resource = R.drawable.a02n;
                    break;

                case "03d":
                    image_resource = R.drawable.a03d;
                    break;

                case "04d":
                    image_resource = R.drawable.a04d;
                    break;

                case "09d":
                    image_resource = R.drawable.a09d;
                    break;

                case "10d":
                    image_resource = R.drawable.a10d;
                    break;

                case "10n":
                    image_resource = R.drawable.a10n;
                    break;

                case "11d":
                    image_resource = R.drawable.a11d;
                    break;

                case "13d":
                    image_resource = R.drawable.a13d;
                    break;

                case "50d":
                    image_resource = R.drawable.a50d;
                    break;

                default:
                    image_resource = R.drawable.a50d;
                    break;

            }
            weather_image.setImageResource(image_resource);

            if (aqiusd <= 50){
                level.setText("GOOD");
            }
            else if(aqiusd <= 100){
                level.setText("MODERATE");
            }
            else if(aqiusd <= 150){
                level.setText("UNHEALTHY");
            }
            else if(aqiusd <= 200){
                level.setText("MODERATELY UNHEALTHY");
                level.setTextColor(Color.RED);
            }
            else if(aqiusd <= 300){
                level.setText("VERY UNHEALTHY");
                level.setTextColor(Color.rgb(128,0,128));
            }
            else {
                level.setText("HAZARDOUS");
                level.setTextColor(Color.rgb(128, 0,0));

            }

        }

    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView titleView;
        private ImageView imageView;
        private TextView contentView;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            titleView = mView.findViewById(R.id.feed_title);
            imageView = mView.findViewById(R.id.feed_image);
            contentView = mView.findViewById(R.id.feed_content);
        }

        public void setmView(String title, String image_url, String content){
            titleView.setText(title);
            contentView.setText(content);
            try{
                Picasso.get().load(image_url).into(imageView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void implementListener(final Context context, final String id){
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailFeed.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });
        }
    }


}
