package com.song.check.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.song.check.R;
import com.song.check.hook.HookCheck;
import com.song.check.utils.LogUtils;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_hook_package_check)
    public void pkgCheck() {
        boolean packageCheck = HookCheck.packageCheck(this);
        LogUtils.i("packageCheck: " + packageCheck);
        Toast.makeText(this, "packageCheck: " + packageCheck, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_hook_file_check)
    public void mapsCheck() {
        boolean mapsCheck = HookCheck.mapsCheck();
        LogUtils.i("mapsCheck: " + mapsCheck);
        Toast.makeText(this, "mapsCheck: " + mapsCheck, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_hook_exception_check)
    public void exceptionCheck() {
        boolean exceptionCheck = HookCheck.exceptionCheck();
        LogUtils.i("exceptionCheck: " + exceptionCheck);
        Toast.makeText(this, "exceptionCheck: " + exceptionCheck, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_hook_class_check)
    public void classCheck() {
        boolean classCheck = HookCheck.classCheck();
        LogUtils.i("classCheck: " + classCheck);
        Toast.makeText(this, "classCheck: " + classCheck, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_hook_jni_pkg)
    public void packageJniCheck() {
        int packageCheck = packageCheck();
        LogUtils.i("packageCheck: " + packageCheck);
        Toast.makeText(this, "packageCheck: " + packageCheck, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_hook_jni_file)
    public void fileJniCheck() {
        int fileCheck = fileCheck();
        LogUtils.i("fileCheck: " + fileCheck);
        Toast.makeText(this, "fileCheck: " + fileCheck, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_hook_xhook_jni)
    public void xhookJniCheck() {
        int xhookCheck = xhookCheck();
        LogUtils.i("xhookCheck: " + xhookCheck);
        Toast.makeText(this, "xhookCheck: " + xhookCheck, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_hook_jni_substrate)
    public void substrateJniCheck() {
        int substrateCheck = substrateCheck();
        LogUtils.i("substrateCheck: " + substrateCheck);
        Toast.makeText(this, "substrateCheck: " + substrateCheck, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_hook_jni_inline)
    public void inlineJniCheck() {
        int inlineCheck = inlineCheck();
        LogUtils.i("inlineCheck: " + inlineCheck);
        Toast.makeText(this, "inlineCheck: " + inlineCheck, Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_hook_jni_plt)
    public void pltJniCheck() {
        int pltCheck = pltCheck();
        LogUtils.i("pltCheck: " + pltCheck);
        Toast.makeText(this, "pltCheck: " + pltCheck, Toast.LENGTH_SHORT).show();
    }

    public native int packageCheck();

    public native int fileCheck();

    public native int xhookCheck();

    public native int substrateCheck();

    public native int inlineCheck();

    public native int pltCheck();
}
