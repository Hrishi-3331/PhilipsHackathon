package com.hackathon.philips.dare2complete.philips;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.philips.dare2complete.philips.Objects.Hospital;
import com.hackathon.philips.dare2complete.philips.ViewHolders.ContactViewHolder;

public class EmergencyContacts extends AppCompatActivity {

    private DatabaseReference mRef;
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        recyclerView = (RecyclerView)findViewById(R.id.contsctList);
        manager = new LinearLayoutManager(EmergencyContacts.this, LinearLayoutManager.VERTICAL, true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        mRef = FirebaseDatabase.getInstance().getReference().child("Hospitals");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Hospital, ContactViewHolder> adapter = new FirebaseRecyclerAdapter<Hospital, ContactViewHolder>(Hospital.class, R.layout.contact_view, ContactViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(ContactViewHolder viewHolder, Hospital model, int position) {
                viewHolder.setmView(model.getName(), model.getContact());
                viewHolder.setOnClickListner(EmergencyContacts.this);
            }
        };

        recyclerView.setAdapter(adapter);
    }

    public void goBack(View view){
        finish();
    }
}
