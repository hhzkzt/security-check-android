package com.song.check.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.check.R;
import com.song.check.emulator.EmulatorCheckPerfect;

import androidx.appcompat.app.AppCompatActivity;

public class EmulatorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmulatorActivity";

    private Button buildCheckBtn;
    private Button qemuCheckBtn;
    private Button propertyCheckBtn;
    private Button cpuCheckBtn;
    private Button pipCheckBtn;
    private Button buildInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);

        buildCheckBtn = ((Button) findViewById(R.id.btn_build_check));
        qemuCheckBtn = ((Button) findViewById(R.id.btn_qemu_check));
        propertyCheckBtn = ((Button) findViewById(R.id.btn_property_check));
        cpuCheckBtn = ((Button) findViewById(R.id.btn_cpu_check));
        pipCheckBtn = ((Button) findViewById(R.id.btn_pip_check));
        buildInfoBtn = ((Button) findViewById(R.id.btn_build_info));

        buildCheckBtn.setOnClickListener(this);
        qemuCheckBtn.setOnClickListener(this);
        propertyCheckBtn.setOnClickListener(this);
        cpuCheckBtn.setOnClickListener(this);
        pipCheckBtn.setOnClickListener(this);
        buildInfoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_build_check:
                Toast.makeText(this, "build check: " + EmulatorCheckPerfect.buildCheck(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_qemu_check:
                EmulatorCheckPerfect.qemuCheck();
                Toast.makeText(this, "qemu check: " + EmulatorCheckPerfect.qemuCheck(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_property_check:
                EmulatorCheckPerfect.checkSystemProperty();
                Toast.makeText(this, "property check: " + EmulatorCheckPerfect.checkSystemProperty(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_cpu_check:
                EmulatorCheckPerfect.checkCpuInfo();
                Toast.makeText(this, "cpuinfo check: " + EmulatorCheckPerfect.checkCpuInfo(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_pip_check:
                EmulatorCheckPerfect.checkPipes();
                Toast.makeText(this, "pips check: " + EmulatorCheckPerfect.checkPipes(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_build_info:
                String deviceInfo = EmulatorCheckPerfect.getDeviceInfo();
                Log.i(TAG, deviceInfo);
                Toast.makeText(this, deviceInfo, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
}
