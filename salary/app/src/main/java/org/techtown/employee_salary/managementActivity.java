package org.techtown.employee_salary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class managementActivity extends AppCompatActivity {

    private ListView listView;
    private EmployeeListAdapter adapter;
    private List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView);
        employeeList = new ArrayList<Employee>();
        adapter = new EmployeeListAdapter(getApplicationContext(),employeeList);
        listView.setAdapter(adapter);


        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("employeeList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String emp_name,emp_position, emp_salary, working_hour;
            int temp;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                emp_name = object.getString("emp_name");
                emp_position = object.getString("position");
                emp_salary = object.getString("salary");
                working_hour = object.getString("working_hour");
                Employee employee = new Employee(emp_name, emp_position , emp_salary, working_hour);
                employeeList.add(employee);
                count++;

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent click_intent = new Intent(managementActivity.this,EmployeeClicked.class);

                click_intent.putExtra("emp_name",employeeList.get(i).getEmp_name());
                click_intent.putExtra("position",employeeList.get(i).getEmp_position());
                click_intent.putExtra("salary",employeeList.get(i).getEmp_salary());
                click_intent.putExtra("working_hour",employeeList.get(i).getWorking_hour());
                managementActivity.this.startActivity(click_intent);
            }
        });

    }
}