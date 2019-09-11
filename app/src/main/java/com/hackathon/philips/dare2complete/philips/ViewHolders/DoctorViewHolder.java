package com.hackathon.philips.dare2complete.philips.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.hackathon.philips.dare2complete.philips.R;
import com.squareup.picasso.Picasso;

import android.widget.TextView;

public class DoctorViewHolder extends RecyclerView.ViewHolder{

    private View mView;
    private TextView nameView;
    private TextView degreeView;
    private ImageView imageView;

    public DoctorViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        nameView = mView.findViewById(R.id.doc_name);
        degreeView = mView.findViewById(R.id.doc_degree);
        imageView = mView.findViewById(R.id.doc_image);
    }

    public void setmView(String name, String degree, String image) {
        nameView.setText(name);
        degreeView.setText(degree);
        Picasso.get().load(image).into(imageView);
    }
}
