package com.creapple.cafe_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class SettingInfoActivity extends AppCompatActivity {

    //추가(6.1)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_info);

        //추가(6.1)
        getSupportActionBar().setTitle("회사 정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
