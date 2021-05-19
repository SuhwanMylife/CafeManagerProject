package com.example.sw15_commute_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class TimeAll extends AppCompatActivity {
    Button btn_day, btn_detail;
    private TextView pick_day;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    @Override
    public void onBackPressed() {
        //뒤로 가면 메인페이지로
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_all);


        //이거 정보 가져오는거처럼 똑같이 하면 될듯(체크페이지)

        //뒤로가기 제어 삽입

        //방향전환
        btn_detail = findViewById(R.id.btn_detail);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeAll.this, TimeDetailActivity.class);
                startActivity(intent);
            }
        });

        pick_day = findViewById(R.id.pick_day);
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pick_day.setText(year + "년 " + month + "월 " + dayOfMonth + "일");
            }
        };

        btn_day = findViewById(R.id.btn_day);
        btn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(TimeAll.this, callbackMethod, 2021, 5, 3);
                dialog.show();
            }
        });
    }

}