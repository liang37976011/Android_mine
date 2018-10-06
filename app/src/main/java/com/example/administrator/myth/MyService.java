package com.example.administrator.myth;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class MyService extends Service {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        wm= (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        lp = ((MyApplication) getApplicationContext()).getMywmParams();
        myFloatView = new MyFloatView(getApplicationContext());
        lp.type = WindowManager.LayoutParams.TYPE_PHONE;
        lp.format= PixelFormat.RGBA_8888;
        lp.gravity= Gravity.RIGHT| Gravity.TOP;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // lp.gravity = Gravity.LEFT|Gravity.TOP;
        lp.height = 200;
        lp.width = 200;
        lp.horizontalMargin = 0f;
        lp.verticalMargin = 0.3f;
        return new MyBinder();
    }

    WindowManager wm;
    WindowManager.LayoutParams lp;
    MyFloatView myFloatView;
    boolean isAddFlag=false;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("myth", "onStart: ");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void show() {
        if (!isAddFlag)
        {
            wm.addView(myFloatView, lp);
            isAddFlag=true;
        }else {
            myFloatView.setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
//        if (isAddFlag)
        wm.removeView(myFloatView);
        isAddFlag=false;
    }

    public  class MyBinder extends Binder {
        public MyService getService(){
            return MyService.this;
        }

    }
}
