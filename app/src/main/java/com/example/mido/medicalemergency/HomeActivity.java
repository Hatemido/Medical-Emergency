package com.example.mido.medicalemergency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.mido.medicalemergency.Doctors.DoctorsActivity;
import com.example.mido.medicalemergency.Slider.CPRActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.hospitalImageView)
     void onHospitalsClicked(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.clinicImageView)
    void onClinicClicked(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.analyticsImageView)
    void onAnalyticsClicked(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.doctorImageView)
    void onDoctorsClicked() {
        Intent intent = new Intent(this,DoctorsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cprImageView)
    void onCPRClicked(){
        Intent intent = new Intent(this, CPRActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.medicineImageView)
    void onMedicineClicked() {
    }




}
