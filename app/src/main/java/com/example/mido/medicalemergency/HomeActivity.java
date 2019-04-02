package com.example.mido.medicalemergency;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mido.medicalemergency.Doctors.DoctorsActivity;
import com.example.mido.medicalemergency.Medicines.MedicineActivity;
import com.example.mido.medicalemergency.Slider.CPRActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.mido.medicalemergency.Consts.ANALYTICS;
import static com.example.mido.medicalemergency.Consts.CLINICS;
import static com.example.mido.medicalemergency.Consts.HOSPITALS;

public class HomeActivity extends AppCompatActivity  {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private TextView navUsername;
    private CircleImageView userImage;

    @BindView(R.id.nv)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        //



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navbar_open , R.string.navbar_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = mNavigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.doctor_name);
        userImage = headerView.findViewById(R.id.doctor_photo);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.profile:
                        startProfileActivity();
                        break;
                    case R.id.LogOut:
                        LogOutFunction();
                        break;
                    case R.id.about:
                        Toast.makeText(HomeActivity.this, "about",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return false;
            }
        });





    }

    private void startProfileActivity() {
        Toast.makeText(HomeActivity.this, "profile",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HomeActivity.this , DoctorDescriptionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            setUserData(auth.getCurrentUser());

        }

    }

    void LogOutFunction(){
        FirebaseAuth.getInstance().signOut();
        goLogInActivity();
    }

    private void goLogInActivity() {
        Intent intent = new Intent(this , LoginActivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setUserData(FirebaseUser  currentUser) {
        if(currentUser.getPhotoUrl()!=null){
            Glide.with(this).load(currentUser.getPhotoUrl()).into(userImage);
        }
        navUsername.setText(currentUser.getDisplayName());
    }



    @OnClick(R.id.hospitalImageView)
     void onHospitalsClicked(){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setAction(HOSPITALS);
        startActivity(intent);

    }

    @OnClick(R.id.clinicImageView)
    void onClinicClicked(){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setAction(CLINICS);
        startActivity(intent);
    }

    @OnClick(R.id.analyticsImageView)
    void onAnalyticsClicked(){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setAction(ANALYTICS);
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
        Intent intent = new Intent(this, MedicineActivity.class);
        startActivity(intent);
    }


}
