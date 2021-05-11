package com.creapple.cafe_manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Employee_clicked extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_clicked);

        TextView name = (TextView)findViewById(R.id.name);
        TextView position = (TextView)findViewById(R.id.position);
        TextView salary = (TextView)findViewById(R.id.salary);
        TextView hour = (TextView)findViewById(R.id.hour);
        TextView total_salary = (TextView)findViewById(R.id.total_salary);

        TextView work_normal = (TextView)findViewById(R.id.work_normal);
        TextView work_over = (TextView)findViewById(R.id.work_over);
        TextView work_night = (TextView)findViewById(R.id.work_night);

        Intent intent = getIntent();

        DecimalFormat myFormatter = new DecimalFormat("###,###");


        name.setText("이름 : " + intent.getStringExtra("emp_name"));
        position.setText("직책 : " + intent.getStringExtra("position"));
        salary.setText("시급 : " + intent.getStringExtra("salary") + "원");
        hour.setText(intent.getStringExtra("working_hour") + "시간 근무");
        double normal, over, night ;
        normal = intent.getDoubleExtra("work_normal",0);
        over = intent.getDoubleExtra("work_over",0);
        night = intent.getDoubleExtra("work_night",0);

        total_salary.setText("총 급여 : " + myFormatter.format(intent.getDoubleExtra("total_salary",0)) +  "원");

        work_normal.setText(Double.toString(normal) + "시간" );
        work_over.setText(Double.toString(over) + "시간" );
        work_night.setText(Double.toString(night) + "시간" );



    }
}