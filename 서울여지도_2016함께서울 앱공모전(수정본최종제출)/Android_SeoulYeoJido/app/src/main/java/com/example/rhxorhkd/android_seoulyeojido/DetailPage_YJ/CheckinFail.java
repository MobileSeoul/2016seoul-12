package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.ImageVideoWrapperEncoder;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.rhxorhkd.android_seoulyeojido.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hanyoojin on 2016. 10. 27..
 */

public class CheckinFail extends Activity {

    private TimerTask mTask;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀바 삭제
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkinfail);

        ImageView failgif = (ImageView)findViewById(R.id.img_failGIF);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(failgif);
        Glide.with(this).load(R.raw.check_in_fail).diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true).into(imageViewTarget);


        mTask = new TimerTask() {
            @Override
            public void run() { finish();
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, 4000);
        // mTimer.schedule(mTask, 3000, 5000);


        //findViewById(R.id.btn_checkincancel).setOnClickListener(this);



    }

    @Override
    protected void onDestroy() {
        Log.i("test", "onDstory()");
        mTimer.cancel();
        super.onDestroy();
    }


}
