package com.example.rhxorhkd.android_seoulyeojido;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.rhxorhkd.android_seoulyeojido.RankRecyclerView.RankAdapter;
import com.example.rhxorhkd.android_seoulyeojido.Validator.NickNameValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ChangeInfo extends AppCompatActivity implements View.OnClickListener {

    private EditText nickName;
    private ImageView iv1, background;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference Ref;
    private FirebaseStorage storage;
    private StorageReference storageReference, profileRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        iv1 = (ImageView)findViewById(R.id.change_img);
        background = (ImageView)findViewById(R.id.change_info_back);
        nickName = (EditText)findViewById(R.id.change_nickname);

        Glide.with(this).load(R.drawable.loginactivity).into(background);


        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        Ref = db.getReference("member");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://seoulmap-db7e1.appspot.com");


        findViewById(R.id.chage_back).setOnClickListener(this);
        findViewById(R.id.change_img).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);



        FirebaseUser user = auth.getCurrentUser();
        Ref.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                nickName.setText(""+data.child("nickname").getValue());
                Glide.with(ChangeInfo.this).load(""+data.child("profile").getValue()).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv1){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(this.getView().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        iv1.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public static int REQ_CODE_SELECT_IMAGE = 100;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chage_back :

                if(!NickNameValidator.getInstance().validate(nickName.getText().toString(),2,12)){
                    Toast.makeText(getApplicationContext(),"닉네임 형식이 바르지 않습니다(특수문자제외 2~12자)",Toast.LENGTH_LONG).show();
                }else{
                    final FirebaseUser user = auth.getCurrentUser();
                    Ref.child(user.getUid()+"/nickname").setValue(nickName.getText().toString());
                    Toast.makeText(this, "닉네임 변경 완료", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.change_img :
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQ_CODE_SELECT_IMAGE);
                break;
            case R.id.logout :
                auth.getInstance().signOut();
                startActivity(new Intent(ChangeInfo.this, StartActivity.class));
                finish();
                break;
            default:break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                final FirebaseUser user = auth.getCurrentUser();
                Glide.with(this).load(data.getData()).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv1) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(this.getView().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        iv1.setImageDrawable(circularBitmapDrawable);
                    }
                });

                Uri uri = data.getData();
                profileRef = storageReference.child("profile/"+user.getUid()+".png");

                profileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        Ref.child(user.getUid()+"/profile").setValue(downloadUrl.toString());
                        Toast.makeText(ChangeInfo.this, "사진변경완료", Toast.LENGTH_SHORT).show();

//                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                .setPhotoUri(downloadUrl)
//                                .build();

//                        user.updateProfile(profileUpdates)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Ref.child(user.getUid()+"/profile").setValue(downloadUrl.toString());
//                                            Toast.makeText(ChangeInfo.this, "사진변경완료", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChangeInfo.this, "네트워크 상태가 불안정합니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
