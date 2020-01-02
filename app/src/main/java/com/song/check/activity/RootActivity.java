package com.song.check.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.song.check.R;
import com.song.check.root.RootCheck;
import com.song.check.utils.LogUtils;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_root_su_v)
    public void suCheck() {
        String su = rootCheck();
        LogUtils.i("suCheck: " + su);
        Toast.makeText(this, "suCheck: " + su, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_root_file_check)
    public void fileCheck() {
        boolean fileCheck = RootCheck.fileCheck();
        LogUtils.i("fileCheck: " + fileCheck);
        Toast.makeText(this, "fileCheck: " + fileCheck, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_root_pkg_check)
    public void pkgCheck() {
        boolean pkgCheck = RootCheck.rootPackagesCheck(this);
        LogUtils.i("pkgCheck: " + pkgCheck);
        Toast.makeText(this, "pkgCheck: " + pkgCheck, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_root_property_check)
    public void propertyCheck() {
        boolean propertyCheck = RootCheck.dangerousPropertiesCheck();
        LogUtils.i("propertyCheck: " + propertyCheck);
        Toast.makeText(this, "propertyCheck: " + propertyCheck, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_root_rw_check)
    public void rwCheck() {
        boolean rwCheck = RootCheck.rwPathsCheck();
        LogUtils.i("rwCheck: " + rwCheck);
        Toast.makeText(this, "rwCheck: " + rwCheck, Toast.LENGTH_SHORT).show();
    }

    public native String rootCheck();
}
