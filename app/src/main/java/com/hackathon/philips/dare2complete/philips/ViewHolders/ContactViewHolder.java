package com.hackathon.philips.dare2complete.philips.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.hackathon.philips.dare2complete.philips.R;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private String contact;
    private ImageButton call_btn;

    public ContactViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.hos_name);
        call_btn = itemView.findViewById(R.id.contact_btn);
    }

    public void setmView(String name, String contact) {
        this.contact = contact;
        this.name.setText(name);
    }

    public void setOnClickListner(final Context context){
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ contact));
                context.startActivity(intent);
            }
        });
    }
}
