package com.creapple.cafe_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.creapple.cafe_manager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeAll extends AppCompatActivity {
    Button btn_day, btn_detail;
    private TextView pick_day;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private ArrayList<Emp_work> workArrayList;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_all);

        getSupportActionBar().setTitle("일별 근무 확인");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String work_list = intent.getStringExtra("worklist");
        String emp_list = intent.getStringExtra("employeeList");
        String userStore = intent.getStringExtra("userStore");

        //이거 정보 가져오는거처럼 똑같이 하면 될듯(체크페이지)

        //뒤로가기 제어 삽입

        //방향전환
        btn_detail = findViewById(R.id.btn_detail);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeAll.this, com.creapple.cafe_manager.TimeDetailActivity.class);
                intent.putExtra("worklist", work_list);
                intent.putExtra("employeeList", emp_list);
                intent.putExtra("userStore", userStore);
                startActivity(intent);
                finish();
            }
        });

        workArrayList = new ArrayList<Emp_work>();
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat fms = new SimpleDateFormat("HH:mm");
        SimpleDateFormat fmss = new SimpleDateFormat("yyyy년 M월 d일");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("M");
        SimpleDateFormat dayFormat = new SimpleDateFormat("d");



        long now = System.currentTimeMillis();
        Date date = new Date(now);

        pick_day = findViewById(R.id.pick_day);
        pick_day.setText(yearFormat.format(date) + "년 " + monthFormat.format(date) + "월 " + dayFormat.format(date) + "일");

        try {
            JSONObject jsonObject = new JSONObject(work_list);
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            int count = 0;
            String employee_name, work_type, work_start, work_end, store_name = null;
            int flag = 0;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                store_name = object.getString("store_name");
                if(store_name.equals(userStore)){
                    employee_name = object.getString("employee_name");
                    work_type = object.getString("work_type");
                    work_start = object.getString("work_start");
                    work_end = object.getString("work_end");
                    Emp_work tmpWork = new Emp_work(employee_name, work_type, fm.parse(work_start), fm.parse(work_end));

                    workArrayList.add(tmpWork);
                    flag = 1;
                }
                count++;
            }
            if(flag == 0){
                employee_name = "";
                work_type = "";
                work_start = "1998-12-18 00:00:00";
                work_end = "1998-12-18 00:00:00";
                Emp_work tmpWork = new Emp_work(employee_name, work_type, fm.parse(work_start), fm.parse(work_end));
                workArrayList.add(tmpWork);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }

        TextView emp, work;
        for (int i = 0; i<5;i++) {
            int resId = getResources().getIdentifier("emp" + i, "id", "com.creapple.cafe_manager");
            int resId2 = getResources().getIdentifier("work" + i, "id", "com.creapple.cafe_manager");
            emp = findViewById(resId);
            emp.setText(" ");
            work = findViewById(resId2);
            work.setText(" ");
        }
        work = findViewById(R.id.work0);
        work.setText("직원이 없습니다.");

        int n = 0;
        int count = 0;
        String te = pick_day.getText().toString();
        String string = fmss.format(workArrayList.get(0).work_start);
        for (int i = 0; i<workArrayList.size();i++){
            if(pick_day.getText().equals(fmss.format(workArrayList.get(i).work_start))){
                n = i;
                count++;
            }
        }
        for (int j = count; j > 0; j--){
            int resId = getResources().getIdentifier("emp" + (count - j),"id","com.creapple.cafe_manager");
            int resId2 = getResources().getIdentifier("work" + (count - j),"id","com.creapple.cafe_manager");

            String work_start = fms.format(workArrayList.get(n-j+1).work_start);
            String work_end = fms.format(workArrayList.get(n-j+1).work_end);


            emp = findViewById(resId);
            emp.setText(workArrayList.get(n-j+1).emp_name);
            work = findViewById(resId2);
            work.setText(work_start + " ~ " + work_end);
        }

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month++;
                pick_day.setText(year + "년 " + month + "월 " + dayOfMonth + "일");
                TextView emp, work;
                for (int i = 0; i<5;i++) {
                    int resId = getResources().getIdentifier("emp" + i, "id", "com.creapple.cafe_manager");
                    int resId2 = getResources().getIdentifier("work" + i, "id", "com.creapple.cafe_manager");
                    emp = findViewById(resId);
                    emp.setText(" ");
                    work = findViewById(resId2);
                    work.setText(" ");
                }
                work = findViewById(R.id.work0);
                work.setText("직원이 없습니다.");

                int n = 0;
                int count = 0;
                for (int i = 0; i<workArrayList.size();i++){
                    if(pick_day.getText().equals(fmss.format(workArrayList.get(i).work_start))){
                        n = i;
                        count++;
                    }
                }
                for (int j = count; j > 0; j--){
                    int resId = getResources().getIdentifier("emp" + (count - j),"id","com.creapple.cafe_manager");
                    int resId2 = getResources().getIdentifier("work" + (count - j),"id","com.creapple.cafe_manager");

                    String work_start = fms.format(workArrayList.get(n-j+1).work_start);
                    String work_end = fms.format(workArrayList.get(n-j+1).work_end);


                    emp = findViewById(resId);
                    emp.setText(workArrayList.get(n-j+1).emp_name);
                    work = findViewById(resId2);
                    work.setText(work_start + " ~ " + work_end);
                }
            }

        };
        btn_day = findViewById(R.id.btn_day);

        btn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = 0, month = 0 , day = 0;
                DateFormat dateFormat = new SimpleDateFormat ("yyyy년 M월 d일");
                try {
                    Date date = dateFormat.parse(pick_day.getText().toString());
                    year = Integer.parseInt(yearFormat.format(date));
                    month = Integer.parseInt(monthFormat.format(date));
                    day = Integer.parseInt(dayFormat.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                DatePickerDialog dialog = new DatePickerDialog(TimeAll.this, callbackMethod, year, month-1,day);

                dialog.show();
            }
        });




    }

}