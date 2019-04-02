package com.example.mido.medicalemergency;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mido.medicalemergency.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private DialogPlus dialog;
    private User userdatafordialog;

    private ImageView save,close;
    private TextInputEditText name,information,number1,number2,addres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_description);
        ButterKnife.bind(this);


        dialog = DialogPlus.newDialog(this)
                .setCancelable(true)
                .setContentHolder(new ViewHolder(R.layout.dialog_layout))
                .setExpanded(true,1000)  // This will enable the expand feature, (similar to android L share dialog)
                .create();

        save= (ImageView) dialog.findViewById(R.id.save);
        close=(ImageView) dialog.findViewById(R.id.cancel);
        name=(TextInputEditText) dialog.findViewById(R.id.name);
        information=(TextInputEditText) dialog.findViewById(R.id.information);
        number1=(TextInputEditText) dialog.findViewById(R.id.number1);
        number2=(TextInputEditText) dialog.findViewById(R.id.number2);
        addres=(TextInputEditText) dialog.findViewById(R.id.location);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedata();
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void updatedata() {
        String stname,stinformation,stnumber1,stnumber2,staddres;
        Map<String,Object> map = new HashMap<>();

        stname=name.getText().toString().trim();
        stinformation=information.getText().toString().trim();
        stnumber1=number1.getText().toString().trim();
        stnumber2=number2.getText().toString().trim();
        staddres=addres.getText().toString().trim();

        if(!stname.isEmpty()){
            map.put("name",stname);
        }

        if(!stinformation.isEmpty()){
            map.put("doctorInfo",stinformation);
        }
        if(!stnumber1.isEmpty()){
            map.put("phone1",stnumber1);
        }
        if(!stnumber2.isEmpty()){
            map.put("phone2",stnumber2);
        }
        if(!staddres.isEmpty()){
            map.put("location",staddres);
        }

        reference.child(Consts.USERS).child(mUid).updateChildren(map);

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
        userdatafordialog=user;
        doctorName.setText(user.getName());
        doctorInfo.setText(user.getDoctorInfo()!=null?user.getDoctorInfo():"");
        phoneNumber.setText(user.getPhone1() != null ? user.getPhone1() : "");
        phoneNumber2.setText(user.getPhone2() != null ? user.getPhone2() : "");
        location.setText(user.getLocation() != null ? user.getLocation() : "");

        if (user.getImage() != null) {
            Glide.with(this).load(user.getImage()).into(doctorPhoto);
        }
    }

    private void updateDialogData(){
        name.setText(userdatafordialog.getName());
        information.setText(userdatafordialog.getDoctorInfo()!=null?userdatafordialog.getDoctorInfo():"");
        number1.setText(userdatafordialog.getPhone1() != null ? userdatafordialog.getPhone1() : "");
        number2.setText(userdatafordialog.getPhone2() != null ? userdatafordialog.getPhone2() : "");
        addres.setText(userdatafordialog.getLocation() != null ? userdatafordialog.getLocation() : "");
    }

    @OnClick(R.id.edit)
    void edit(){
        updateDialogData();
        dialog.show();
    }
}
