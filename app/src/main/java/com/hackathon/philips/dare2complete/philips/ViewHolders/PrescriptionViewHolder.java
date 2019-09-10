package com.hackathon.philips.dare2complete.philips.ViewHolders;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.hackathon.philips.dare2complete.philips.R;
import android.widget.TextView;

public class PrescriptionViewHolder extends RecyclerView.ViewHolder{

    private View mView;
    private TextView nameView;
    private TextView instructionView;
    private ImageView m_t, a_t, e_t;

    public PrescriptionViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        nameView = itemView.findViewById(R.id.med_name);
        instructionView = itemView.findViewById(R.id.med_instructions);
        m_t = itemView.findViewById(R.id.morning);
        a_t = itemView.findViewById(R.id.afternoon);
        e_t = itemView.findViewById(R.id.evening);
    }

    public void setmView(String name, String instruction, boolean m, boolean a, boolean e){
        nameView.setText(name);
        instructionView.setText(instruction);
        if (m){
            m_t.setImageResource(R.drawable.tick);
        }else {
            m_t.setImageResource(R.drawable.cross);
        }

        if (a){
            a_t.setImageResource(R.drawable.tick);
        }else {
            a_t.setImageResource(R.drawable.cross);
        }

        if (e){
            e_t.setImageResource(R.drawable.tick);
        }else {
            e_t.setImageResource(R.drawable.cross);
        }
    }

}
