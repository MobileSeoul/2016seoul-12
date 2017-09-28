package com.example.rhxorhkd.android_seoulyeojido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class LaunchSplash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_splash);
        ImageView splashimage = (ImageView)findViewById(R.id.splashimage);
        //Glide.with(this).load(R.drawable.splash).into(splashimage);
        Glide.with(this).load(R.raw.splash).diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true).into(splashimage);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainintent = new Intent(LaunchSplash.this,StartActivity.class);
                startActivity(mainintent);
                finish();
            }
        },2000);
    }

}
