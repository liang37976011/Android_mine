package com.example.administrator.myth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button button;
    private MyService myService;
    MyServiceConnection myServiceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.btn_show);

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            }
        }

        myServiceConnection=new MyServiceConnection();
        bindService(new Intent(MainActivity.this,MyService.class),myServiceConnection,BIND_AUTO_CREATE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startService(new Intent(MainActivity.this,MyService.class));
                if(myService!=null)
                    myService.show();
            }
        });
//        startService(new Intent(this,MyService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(MainActivity.this, "not granted", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("myth", "onServiceConnected: ");
            myService=((MyService.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("myth", "onServiceDisconnected: ");
        }
    }
 }
