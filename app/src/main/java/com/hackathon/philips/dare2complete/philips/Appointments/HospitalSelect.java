package com.hackathon.philips.dare2complete.philips.Appointments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.philips.dare2complete.philips.Objects.Hospital;
import com.hackathon.philips.dare2complete.philips.R;
import com.squareup.picasso.Picasso;

public class HospitalSelect extends AppCompatActivity {

    private static String category;
    private RecyclerView hospital_list;
    private LinearLayoutManager manager;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_select);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        hospital_list = (RecyclerView)findViewById(R.id.hospital_select_list);
        manager = new LinearLayoutManager(HospitalSelect.this, LinearLayoutManager.VERTICAL, true);
        manager.setStackFromEnd(true);
        hospital_list.setLayoutManager(manager);

        mref = FirebaseDatabase.getInstance().getReference().child("Hospitals");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Hospital, HospitalViewHolder> adapter = new FirebaseRecyclerAdapter<Hospital, HospitalViewHolder>(Hospital.class, R.layout.review_block, HospitalViewHolder.class, mref) {
            @Override
            protected void populateViewHolder(HospitalViewHolder viewHolder, Hospital model, int position) {
                viewHolder.filter(getRef(position), model.getName(), model.getImage(), model.getAddress(), model.getRating());
                viewHolder.setListner(HospitalSelect.this, getRef(position).getKey());
            }
        };
        hospital_list.setAdapter(adapter);
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private ImageView image_block;
        private TextView title_block;
        private TextView address_block;
        private TextView rating_block;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            image_block = mView.findViewById(R.id.block_image);
            title_block = mView.findViewById(R.id.block_title);
            address_block = mView.findViewById(R.id.block_address);
            rating_block = mView.findViewById(R.id.block_rating);
        }

        public void setmView(String title, String image, String address, double rating){
            try{
                Picasso.get().load(image).into(image_block);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            title_block.setText(title);
            address_block.setText(address);
            rating_block.setText(String.valueOf(rating));
        }

        private void filter(DatabaseReference reference, final String title, final String image, final String address, final double rating){
            reference.child("department").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean temp = (Boolean)dataSnapshot.child(category).getValue();
                    if (!temp){
                        mView.setVisibility(View.GONE);
                    }
                    else {
                        setmView(title, image, address, rating);
                        mView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        private void setListner(final Context context, final String id){
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BookAppointment.class);
                    intent.putExtra("id", id);
                    intent.putExtra("category", category);
                    context.startActivity(intent);
                }
            });
        }

    }

    public void goBack(View view){
        finish();
    }
}

