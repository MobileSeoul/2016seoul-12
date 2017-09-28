package com.example.rhxorhkd.android_seoulyeojido;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rhxorhkd.android_seoulyeojido.Validator.EmailValidator;
import com.example.rhxorhkd.android_seoulyeojido.Validator.PasswordValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email,pw;
    private ImageView iv1;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        iv1 = (ImageView)findViewById(R.id.login_background);

        Glide.with(this).load(R.drawable.loginactivity).into(iv1);

        auth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {

                }
            }
        };


        email = (EditText)findViewById(R.id.email);
        pw = (EditText)findViewById(R.id.pw);

        findViewById(R.id.login_btn_back).setOnClickListener(this);
        findViewById(R.id.submit).setOnClickListener(this);
        findViewById(R.id.go_join).setOnClickListener(this);
        findViewById(R.id.find_pw).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.go_join :
                startActivity(new Intent(this, JoinActivity.class));
                break;
            case R.id.submit :
                if(!EmailValidator.getInstance().isValid(email.getText().toString())){
                    Toast.makeText(this, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();

                }else if(!PasswordValidator.getInstance().tvtalkValidate(pw.getText().toString())){
                    Toast.makeText(this, "비밀번호 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(email.getText().toString(), pw.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "회원정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    }else{
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
                break;
            case R.id.login_btn_back :
                finish();
                break;
            case R.id.find_pw :
                startActivity(new Intent(this, FindPwActivity.class));
                finish();
                break;
            default: break;
        }
    }

    @Override
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
}
