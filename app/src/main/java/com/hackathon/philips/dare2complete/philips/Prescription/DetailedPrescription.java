package com.hackathon.philips.dare2complete.philips.Prescription;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.philips.dare2complete.philips.Objects.Medicine;
import com.hackathon.philips.dare2complete.philips.R;
import com.hackathon.philips.dare2complete.philips.ViewHolders.PrescriptionViewHolder;

public class DetailedPrescription extends AppCompatActivity {

    private TextView doctorName;
    private TextView hospName;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRef;
    private DatabaseReference hRef;
    private RecyclerView presDetView;
    private LinearLayoutManager manager;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_prescription);

        doctorName = (TextView)findViewById(R.id.pres_doc_name);
        hospName = (TextView)findViewById(R.id.pres_hospital_name);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("Prescriptions").child("history").child(id);
        hRef = mRef.child("contents");

        presDetView = (RecyclerView)findViewById(R.id.pres_det_view);
        manager = new LinearLayoutManager(DetailedPrescription.this, LinearLayoutManager.VERTICAL, true);
        manager.setStackFromEnd(true);
        presDetView.setLayoutManager(manager);

        mRef.child("doctor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorName.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("hospital").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospName.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Medicine, PrescriptionViewHolder> adapter = new FirebaseRecyclerAdapter<Medicine, PrescriptionViewHolder>(Medicine.class, R.layout.medicine_view_layout, PrescriptionViewHolder.class, hRef) {
            @Override
            protected void populateViewHolder(PrescriptionViewHolder viewHolder, Medicine model, int position) {
                viewHolder.setmView(model.getName(), model.getInstruction(), model.isMorning(), model.isAfternoon(), model.isEvening());
            }
        };
        presDetView.setAdapter(adapter);
    }
    public void goBack(View view){
        finish();
    }
}
