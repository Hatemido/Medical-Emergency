package com.example.mido.medicalemergency;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mido.medicalemergency.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorDescriptionActivity extends AppCompatActivity {


    @BindView(R.id.doctor_photo)
    CircleImageView doctorPhoto;

    @BindView(R.id.doctor_name)
    TextView doctorName;

    @BindView(R.id.doctor_info)
    TextView doctorInfo;

    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.phone_number_2)
    TextView phoneNumber2;

    @BindView(R.id.phone_number_1)
    TextView phoneNumber;

    FirebaseAuth mAuth;
    String mUid;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_description);
        ButterKnife.bind(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mUid = mAuth.getCurrentUser().getUid();
        }
        getUserInfo();
    }

    private void getUserInfo() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        reference.child(Consts.USERS).child(mUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                updateUI(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUI(User user) {
        doctorName.setText(user.getName());
        doctorInfo.setText(user.getDoctorInfo()!=null?user.getDoctorInfo():"");
        phoneNumber.setText(user.getPhone1() != null ? user.getPhone1() : "");
        phoneNumber2.setText(user.getPhone1() != null ? user.getPhone1() : "");
        location.setText(user.getLocation() != null ? user.getLocation() : "");

        if (user.getImage() != null) {
            GlideApp.with(this).load(user.getImage()).into(doctorPhoto);
        }
    }
}
