package com.creapple.cafe_manager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {
    private ListView listView;
    private EmployeeListAdapter adapter;
    private List<Employee> employeeList;
    ArrayList<Emp_work> workArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView);
        employeeList = new ArrayList<Employee>();
        workArrayList = new ArrayList<Emp_work>();
        adapter = new EmployeeListAdapter(getApplicationContext(),employeeList);
        listView.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("worklist"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            int count = 0;
            String employee_name, work_start, work_end = null;
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                employee_name = object.getString("employee_name");
                work_start = object.getString("work_start");
                work_end = object.getString("work_end");
                Emp_work tmpWork = new Emp_work(employee_name, fm.parse(work_start), fm.parse(work_end));
                workArrayList.add(tmpWork);
                count++;
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        };

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("employeeList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String emp_name,emp_position, emp_salary, working_hour = null;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                emp_name = object.getString("emp_name");
                emp_position = object.getString("position");
                emp_salary = object.getString("salary");
                long work_time = 0;
                for(int i = 0; i<workArrayList.size();i++){
                    if(workArrayList.get(i).getEmp_name().equals(emp_name)){
                       long temp = workArrayList.get(i).getWork_end().getTime() - workArrayList.get(i).getWork_start().getTime();
                       work_time += temp;
                       working_hour = Integer.toString((int) (work_time/3600000));
                    }
                }
                Employee employee = new Employee(emp_name, emp_position , emp_salary,working_hour);
                employeeList.add(employee);
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent click_intent = new Intent(ManagementActivity.this,Employee_clicked.class);

                click_intent.putExtra("emp_name",employeeList.get(i).getEmp_name());
                click_intent.putExtra("position",employeeList.get(i).getEmp_position());
                click_intent.putExtra("salary",employeeList.get(i).getEmp_salary());
                click_intent.putExtra("working_hour",employeeList.get(i).getWorking_hour());
                ManagementActivity.this.startActivity(click_intent);
            }
        });

    }



}