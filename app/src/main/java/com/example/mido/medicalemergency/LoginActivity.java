package com.example.mido.medicalemergency;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    @BindView(R.id.login_but)
    Button logIn;

    @BindView(R.id.login_with_google_btn)
    ConstraintLayout logInGoogle;

    @BindView(R.id.mail)
    TextInputEditText mailInput;

    @BindView(R.id.password)
    TextInputEditText passwordInput;

    private GoogleApiClient googleApiClient;

    private static final int GOOGLE_REQWEST = 785;

    private FirebaseAuth auth;

    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        auth=FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API ,  gso)
                .build();

        logInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,GOOGLE_REQWEST);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=mailInput.getText().toString();
                String password=passwordInput.getText().toString();
                if(mail.trim().equals("") || password.trim().equals("") ){
                    Toast.makeText(getApplicationContext() , " all feilds are requird" , Toast.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(mail , password )
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        openhome();
                                    }else{
                                        Toast.makeText(getApplicationContext() , "your e_mail or password is not correct " , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this , connectionResult.getErrorMessage() ,Toast.LENGTH_LONG ).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_REQWEST){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handelSignInResult(result);
        }
    }

    private void handelSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            openhome();
        }else{
            Toast.makeText(this,"error while login try agan" , Toast.LENGTH_LONG).show();
        }
    }

    private void openhome() {
        Intent intent = new Intent(this , HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            openhome();
        }

        user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            openhome();
        }
    }
}
