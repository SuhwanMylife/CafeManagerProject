package com.creapple.cafe_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Time_clicked extends AppCompatActivity {
    

    private DatePickerDialog.OnDateSetListener callbackMethod;

    private ArrayList<Emp_work> workArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_clicked);

        Intent intent = getIntent();
        String work_list = intent.getStringExtra("worklist");
        TextView emp_name = findViewById(R.id.name);
        TextView emp_position = findViewById(R.id.position);
        emp_name.setText(intent.getStringExtra("emp_name"));
        emp_position.setText(intent.getStringExtra("position"));

        Button btn_term = findViewById(R.id.btn_term);
        TextView pick_term = findViewById(R.id.pick_term);

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

        SimpleDateFormat fms = new SimpleDateFormat("HH:mm");

        DateFormat dateFormat = new SimpleDateFormat("yyyy년 M월 d일");

        long now = System.currentTimeMillis();
        Date now_date = new Date(now);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now_date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        int count = 0;
        while (week != 0){
            week--;
            count++;
        }
        cal.add(Calendar.DATE,-count + 1);
        Date start_date = new Date(cal.getTimeInMillis());
        cal.add(Calendar.DATE,6);
        Date end_date = new Date(cal.getTimeInMillis());
        pick_term.setText(yearFormat.format(start_date) + "년 " + monthFormat.format(start_date) + "월 " + dayFormat.format(start_date) + "일" +
                "~" + yearFormat.format(end_date) + "년 " + monthFormat.format(end_date) + "월 " + dayFormat.format(end_date) + "일");

//        try {
//            JSONObject jsonObject = new JSONObject(work_list);
//            JSONArray jsonArray = jsonObject.getJSONArray("response");
//
//            int count = 0;
//            String employee_name, work_type, work_start, work_end = null;
//
//            while (count < jsonArray.length()) {
//                JSONObject object = jsonArray.getJSONObject(count);
//                employee_name = object.getString("employee_name");
//                work_type = object.getString("work_type");
//                work_start = object.getString("work_start");
//                work_end = object.getString("work_end");
//                Emp_work tmpWork = new Emp_work(employee_name, work_type, fm.parse(work_start), fm.parse(work_end));
//
//                workArrayList.add(tmpWork);
//                count++;
//            }
//        } catch (JSONException | ParseException e) {
//            e.printStackTrace();
//        }

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                Date pick_date = new Date(year-1900,month,dayOfMonth);
                cal.setTime(pick_date);
                int week = cal.get(Calendar.DAY_OF_WEEK);
                int count = 0;
                while (week != 0){
                    week--;
                    count++;
                }
                cal.add(Calendar.DATE,-count + 1);
                Date start_date = new Date(cal.getTimeInMillis());
                cal.add(Calendar.DATE,6);
                Date end_date = new Date(cal.getTimeInMillis());
                pick_term.setText(yearFormat.format(start_date) + "년 " + monthFormat.format(start_date) + "월 " + dayFormat.format(start_date) + "일" +
                        "~" + yearFormat.format(end_date) + "년 " + monthFormat.format(end_date) + "월 " + dayFormat.format(end_date) + "일");


//                    for (int i = 0; i<7;i++) {
//                        int resId = getResources().getIdentifier("work_start" + i, "id", "com.creapple.cafe_manager");
//                        int resId2 = getResources().getIdentifier("work_end" + i, "id", "com.creapple.cafe_manager");
//                        work_start = findViewById(resId);
//                        work_start.setText(" ");
//                        work_end = findViewById(resId2);
//                        work_end.setText(" ");
//                    }

//                    for(int i = 0; i<7;i++){
//                        int resId = getResources().getIdentifier("work_start" + i, "id", "com.creapple.cafe_manager");
//                        int resId2 = getResources().getIdentifier("work_end" + i, "id", "com.creapple.cafe_manager");
//                        work_start = findViewById(resId);
//                        work_start.setText(fms.format(workArrayList.get(i).getWork_start()));
//                        work_end = findViewById(resId2);
//                        work_end.setText(fms.format(workArrayList.get(i).getWork_end()));
//                    }

            }
        };
        btn_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int year = 0, month = 0, day = 0;
                    year = Integer.parseInt(yearFormat.format(now_date));
                    month = Integer.parseInt(monthFormat.format(now_date));
                    day = Integer.parseInt(dayFormat.format(now_date));

                    DatePickerDialog dialog = new DatePickerDialog(Time_clicked.this, callbackMethod, year, month - 1, day);

                    dialog.show();
            }
        });
    }
}

