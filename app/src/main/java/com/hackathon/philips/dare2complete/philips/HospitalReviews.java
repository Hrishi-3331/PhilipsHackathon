package com.hackathon.philips.dare2complete.philips;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.philips.dare2complete.philips.Objects.Hospital;
import com.squareup.picasso.Picasso;

public class HospitalReviews extends AppCompatActivity {

    private RecyclerView hospital_list;
    private DatabaseReference mRef;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_reviews);

        mRef = FirebaseDatabase.getInstance().getReference().child("Hospital review");
        hospital_list = (RecyclerView)findViewById(R.id.hospital_list);

        manager = new LinearLayoutManager(HospitalReviews.this, LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        hospital_list.setLayoutManager(manager);
    }

    @Override
    protected void onStart() {
        super.onStart();
       FirebaseRecyclerAdapter<Hospital, HospitalViewHolder> adapter = new FirebaseRecyclerAdapter<Hospital, HospitalViewHolder>(Hospital.class, R.layout.review_block, HospitalViewHolder.class, mRef) {
           @Override
           protected void populateViewHolder(HospitalViewHolder viewHolder, Hospital model, int position) {
               viewHolder.setmView(model.getName(), model.getImage(), model.getDescription(), model.getRating());
           }
       };
        hospital_list.setAdapter(adapter);

    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private ImageView image_block;
        private TextView title_block;
        private TextView description_block;
        private RatingBar rating_block;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            image_block = mView.findViewById(R.id.block_image);
            title_block = mView.findViewById(R.id.block_title);
            description_block = mView.findViewById(R.id.block_description);
            rating_block = mView.findViewById(R.id.block_ratingBar);
        }

        public void setmView(String title, String image, String description, int rating){
            try{
                Picasso.get().load(image).into(image_block);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            title_block.setText(title);
            description_block.setText(description);
        }

    }

    public void goBack(View view){
        finish();
    }
}
