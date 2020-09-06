package com.isha.doctors_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Appointment_details extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5;
    String contact,date,time,desc,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        t1=findViewById(R.id.textView3);
        t2=findViewById(R.id.textView4);
        t3=findViewById(R.id.textView5);
        t4=findViewById(R.id.textView6);
        t5=findViewById(R.id.textView7);
        name = getIntent().getStringExtra("name");
        contact = getIntent().getStringExtra("contact");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        desc = getIntent().getStringExtra("desc");
        t1.setText(name);
        t2.setText(contact);
        t3.setText(date);
        t4.setText(time);
        t5.setText(desc);

    }

    public void confirm(View view) {

    }
}
