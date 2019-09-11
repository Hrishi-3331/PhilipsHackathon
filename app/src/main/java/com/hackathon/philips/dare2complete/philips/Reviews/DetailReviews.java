package com.hackathon.philips.dare2complete.philips.Reviews;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.philips.dare2complete.philips.DetailFeed;
import com.hackathon.philips.dare2complete.philips.Objects.Doctor;
import com.hackathon.philips.dare2complete.philips.Objects.Review;
import com.hackathon.philips.dare2complete.philips.R;
import com.hackathon.philips.dare2complete.philips.ViewHolders.DoctorViewHolder;
import com.hackathon.philips.dare2complete.philips.ViewHolders.ReviewViewHolder;
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
    private EditText user_reiew;
    private FirebaseUser mUser;
    private RatingBar user_rating;

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

        user_reiew = (EditText)findViewById(R.id.review_box);
        user_rating = (RatingBar)findViewById(R.id.hosp_m_rate);
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        RecyclerView.LayoutManager manager = new GridLayoutManager(DetailReviews.this, 2);
        staffView.setLayoutManager(manager);

        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(DetailReviews.this, LinearLayoutManager.VERTICAL, true);
        reviewsView.setLayoutManager(manager1);

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

        FirebaseRecyclerAdapter<Review, ReviewViewHolder> adapter1 = new FirebaseRecyclerAdapter<Review, ReviewViewHolder>(Review.class, R.layout.rating_view, ReviewViewHolder.class, aRef) {
            @Override
            protected void populateViewHolder(ReviewViewHolder viewHolder, Review model, int position) {
                viewHolder.setmView(model.getName(), model.getRating(), model.getContent());
            }
        };

        reviewsView.setAdapter(adapter1);
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

    public void postComment(View view){
        final String comment = user_reiew.getText().toString().trim();
        InputMethodManager imm = (InputMethodManager) getSystemService(DetailFeed.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        if (!comment.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailReviews.this);
            builder.setTitle("Post comment?");
            builder.setMessage("Do you want to post this Review?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    uploadComment(comment);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void uploadComment(String comment){
        final ProgressDialog dialog = new ProgressDialog(DetailReviews.this);
        dialog.setTitle("Please wait..");
        dialog.setCanceledOnTouchOutside(false);
        DatabaseReference ref = aRef.push();
        float rating = user_rating.getRating();
        ref.child("name").setValue(mUser.getDisplayName());
        ref.child("rating").setValue(rating);
        ref.child("content").setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (!task.isSuccessful()){
                    Toast.makeText(DetailReviews.this, "Error in uploading your review. Please try again later!", Toast.LENGTH_SHORT).show();
                }
                user_reiew.setText("");
                user_reiew.clearFocus();
            }
        });
    }
}
