package com.hackathon.philips.dare2complete.philips.Reviews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.philips.dare2complete.philips.Objects.Doctor;
import com.hackathon.philips.dare2complete.philips.R;
import com.hackathon.philips.dare2complete.philips.ViewHolders.DoctorViewHolder;
import com.squareup.picasso.Picasso;

public class DetailReviews extends AppCompatActivity {

    private DatabaseReference mRef;
    private DatabaseReference aRef;
    private DatabaseReference bRef;
    private TextView addressView;
    private TextView nameView;
    private TextView aboutView;
    private TextView ratingView;
    private ImageView photoView;
    private RecyclerView reviewsView;
    private RecyclerView staffView;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reviews);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        mRef = FirebaseDatabase.getInstance().getReference().child("Hospitals").child(id);
        aRef = mRef.child("reviews");
        bRef = FirebaseDatabase.getInstance().getReference().child("Doctor");

        addressView = (TextView)findViewById(R.id.hosp_address);
        aboutView = (TextView)findViewById(R.id.hosp_about);
        ratingView = (TextView)findViewById(R.id.hosp_rating);
        photoView = (ImageView)findViewById(R.id.hosp_image);
        reviewsView = (RecyclerView)findViewById(R.id.hosp_reviews);
        nameView = (TextView)findViewById(R.id.hosp_name) ;
        staffView = (RecyclerView)findViewById(R.id.hosp_doctors);

        RecyclerView.LayoutManager manager = new GridLayoutManager(DetailReviews.this, 2);
        staffView.setLayoutManager(manager);

        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Doctor, DoctorViewHolder> adapter = new FirebaseRecyclerAdapter<Doctor, DoctorViewHolder>(Doctor.class, R.layout.doctor_layout, DoctorViewHolder.class, bRef) {
            @Override
            protected void populateViewHolder(DoctorViewHolder viewHolder, Doctor model, int position) {
                viewHolder.setmView(model.getName(), model.getDegree(), model.getImage());
            }
        };
        staffView.setAdapter(adapter);
    }

    private void getData() {
        mRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    nameView.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    addressView.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("description").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    aboutView.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("image").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    String uri = dataSnapshot.getValue().toString();
                    Picasso.get().load(uri).into(photoView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("rating").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    ratingView.setText(String.valueOf(dataSnapshot.getValue()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
