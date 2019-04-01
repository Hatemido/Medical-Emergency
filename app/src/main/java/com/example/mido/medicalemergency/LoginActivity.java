package com.example.mido.medicalemergency;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_but)
    Button logIn;

    @BindView(R.id.login_with_google_btn)
    ConstraintLayout logInGoogle;

    @BindView(R.id.mail)
    TextInputEditText mailInput;

    @BindView(R.id.password)
    TextInputEditText passwordInput;

    @BindView(R.id.signup)
    TextView signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
