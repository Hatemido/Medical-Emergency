package com.example.mido.medicalemergency.Doctors;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mido.medicalemergency.Consts;
import com.example.mido.medicalemergency.DoctorDescriptionActivity;
import com.example.mido.medicalemergency.HomeActivity;
import com.example.mido.medicalemergency.Models.User;
import com.example.mido.medicalemergency.R;
import com.example.mido.medicalemergency.UserCallDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mido.medicalemergency.Consts.DOCTOR;
import static com.example.mido.medicalemergency.Consts.USERS;

public class DoctorsActivity extends AppCompatActivity implements DoctorAdapter.OnDoctorClickListener {

    List<User> doctorList = new ArrayList<>();
    @BindView(R.id.doctorsRecyclerView)
    RecyclerView doctorRecyclerView;
    private DialogPlus dialog;
    @BindView(R.id.loading)
    ProgressBar loadingProgresBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_doctor);
        ButterKnife.bind(this);

        getDoctors();

    }

    void getDoctors() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        reference.child(DOCTOR).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorList = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    User user=childSnapshot.getValue(User.class);
                    user.setDoctorid(childSnapshot.getKey());
                    doctorList.add(user);
                }
                setDoctorRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void setDoctorRecyclerView() {
        loadingProgresBar.setVisibility(View.GONE);
        DoctorAdapter adapter = new DoctorAdapter(this, doctorList, this);
        doctorRecyclerView.setAdapter(adapter);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onDoctorClicked(int position) {
        Intent intent = new Intent(this, DoctorDescriptionActivity.class);
        intent.putExtra("userid", doctorList.get(position).getDoctorid());
        startActivity(intent);
    }

    @Override
    public void onCallClicked(int position,User user) {
        UserCallDialog.callDialog(this, user);
    }


    @OnClick(R.id.fab)
    void onAddDoctorClicked() {
        dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.add_doctor))
                .setGravity(Gravity.CENTER)
                .create();
        final Spinner doctorTypeSpinner = (Spinner) dialog.findViewById(R.id.typeSpinner);
        TextView addDoctorTextView = (TextView) dialog.findViewById(R.id.addButton);
        TextView cancelTextView = (TextView) dialog.findViewById(R.id.cancelButton);
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        addDoctorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addDoctor(doctorTypeSpinner.getSelectedItem().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();

    }

    private void addDoctor(final String type) throws Exception {
        final String currentDoctorUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);

        reference.child(USERS).child(currentDoctorUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.setType(type);
                if (user.getPhone1()!=null) {
                    reference.getRoot().child(DOCTOR).child(currentDoctorUid).setValue(user);

                } else {
                    Toast.makeText(DoctorsActivity.this, "Add your Phone Number in Profile Screen", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DoctorsActivity.this, DoctorDescriptionActivity.class);
                    intent.putExtra("userid", currentDoctorUid);
                    startActivity(intent);
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
            }
        });


    }
}
