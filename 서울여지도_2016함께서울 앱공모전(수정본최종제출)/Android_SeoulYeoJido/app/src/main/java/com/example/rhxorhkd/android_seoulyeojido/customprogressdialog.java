package com.example.rhxorhkd.android_seoulyeojido;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class customprogressdialog extends Dialog {
    public customprogressdialog(Context context){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_customprogressdialog);
        ImageView imageView = (ImageView)findViewById(R.id.loading);

        Glide.with(context).load(R.raw.loding).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }
}
