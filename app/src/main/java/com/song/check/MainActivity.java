package com.song.check;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.song.check.activity.DebugActivity;
import com.song.check.activity.EmulatorActivity;
import com.song.check.activity.HookActivity;
import com.song.check.activity.MoreOpenActivity;
import com.song.check.activity.RootActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private Button emulatorCheckBtn;
    private Button moreOpenCheckBtn;
    private Button rootCheckBtn;
    private Button hookCheckBtn;
    private Button debugCheckbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emulatorCheckBtn = ((Button) findViewById(R.id.btn_emulator_check));
        moreOpenCheckBtn = ((Button) findViewById(R.id.btn_more_open_check));
        rootCheckBtn = ((Button) findViewById(R.id.btn_root_check));
        hookCheckBtn = ((Button) findViewById(R.id.btn_hook_check));
        debugCheckbtn = ((Button) findViewById(R.id.btn_debug_check));

        emulatorCheckBtn.setOnClickListener(this);
        moreOpenCheckBtn.setOnClickListener(this);
        rootCheckBtn.setOnClickListener(this);
        hookCheckBtn.setOnClickListener(this);
        debugCheckbtn.setOnClickListener(this);

        // Example of a call to a native method
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_emulator_check:
                startActivity(new Intent(this, EmulatorActivity.class));
                break;
            case R.id.btn_more_open_check:
                startActivity(new Intent(this, MoreOpenActivity.class));
                break;
            case R.id.btn_hook_check:
                startActivity(new Intent(this, HookActivity.class));
                break;
            case R.id.btn_root_check:
                startActivity(new Intent(this, RootActivity.class));
                break;
            case R.id.btn_debug_check:
                startActivity(new Intent(this, DebugActivity.class));
                break;
            default:
                break;

        }


    }
}
