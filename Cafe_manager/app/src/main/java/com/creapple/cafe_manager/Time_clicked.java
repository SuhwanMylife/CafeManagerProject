package com.creapple.cafe_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Time_clicked extends AppCompatActivity {

    private ArrayList<Emp_work> workArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_clicked);

        Intent intent = getIntent();
        String work_list = intent.getStringExtra("worklist");
        String emp_list = intent.getStringExtra("employeeList");
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            JSONObject jsonObject = new JSONObject(work_list);
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            int count = 0;
            String employee_name, work_type, work_start, work_end = null;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                employee_name = object.getString("employee_name");
                work_type = object.getString("work_type");
                work_start = object.getString("work_start");
                work_end = object.getString("work_end");
                Emp_work tmpWork = new Emp_work(employee_name, work_type, fm.parse(work_start), fm.parse(work_end));

                workArrayList.add(tmpWork);
                count++;
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i<5;i++) {
            int resId = getResources().getIdentifier("work_start" + i, "id", "com.creapple.cafe_manager");
            int resId2 = getResources().getIdentifier("work_end" + i, "id", "com.creapple.cafe_manager");
            TextView emp, work;
            emp = findViewById(resId);
            emp.setText(" ");
            work = findViewById(resId2);
            work.setText(" ");
        }
    }
}