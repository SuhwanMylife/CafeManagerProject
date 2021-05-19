package com.creapple.cafe_manager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.creapple.cafe_manager.R;
import com.creapple.cafe_manager.TimeAll;

public class TimeDetailActivity extends AppCompatActivity {
    Button btn_simply;
    
    //https://recipes4dev.tistory.com/58 프레그먼트로 구현

    @Override
    public void onBackPressed() {
        //뒤로 가면 메인페이지로
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_detail);

        btn_simply = findViewById(R.id.btn_simply);
        btn_simply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeDetailActivity.this, TimeAll.class);
                startActivity(intent);
            }
        });
    }
}