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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private TextView navUsername;
    private CircleImageView userImage;

    @BindView(R.id.nv)
    NavigationView nv;

    private String name ="", mail="" ;
    private Uri imguri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navbar_open , R.string.navbar_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = nv.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.doctor_name);
        userImage = headerView.findViewById(R.id.doctor_photo);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.profile:
                        startprofileactivity();
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



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API ,  gso)
                .build();

    }

    private void startprofileactivity() {
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

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }

    }

    void LogOutFunction(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(HomeActivity.this, "you are loged out",Toast.LENGTH_SHORT).show();
                    goLogInActivity();
                }else{
                    Toast.makeText(getApplicationContext() , "error while Log out" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goLogInActivity() {
        Intent intent = new Intent(this , LoginActivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setdata(String displayName, String email, Uri s) {
        name=displayName;
        mail=email;
        imguri=s;

        if(imguri!=null){
            Glide.with(this).load(imguri).into(userImage);
        }
        navUsername.setText(name);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        GoogleSignInAccount account = result.getSignInAccount();
        setdata(account.getDisplayName() , account.getEmail(),account.getPhotoUrl());
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
        Intent intent = new Intent(this, MedicineActivity.class);
        startActivity(intent);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this , connectionResult.getErrorMessage() ,Toast.LENGTH_LONG ).show();
    }
}
