package com.hackathon.philips.dare2complete.philips.Appointments;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.philips.dare2complete.philips.Objects.Appointment;
import com.hackathon.philips.dare2complete.philips.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AppointmentDetails extends AppCompatActivity {

    private ImageView section_image;
    private TextView last_app, next_app, app_text;
    private RecyclerView app_list;
    private LinearLayoutManager manager;
    private TextView header;
    private DatabaseReference mRef,aRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        section_image = (ImageView)findViewById(R.id.appointment_image);
        last_app = (TextView)findViewById(R.id.last_app);
        next_app = (TextView)findViewById(R.id.upcoming_app);
        app_text = (TextView)findViewById(R.id.app_text);

        app_list = (RecyclerView)findViewById(R.id.prev_appointments_list);
        manager = new LinearLayoutManager(AppointmentDetails.this, LinearLayoutManager.VERTICAL, true);
        manager.setStackFromEnd(true);
        app_list.setLayoutManager(manager);

        header = (TextView)findViewById(R.id.header_title);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("appointments");

        final Intent intent = getIntent();
        category = intent.getStringExtra("category");
        String url;
        switch (category){

            case "eye":
                mRef = mRef.child("eye");
                header.setText("Eye Checkup");
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/philipshackathon-989af.appspot.com/o/appointment%20images%2Feye-chart-girl-covers-eye-1200x630.jpg?alt=media&token=041789ce-013b-4866-b577-01e1f929505e").into(section_image);
                break;

            case "teeth":
                mRef = mRef.child("teeth");
                header.setText("Dental Checkup");
                url = "https://firebasestorage.googleapis.com/v0/b/philipshackathon-989af.appspot.com/o/appointment%20images%2FDentist-Opener.png?alt=media&token=95a8a228-744e-460d-86f4-37ed02436fd1";
                Picasso.get().load(url).into(section_image);
                break;

            case "general":
                mRef = mRef.child("general");
                header.setText("General Health Checkup");
                url = "https://firebasestorage.googleapis.com/v0/b/philipshackathon-989af.appspot.com/o/appointment%20images%2Fphysician.jpg?alt=media&token=1274372f-e89e-4509-9417-119527e2d3ee";
                Picasso.get().load(url).into(section_image);
                break;

            case "skin":
                mRef = mRef.child("skin");
                header.setText("Skin Care Checkup");
                url = "https://firebasestorage.googleapis.com/v0/b/philipshackathon-989af.appspot.com/o/appointment%20images%2Fskin2.jpg?alt=media&token=26c1f4c3-e81d-47de-aa1c-527c63376c1b";
                Picasso.get().load(url).into(section_image);
                break;

            case "heart":
                mRef = mRef.child("heart");
                header.setText("Cardio Checkup");
                url = "https://firebasestorage.googleapis.com/v0/b/philipshackathon-989af.appspot.com/o/appointment%20images%2Fheart.jpg?alt=media&token=f1a0603d-6ce6-4f11-955a-18ba4ad3c959";
                Picasso.get().load(url).into(section_image);
                break;
        }

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        java.util.Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        final String mDate = format.format(date);

        mRef.child("last_appointment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    last_app.setText(dataSnapshot.getValue().toString());
                    last_app.setTextColor(Color.BLACK);
                    String temp = dataSnapshot.getValue().toString().trim();
                    int m1 = Integer.valueOf(temp.split("/")[1]);
                    int m2 = Integer.valueOf(mDate.split("/")[1]);
                    int y1 = Integer.valueOf(temp.split("/")[2]);
                    int y2 = Integer.valueOf(mDate.split("/")[2]);
                    int interval = (y2 - y1) * 12 + (m2 - m1);
                    if (interval >= 6){
                        app_text.setText(getString(R.string.st1) + " " + interval + " " + getString(R.string.st2));
                    }else {
                        app_text.setText(getString(R.string.st1) + " " + interval + " " + " months back. You must book an appointment after " + (6 - interval) + " months");
                    }
                }
                else {
                    last_app.setText("Data unavailable");
                    last_app.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("next_appointment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    next_app.setText(dataSnapshot.getValue().toString());
                    next_app.setTextColor(Color.BLACK);
                    app_text.setText("You have your next appointment on " + dataSnapshot.getValue().toString());
                }
                else {
                    next_app.setText("Data unavailable");
                    next_app.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        aRef = mRef.child("history");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Appointment, AppointmentViewHolder> adapter = new FirebaseRecyclerAdapter<Appointment, AppointmentViewHolder>(Appointment.class, R.layout.appointment_view, AppointmentViewHolder.class, aRef) {
            @Override
            protected void populateViewHolder(AppointmentViewHolder viewHolder, Appointment model, int position) {
                viewHolder.setmView(model.getHospital(), model.getDate());
            }
        };

        app_list.setAdapter(adapter);
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView hospital_name;
        private TextView app_date;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            hospital_name = mView.findViewById(R.id.app_hospital_name);
            app_date = mView.findViewById(R.id.app_date);
        }

        public void setmView(String name, String date){
            hospital_name.setText(name);
            app_date.setText(date);
        }
    }

    public void goBack(View view){
        finish();
    }

}
