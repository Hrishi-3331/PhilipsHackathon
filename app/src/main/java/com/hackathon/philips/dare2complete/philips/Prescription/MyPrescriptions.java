package com.hackathon.philips.dare2complete.philips.Prescription;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.philips.dare2complete.philips.Objects.Medicine;
import com.hackathon.philips.dare2complete.philips.R;
import com.hackathon.philips.dare2complete.philips.ViewHolders.PrescriptionViewHolder;

public class MyPrescriptions extends AppCompatActivity {

    private RecyclerView presView;
    private DatabaseReference mRef;
    private LinearLayoutManager manager;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prescriptions);

        presView = (RecyclerView)findViewById(R.id.pres_view);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("Prescriptions").child("current").child("contents");

        manager = new LinearLayoutManager(MyPrescriptions.this, LinearLayoutManager.VERTICAL, true);
        manager.setStackFromEnd(true);
        presView.setLayoutManager(manager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Medicine, PrescriptionViewHolder> adapter = new FirebaseRecyclerAdapter<Medicine, PrescriptionViewHolder>(Medicine.class, R.layout.medicine_view_layout, PrescriptionViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(PrescriptionViewHolder viewHolder, Medicine model, int position) {
                viewHolder.setmView(model.getName(), model.getInstruction(), model.isMorning(), model.isAfternoon(), model.isEvening());
            }
        };
        presView.setAdapter(adapter);
    }

    public void goToHistory(View view){
        startActivity(new Intent(MyPrescriptions.this, PrescriptionHistory.class));
    }

    public void goBack(View view){
        finish();
    }

}
