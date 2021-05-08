//메인페이지로 사용될 예정
package com.creapple.cafe_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CheckActivity extends AppCompatActivity {

    private TextView tv_id, tv_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        tv_id = findViewById(R.id.tv_id);
        tv_pass = findViewById(R.id.tv_pass);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userID");
        String userStore = intent.getStringExtra("userStore");

        tv_id.setText(userId);
        tv_pass.setText(userStore);
    }
}