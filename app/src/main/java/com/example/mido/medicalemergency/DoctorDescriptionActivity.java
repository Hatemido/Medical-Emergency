package com.example.mido.medicalemergency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorDescriptionActivity extends AppCompatActivity {


    @BindView(R.id.doctor_photo)
    CircleImageView  doctorPhoto;

    @BindView(R.id.doctor_name)
    TextView doctorName;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.doctor_info)
    TextView doctorInfo;

    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.phone_number_2)
    TextView phoneNumber2;

    @BindView(R.id.phone_number_1)
    TextView phoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_description);
    }
}
