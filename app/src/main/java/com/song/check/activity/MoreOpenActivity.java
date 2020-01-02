package com.song.check.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.check.R;
import com.song.check.moreopen.MoreOpenCheck;
import com.song.check.utils.LogUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MoreOpenActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MoreOpenActivity";

    private Button psCheckBtn;
    private Button mapsCheckBtn;
    private Button pathCheckBtn;
    private Button pkgCheckBtn;
    private Button shellCheckBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_open);

        psCheckBtn = (Button) findViewById(R.id.btn_ps_check);
        mapsCheckBtn = (Button) findViewById(R.id.btn_maps_check);
        pathCheckBtn = (Button) findViewById(R.id.btn_path_check);
        pkgCheckBtn = (Button) findViewById(R.id.btn_pkg_check);
        shellCheckBtn = (Button) findViewById(R.id.btn_shell_check);

        psCheckBtn.setOnClickListener(this);
        mapsCheckBtn.setOnClickListener(this);
        pathCheckBtn.setOnClickListener(this);
        pkgCheckBtn.setOnClickListener(this);
        shellCheckBtn.setOnClickListener(this);
    }

    public native int moreOpenCheck();

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_ps_check:
                boolean ps = MoreOpenCheck.processCheck();
                LogUtils.i(TAG, "processCheck: " + ps);
                Toast.makeText(this, "processCheck: " + ps, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_maps_check:
                boolean maps = MoreOpenCheck.mapsCheck();
                LogUtils.i(TAG, "mapsCheck: " + maps);
                Toast.makeText(this, "mapsCheck: " + maps, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_path_check:
                boolean path = MoreOpenCheck.pathCheck(this);
                LogUtils.i(TAG, "pathCheck: " + path);
                Toast.makeText(this, "pathCheck: " + path, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_pkg_check:
                boolean pkg = MoreOpenCheck.packageCheck(this);
                LogUtils.i(TAG, "packageCheck: " + pkg);
                Toast.makeText(this, "packageCheck: " + pkg, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_shell_check:
                int shell = moreOpenCheck();
                LogUtils.i(TAG, "moreOpenCheck: " + shell);
                Toast.makeText(this, "moreOpenCheck: " + shell, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
}
