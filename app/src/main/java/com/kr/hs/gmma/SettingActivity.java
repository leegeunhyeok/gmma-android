package com.kr.hs.gmma;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by lghlo on 2017-08-15.
 */

public class SettingActivity extends AppCompatActivity {
    private Switch sw = null;
    private SharedPreferences mPref = null;

    SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            boolean dsModeON = sharedPreferences.getBoolean(key, false); // 저장된 값이 없으면 기본값 false
            String s = "";
            if(dsModeON){
                s = "새로고침 버튼을 눌러 수동으로 데이터를 불러올 수 있습니다.";
            } else {
                s = "앱 실행시 자동으로 데이터를 불러옵니다.";
            }
            Toast.makeText(SettingActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);

        Button btn = (Button)findViewById(R.id.setting_submit_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sw = (Switch)findViewById(R.id.datasave_switch);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOn = sw.isChecked();
                SharedPreferences.Editor prefEditor = mPref.edit();
                prefEditor.putBoolean("SAVEMODE_SET", isOn);
                prefEditor.apply();
            }
        });

        mPref = getSharedPreferences("Setting", Context.MODE_PRIVATE);
        boolean isOn = mPref.getBoolean("SAVEMODE_SET", false);
        mPref.registerOnSharedPreferenceChangeListener(mListener);
        sw.setChecked(isOn);
    }

    @Override
    public void onDestroy(){
        mPref.unregisterOnSharedPreferenceChangeListener(mListener);
        super.onDestroy();
    }
}
