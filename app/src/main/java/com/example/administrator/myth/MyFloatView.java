package com.example.administrator.myth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class MyFloatView extends ImageView {

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private WindowManager wm;
    private WindowManager.LayoutParams wmParams;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyFloatView(Context context) {
        super(context);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wmParams = ((MyApplication) context).getMywmParams();
        this.setBackground(context.getResources().getDrawable(R.drawable.volume));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        x = event.getRawX();
        y = event.getRawY() - 25;   //25是系统状态栏的高度
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
                Log.d("myth", "onTouchEvent : event.getRawX():" + event.getRawX()+" event.getRawY():"+event.getRawY()+" event.getX(): "+event.getX()+" event.getY():"+event.getY());
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                updateViewPosition();
                mTouchStartX = mTouchStartY = 0;
                break;
        }
        return true;
    }

    private void updateViewPosition() {
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY);
        wm.updateViewLayout(this, wmParams);  //刷新显示
    }
}
