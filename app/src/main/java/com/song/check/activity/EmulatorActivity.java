package com.song.check.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.check.R;
import com.song.check.emulator.EmulatorCheckPerfect;
import com.song.check.utils.LogUtils;

import androidx.appcompat.app.AppCompatActivity;

public class EmulatorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmulatorActivity";

    private Button buildCheckBtn;
    private Button qemuCheckBtn;
    private Button propertyCheckBtn;
    private Button cpuCheckBtn;
    private Button pipCheckBtn;
    private Button buildInfoBtn;

    private Button fileCheckJniBtn;
    private Button thermalCheckJniBtn;
    private Button buildCheckJniBtn;
    private Button bluetoothCheckJniBtn;

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

        fileCheckJniBtn = ((Button) findViewById(R.id.btn_jni_file_check));
        thermalCheckJniBtn = ((Button) findViewById(R.id.btn_jni_thermal_check));
        buildCheckJniBtn = ((Button) findViewById(R.id.btn_jni_build_check));
        bluetoothCheckJniBtn = ((Button) findViewById(R.id.btn_jni_bluetooth_check));

        buildCheckBtn.setOnClickListener(this);
        qemuCheckBtn.setOnClickListener(this);
        propertyCheckBtn.setOnClickListener(this);
        cpuCheckBtn.setOnClickListener(this);
        pipCheckBtn.setOnClickListener(this);
        buildInfoBtn.setOnClickListener(this);

        fileCheckJniBtn.setOnClickListener(this);
        thermalCheckJniBtn.setOnClickListener(this);
        buildCheckJniBtn.setOnClickListener(this);
        bluetoothCheckJniBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_build_check:
                Toast.makeText(this, "build check: " + EmulatorCheckPerfect.buildCheck(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_qemu_check:
                Toast.makeText(this, "qemu check: " + EmulatorCheckPerfect.qemuCheck(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_property_check:
                Toast.makeText(this, "property check: " + EmulatorCheckPerfect.checkSystemProperty(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_cpu_check:
                Toast.makeText(this, "cpuinfo check: " + EmulatorCheckPerfect.checkCpuInfo(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_pip_check:
                Toast.makeText(this, "pips check: " + EmulatorCheckPerfect.checkPipes(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_build_info:
                String deviceInfo = EmulatorCheckPerfect.getDeviceInfo();
                LogUtils.i(TAG, deviceInfo);
                Toast.makeText(this, deviceInfo, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_jni_file_check:
                boolean filesEmulatorcheck = specialFilesEmulatorcheck();
                LogUtils.i(TAG, "filesEmulatorcheck: " + filesEmulatorcheck);
                Toast.makeText(this, "" + filesEmulatorcheck, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_jni_thermal_check:
                boolean thermalCheck = thermalCheck();
                LogUtils.i(TAG, "thermalCheck: " + thermalCheck);
                Toast.makeText(this, "thermalCheck: " + thermalCheck, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_jni_build_check:
                boolean buildCheck = buildCheck();
                LogUtils.i(TAG, "buildCheck: " + buildCheck);
                Toast.makeText(this, "buildCheck: " + buildCheck, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_jni_bluetooth_check:
                boolean bluetoothCheck = bluetoothCheck();
                LogUtils.i(TAG, "bluetoothCheck: " + bluetoothCheck);
                Toast.makeText(this, "bluetoothCheck: " + bluetoothCheck, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    public native boolean specialFilesEmulatorcheck();

    public native boolean thermalCheck();

    public native boolean buildCheck();

    public native boolean bluetoothCheck();
}
