package com.song.check;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.song.check.activity.DebugActivity;
import com.song.check.activity.EmulatorActivity;
import com.song.check.activity.HookActivity;
import com.song.check.activity.MoreOpenActivity;
import com.song.check.activity.RootActivity;
import com.song.check.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.tv_version)
    TextView versionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        versionTv.setText(Constants.VERSION);

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

    }

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
