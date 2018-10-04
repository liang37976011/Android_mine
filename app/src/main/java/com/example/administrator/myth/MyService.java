package com.example.administrator.myth;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.WindowManager;

public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyFloatView myFloatView;
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams lp = ((MyApplication) getApplicationContext()).getMywmParams();
        myFloatView = new MyFloatView(getApplicationContext());
        lp.type = WindowManager.LayoutParams.TYPE_PHONE;
        lp.format= PixelFormat.RGBA_8888;
        lp.gravity=Gravity.LEFT| Gravity.TOP;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // lp.gravity = Gravity.LEFT|Gravity.TOP;
        lp.height = 200;
        lp.width = 200;
        lp.horizontalMargin = 0.3f;
        lp.verticalMargin = 0.3f;

        wm.addView(myFloatView, lp);
        return super.onStartCommand(intent, flags, startId);
    }
}
