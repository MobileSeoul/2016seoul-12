package com.example.rhxorhkd.android_seoulyeojido;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rhxorhkd.android_seoulyeojido.Validator.EmailValidator;
import com.example.rhxorhkd.android_seoulyeojido.Validator.NickNameValidator;
import com.example.rhxorhkd.android_seoulyeojido.Validator.PasswordValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email, nickName, pw1, pw2;
    private ImageView iv1;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference Ref;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        iv1 = (ImageView)findViewById(R.id.join_background);

        Glide.with(this).load(R.drawable.loginactivity).into(iv1);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        Ref = db.getReference("member");

        email = (EditText)findViewById(R.id.join_email);
        nickName = (EditText)findViewById(R.id.join_nickname);
        pw1 = (EditText)findViewById(R.id.pw1);
        pw2 = (EditText)findViewById(R.id.pw2);


        findViewById(R.id.join_btn_back).setOnClickListener(this);
        findViewById(R.id.done).setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(JoinActivity.this, MainActivity.class));
                    finish();
                } else {

                }
            }
        };
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.join_btn_back :
                finish();
                break;
            case R.id.done :
                if( !EmailValidator.getInstance().isValid(email.getText().toString()) )
                    Toast.makeText(getApplicationContext(),"이메일 형식이 올바르지 않습니다.",Toast.LENGTH_LONG).show();
                else if ( !NickNameValidator.getInstance().validate(nickName.getText().toString(),2,12) )
                    Toast.makeText(getApplicationContext(),"닉네임 형식이 바르지 않습니다(특수문자제외 2~12자)",Toast.LENGTH_LONG).show();
                else if( !PasswordValidator.getInstance().tvtalkValidate(pw1.getText().toString()))
                    Toast.makeText(this, "비밀번호가 올바르지 않습니다(영문, 숫자 조합 6~12자", Toast.LENGTH_LONG).show();
                else if(  !pw1.getText().toString().equals(pw2.getText().toString()) )
                    Toast.makeText(this, "비밀번호 확인이 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                else {
                    auth.createUserWithEmailAndPassword(email.getText().toString(),pw2.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(JoinActivity.this, "이미 가입된 이메일입니다.", Toast.LENGTH_SHORT).show();
                            }else{
                                final FirebaseUser user = auth.getCurrentUser();
                                Ref.child(user.getUid()+"/email").setValue(email.getText().toString());
                                Ref.child(user.getUid()+"/nickname").setValue(nickName.getText().toString());
                                Ref.child(user.getUid()+"/profile").setValue("https://firebasestorage.googleapis.com/v0/b/seoulmap-db7e1.appspot.com/o/profile%2Fprofile.png?alt=media&token=17e6a808-4662-4be3-be44-716190af4666");
                                Toast.makeText(JoinActivity.this, "성공!", Toast.LENGTH_SHORT).show();
//                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                        .setDisplayName(nickName.getText().toString())
//                                        .build();
//
//                                user.updateProfile(profileUpdates)
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task1) {
//                                                if (task1.isSuccessful()) {
//
//
//                                                }else{
//                                                    Toast.makeText(JoinActivity.this, "닉네임 에러", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
                            }
                        }
                    });
                }
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
