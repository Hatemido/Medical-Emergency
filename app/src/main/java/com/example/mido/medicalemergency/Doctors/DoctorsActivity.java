package com.example.mido.medicalemergency.Doctors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mido.medicalemergency.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorsActivity extends AppCompatActivity implements DoctorAdapter.OnDoctorClickListener {

    List<Doctor> doctorList = new ArrayList<>();
    @BindView(R.id.doctorsRecyclerView)
    RecyclerView doctorRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        getSupportActionBar().setTitle(R.string.title_activity_doctor);
        ButterKnife.bind(this);
        doctorList = new ArrayList<>();
        doctorList.add(new Doctor());
        doctorList.add(new Doctor());
        doctorList.add(new Doctor());
        doctorList.add(new Doctor());
        setDoctorRecyclerView();

    }

    void setDoctorRecyclerView(){
        DoctorAdapter adapter = new DoctorAdapter(this, doctorList, this);
        doctorRecyclerView.setAdapter(adapter);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onDoctorClicked(int position) {

    }
}
