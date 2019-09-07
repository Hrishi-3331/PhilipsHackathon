package com.hackathon.philips.dare2complete.philips.Appointments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.philips.dare2complete.philips.R;

import java.util.Calendar;

public class BookAppointment extends AppCompatActivity {

    private String id;
    private String category;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String Sdate;
    private String Sslot;
    private TextView date_select, slot_select, date, time, fees, text, hospital, address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        category = intent.getStringExtra("category");
        Sdate = "NA";
        Sslot = "NA";

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mRef = FirebaseDatabase.getInstance().getReference().child("Hospitals").child(id);
        date_select = (TextView)findViewById(R.id.date_select);
        slot_select = (TextView)findViewById(R.id.slot_select);
        date = (TextView)findViewById(R.id.booking_date);
        time = (TextView)findViewById(R.id.booking_time);
        fees = (TextView)findViewById(R.id.booking_fees);
        text = (TextView)findViewById(R.id.booking_text);
        hospital = (TextView)findViewById(R.id.booking_hospital);
        address = (TextView)findViewById(R.id.booking_address);

        mRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospital.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                address.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("Appointments").child(category).child("fees").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    fees.setText("₹" + dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void selectDate(View view){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Sdate = dayOfMonth + "/" + month + "/" + year;
                        date_select.setText(Sdate);
                        date.setText(Sdate);
                    }

                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void selectSlot(View view){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BookAppointment.this);
        LayoutInflater inflater = getLayoutInflater();
        View DialogLayout = inflater.inflate(R.layout.slot_select_dialog, null);
        builder.setView(DialogLayout);

        Button ok = (Button) DialogLayout.findViewById(R.id.btn_ok);
        Button cancel = (Button) DialogLayout.findViewById(R.id.btn_cancel);
        LinearLayout slot1 = (LinearLayout)DialogLayout.findViewById(R.id.slot1);
        LinearLayout slot2 = (LinearLayout)DialogLayout.findViewById(R.id.slot2);
        LinearLayout slot3 = (LinearLayout)DialogLayout.findViewById(R.id.slot3);


        final android.app.AlertDialog select_dialog = builder.create();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.slot1:
                        Sslot = "9:00 AM to 01:00 PM";
                        break;

                    case R.id.slot2:
                        Sslot = "3:00 PM to 07:00 PM";
                        break;

                    case R.id.slot3:
                        Sslot = "7:00 PM to 09:30 PM";
                        break;
                }

                slot_select.setText(Sslot);
                time.setText(Sslot);
                select_dialog.dismiss();
            }
        };

        slot1.setOnClickListener(listener);
        slot2.setOnClickListener(listener);
        slot3.setOnClickListener(listener);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_dialog.dismiss();
            }
        });

        select_dialog.show();
    }

    public void ConfirmBooking(View view){

        if (Sdate == "NA" || Sslot == "NA"){
            Toast.makeText(BookAppointment.this, "Please select date and slot", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(BookAppointment.this);
        dialog.setTitle("Booking your appointment");
        dialog.setMessage("Please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        DatabaseReference reference = mRef.child("Appointments").child(category).child("bookings").push();
        reference.child("slot").setValue(Sslot);
        reference.child("date").setValue(Sdate);
        reference.child("id").setValue(mUser.getUid());
        DatabaseReference aRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("appointments").child(category);
        aRef.child("next_appointment").setValue(Sdate);
        DatabaseReference bRef = aRef.child("history").push();
        bRef.child("date").setValue(Sdate);
        bRef.child("hospital").setValue(hospital.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(BookAppointment.this, "Appointment booked successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(BookAppointment.this, "Error in booking appointment. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
