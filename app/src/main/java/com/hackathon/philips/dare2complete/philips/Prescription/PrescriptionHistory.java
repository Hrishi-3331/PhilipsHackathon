package com.hackathon.philips.dare2complete.philips.Prescription;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.philips.dare2complete.philips.Objects.Prescription;
import com.hackathon.philips.dare2complete.philips.R;
import com.hackathon.philips.dare2complete.philips.ViewHolders.HistoryViewHolder;

public class PrescriptionHistory extends AppCompatActivity {

    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private RecyclerView presView;
    private LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_history);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("Prescriptions").child("history");
        presView = (RecyclerView)findViewById(R.id.pres_history);

        manager = new LinearLayoutManager(PrescriptionHistory.this, LinearLayoutManager.VERTICAL, true);
        manager.setStackFromEnd(true);
        presView.setLayoutManager(manager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Prescription, HistoryViewHolder> adapter = new FirebaseRecyclerAdapter<Prescription, HistoryViewHolder>(Prescription.class, R.layout.pres_view, HistoryViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(HistoryViewHolder viewHolder, Prescription model, int position) {
                viewHolder.setmView(model.getDoctor(), model.getHospital(), model.getRemark(), model.getDate_start(), model.getDate_end());
                viewHolder.setOnclickListner(PrescriptionHistory.this, getRef(position).getKey());
            }
        };
        presView.setAdapter(adapter);
    }

    public void goBack(View view){
        finish();
    }
}
