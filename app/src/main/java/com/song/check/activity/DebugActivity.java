package com.song.check.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.song.check.R;
import com.song.check.debug.DebugCheck;
import com.song.check.utils.LogUtils;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_debug_open_debug)
    public void openDebug(){
        boolean isOpenDebug = DebugCheck.isOpenDebug(this);
        LogUtils.i("isOpenDebug: " + isOpenDebug);
        Toast.makeText(this, "isOpenDebug: " + isOpenDebug, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_debug_version)
    public void versionCheck(){
        boolean debugVersionCheck = DebugCheck.debugVersionCheck(this);
        LogUtils.i("debugVersionCheck: " + debugVersionCheck);
        Toast.makeText(this, "debugVersionCheck: " + debugVersionCheck, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_debug_connected)
    public void connectedCheck(){
        boolean connectedCheck = DebugCheck.connectedCheck();
        LogUtils.i("connectedCheck: " + connectedCheck);
        Toast.makeText(this, "connectedCheck: " + connectedCheck, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_debug_tracerpid)
    public void tracerPidJniCheck(){
        int tracerPidCheck = tracerPidCheck();
        LogUtils.i("tracerPidCheck: " + tracerPidCheck);
        Toast.makeText(this, "tracerPidCheck: " + tracerPidCheck, Toast.LENGTH_SHORT).show();
    }

    public native int tracerPidCheck();
}
