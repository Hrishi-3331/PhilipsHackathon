package com.hackathon.philips.dare2complete.philips.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.hackathon.philips.dare2complete.philips.R;
import android.widget.TextView;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private View mView;
    private TextView nameView;
    private TextView rateView;
    private TextView contentView;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        nameView = mView.findViewById(R.id.rater_name);
        rateView = mView.findViewById(R.id.rater_rating);
        contentView = mView.findViewById(R.id.rating_content);
    }

    public void setmView(String name, double rating, String content) {
        nameView.setText(name);
        rateView.setText(String.valueOf(rating));
        contentView.setText(content);
    }
}
