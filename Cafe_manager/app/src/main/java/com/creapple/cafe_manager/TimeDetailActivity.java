package com.creapple.cafe_manager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.TimedMetaData;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.creapple.cafe_manager.R;
import com.creapple.cafe_manager.TimeAll;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeDetailActivity extends AppCompatActivity {
    private ListView listView;
    private TimeAdapter adapter;
    private List<Employee> employeeList;
    private ArrayList<Emp_work> workArrayList;
    
    //https://recipes4dev.tistory.com/58 프레그먼트로 구현

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_detail);

        Intent intent = getIntent();
        String work_list = intent.getStringExtra("worklist");
        String emp_list = intent.getStringExtra("employeeList");

        Button btn_simply = findViewById(R.id.btn_simply);
        btn_simply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeDetailActivity.this, TimeAll.class);
                intent.putExtra("worklist", work_list);
                intent.putExtra("employeeList", emp_list);
                startActivity(intent);
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        employeeList = new ArrayList<Employee>();
        workArrayList = new ArrayList<Emp_work>();
        adapter = new TimeAdapter(getApplicationContext(), employeeList);
        listView.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("employeeList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String emp_name, emp_position = null;
            while (count < jsonArray.length()) {

                JSONObject object = jsonArray.getJSONObject(count);
                emp_name = object.getString("emp_name");
                emp_position = object.getString("position");


                Employee employee = new Employee(emp_name, emp_position);
                employeeList.add(employee);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TimeDetailActivity.this,Time_clicked.class);

                intent.putExtra("emp_name",employeeList.get(i).getEmp_name());
                intent.putExtra("position",employeeList.get(i).getEmp_position());
                intent.putExtra("worklist", work_list);
                intent.putExtra("employeeList", emp_list);
                startActivity(intent);
            }
        });
    }
}