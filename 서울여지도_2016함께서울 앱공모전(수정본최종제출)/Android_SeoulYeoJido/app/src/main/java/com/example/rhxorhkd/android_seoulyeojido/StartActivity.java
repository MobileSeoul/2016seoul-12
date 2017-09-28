package com.example.rhxorhkd.android_seoulyeojido;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ImageView iv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        iv1 = (ImageView)findViewById(R.id.start_background);

        Glide.with(this).load(R.drawable.start_activity).skipMemoryCache(true).into(iv1);

        ActionBar ab = getSupportActionBar();
        ab.hide();


        findViewById(R.id.login_btn).setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                } else {

                }
            }
        };




    }@Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn :
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default: break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);

        finish();

        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
