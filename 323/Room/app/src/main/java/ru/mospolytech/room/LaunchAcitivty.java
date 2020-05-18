package ru.mospolytech.room;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class LaunchAcitivty extends AppCompatActivity {

    int versionCode;
    String versionName;
    Activity activity;
    TextView textVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        PackageInfo info = null;
        try {
            PackageManager pm = getPackageManager();
            if (pm != null){
                info = pm.getPackageInfo(getPackageName(), 0);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(App.app, "Ошибка", Toast.LENGTH_LONG).show();
        }
        if (info == null) {
            info = new PackageInfo();
            info.versionName = "0.0";
            info.versionCode = 0;
        }
        versionName = info.versionName;
        versionCode = info.versionCode;
        textVersion = findViewById(R.id.textVersion);
        textVersion.setText(getString(R.string.version, versionName, versionCode));

        activity = this;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}
