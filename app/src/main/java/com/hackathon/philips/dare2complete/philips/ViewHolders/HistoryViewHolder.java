package com.hackathon.philips.dare2complete.philips.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.hackathon.philips.dare2complete.philips.R;

import com.hackathon.philips.dare2complete.philips.Prescription.DetailedPrescription;

public class HistoryViewHolder extends RecyclerView.ViewHolder{

    private View mView;
    private TextView doctor;
    private TextView hospital;
    private TextView date_start;
    private TextView date_end;
    private TextView remark;

    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        doctor = mView.findViewById(R.id.doctor_name);
        hospital = mView.findViewById(R.id.hospital_name);
        date_start = mView.findViewById(R.id.d_start);
        date_end = mView.findViewById(R.id.d_end);
        remark = mView.findViewById(R.id.remarks);
    }

    public void setmView(String doctor, String hospital, String remark, String date_start, String date_end){
        this.date_end.setText(date_end);
        this.date_start.setText(date_start);
        this.doctor.setText(doctor);
        this.hospital.setText(hospital);
        this.remark.setText(remark);
    }

    public void setOnclickListner(final Context context, final String id){
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedPrescription.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
    }
}
