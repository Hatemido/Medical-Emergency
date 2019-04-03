package com.example.mido.medicalemergency.Medicines;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mido.medicalemergency.Consts;
import com.example.mido.medicalemergency.Doctors.DoctorAdapter;
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

import static com.example.mido.medicalemergency.Consts.USERS;

public class MedicineActivity extends AppCompatActivity implements MedicineAdapter.OnMedicineClickListener, DoctorAdapter.OnDoctorClickListener {


    List<Medicine> medicineList;

    String mUid;
    String mEmail;
    @BindView(R.id.medicineRecyclerView)
    RecyclerView medicineRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        ButterKnife.bind(this);
        getMedicines();
    }

    void getMedicines() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        reference.child(Consts.MEIDINCES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicineList = new ArrayList<>();
                for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                    Medicine medicine = childSnap.getValue(Medicine.class);
                    medicine.setId(childSnap.getKey());

                    medicineList.add(medicine);
                }
                setMedicineRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    void setMedicineRecyclerView() {
        findViewById(R.id.loading).setVisibility(View.GONE);
        MedicineAdapter adapter = new MedicineAdapter(this, medicineList, this);
        medicineRecyclerView.setAdapter(adapter);
        medicineRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onMedicineClicked(int position) {
        final Medicine medicine = medicineList.get(position);
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.user_dialog))
                .setGravity(Gravity.BOTTOM)
                .create();
        boolean isExists = false;
        if (medicine.getUsers() != null) {
            List<User> userList = new ArrayList<User>(medicine.getUsers().values());
            isExists = isCurrentUserInIt(userList);
            DoctorAdapter adapter = new DoctorAdapter(this, userList, this);
            RecyclerView usersReyclerView = (RecyclerView) dialog.findViewById(R.id.userRecyclerView);
            usersReyclerView.setAdapter(adapter);
            usersReyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        TextView addUserTextView = (TextView) dialog.findViewById(R.id.addTextView);
        addUserTextView.setText(isExists?"-":"+");
        final boolean finalIsExists = isExists;
        addUserTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
               if(finalIsExists){
                   removeFromUsers(medicine);
               }else {
                   addUserToServer(medicine);
               }
            }
        });
        ImageView closeDialog = (ImageView) dialog.findViewById(R.id.cancel);
                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    private void removeFromUsers(Medicine medicine) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        reference.getRoot().child(Consts.MEIDINCES).child(medicine.getId()).child("users").child(mUid).removeValue();

    }

    private boolean isCurrentUserInIt(List<User> userList) {

        for (User user:userList)
        {
            if(user.getEmail().equals(mEmail))
                return true;
        }
        return false;
    }

    private void addUserToServer(final Medicine medicine) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        reference.child(USERS).child(mUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getPhone1() != null) {
                    reference.getRoot().child(Consts.MEIDINCES).child(medicine.getId()).child("users").child(mUid).setValue(user);
                } else {
                    Toast.makeText(MedicineActivity.this, "please Add your Phone Number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDoctorClicked(int position) {

    }

    @Override
    public void onCallClicked(int position, User user) {
        Log.e("here", "onCallClicked: ");
        callIntent(user.getPhone1());
    }

    void callIntent(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}
