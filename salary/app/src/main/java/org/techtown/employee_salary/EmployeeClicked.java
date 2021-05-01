package org.techtown.employee_salary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class EmployeeClicked extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeeclicked);

        TextView name = (TextView)findViewById(R.id.name);
        TextView position = (TextView)findViewById(R.id.position);
        TextView salary = (TextView)findViewById(R.id.salary);
        TextView hour = (TextView)findViewById(R.id.hour);
        TextView total_salary = (TextView)findViewById(R.id.total_salary);
        Intent intent = getIntent();

        DecimalFormat myFormatter = new DecimalFormat("###,###");

        name.setText("이름 : " + intent.getStringExtra("emp_name"));
        position.setText("직책 : " + intent.getStringExtra("position"));
        salary.setText("시급 : " + intent.getStringExtra("salary") + "원");
        hour.setText(intent.getStringExtra("working_hour") + "시간 근무");
        int temp1 = Integer.parseInt(intent.getStringExtra("salary"));
        int temp2 = Integer.parseInt(intent.getStringExtra("working_hour"));
        total_salary.setText("총 급여 : " + myFormatter.format(temp1 * temp2) + "원");


    }
}