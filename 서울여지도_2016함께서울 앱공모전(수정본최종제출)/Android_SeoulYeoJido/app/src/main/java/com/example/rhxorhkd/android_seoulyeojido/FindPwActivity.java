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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FindPwActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;

    private EditText email;
    private ImageView iv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        iv1 = (ImageView)findViewById(R.id.findpw_background);

        Glide.with(this).load(R.drawable.loginactivity).into(iv1);

        auth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.find_mail);


        findViewById(R.id.find_btn).setOnClickListener(this);
        findViewById(R.id.findpw_btn_back).setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.find_btn :
                if(!EmailValidator.getInstance().isValid(email.getText().toString())){
                    Toast.makeText(this, "잘못된 이메일 형식입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    String emailAddress = email.getText().toString();
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(FindPwActivity.this, "전송되었습니다.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(FindPwActivity.this, LoginActivity.class));
                                    }
                                }
                            });
                }
                break;
            case R.id.findpw_btn_back :
                finish();
                break;
            default: break;

        }
    }
}
