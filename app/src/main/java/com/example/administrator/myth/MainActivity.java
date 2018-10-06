package com.example.administrator.myth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button btn_show;
    private Button btn_hide;
    private MyService myService;
    MyServiceConnection myServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_show = findViewById(R.id.btn_show);
        btn_hide = findViewById(R.id.btn_hide);

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            }
        }

        myServiceConnection = new MyServiceConnection();
        bindService(new Intent(MainActivity.this, MyService.class), myServiceConnection, BIND_AUTO_CREATE);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
//                startService(new Intent(MainActivity.this,MyService.class));
                if (myService != null)
                    myService.show();
            }
        });
        btn_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myService != null)
                    myService.hide();
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
            myService = ((MyService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("myth", "onServiceDisconnected: ");
        }
    }
}
