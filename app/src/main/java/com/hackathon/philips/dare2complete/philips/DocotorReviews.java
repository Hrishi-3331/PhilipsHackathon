package com.hackathon.philips.dare2complete.philips;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.hackathon.philips.dare2complete.philips.Objects.Doctor;
import com.hackathon.philips.dare2complete.philips.Objects.Hospital;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DocotorReviews extends AppCompatActivity {

    private RecyclerView hospital_list;
    private DatabaseReference mRef;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docotor_reviews);
        mRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
        hospital_list = (RecyclerView)findViewById(R.id.doctor_list);

        manager = new LinearLayoutManager(DocotorReviews.this, LinearLayoutManager.VERTICAL, true);
        manager.setStackFromEnd(true);
        hospital_list.setLayoutManager(manager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Doctor, DoctorView> adapter = new FirebaseRecyclerAdapter<Doctor, DoctorView>(Doctor.class, R.layout.review_block, DoctorView.class, mRef) {
            @Override
            protected void populateViewHolder(DoctorView viewHolder, Doctor model, int position) {
                viewHolder.setmView(model.getName(), model.getImage(), model.getDegree(), model.getSpecialisation(), model.getRating());
            }
        };
        hospital_list.setAdapter(adapter);

    }

    public static class DoctorView extends RecyclerView.ViewHolder{

        private View mView;
        private ImageView image_block;
        private TextView title_block;
        private TextView description_block;
        private TextView rating_block;

        public DoctorView(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            image_block = mView.findViewById(R.id.block_image);
            title_block = mView.findViewById(R.id.block_title);
            description_block = mView.findViewById(R.id.block_address);
            rating_block = mView.findViewById(R.id.block_rating);
        }

        public void setmView(String title, String image, String degree, String specialisation, double rating){
            try{
                Picasso.get().load(image).into(image_block, new Callback() {
                    @Override
                    public void onSuccess() {
                        try{
                            cropImage();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
            }
            title_block.setText(title);
            description_block.setText(degree + "\n\n" + getSpecialization(specialisation));
        }

        private String getSpecialization(String key){
            switch (key){
                case "eye":
                    return "Ophthalmologist";

                case "ent":
                    return "Ear, Nose and Throat specialist";

                case "heart":
                    return "Cardiologist";

                case "teeth":
                    return "Dentist";

                case "general":
                    return "General Physician";

            }
            return "Physician";
        }

        private void cropImage() {
            Drawable drawable = image_block.getDrawable();
            if (drawable!= null) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                image_block.setImageBitmap(getclip(bitmapDrawable.getBitmap()));
            }
        }

        Bitmap getclip(Bitmap bitmap) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                    bitmap.getWidth() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        }

    }

    public void goBack(View view){
        finish();
    }

}
