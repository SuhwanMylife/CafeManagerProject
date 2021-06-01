package com.creapple.cafe_manager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {
    private ListView listView;
    private EmployeeListAdapter adapter;
    private List<Employee> employeeList;
    private Spinner monthSpinner;
    private ArrayList<Emp_work> workArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView);
        employeeList = new ArrayList<Employee>();
        workArrayList = new ArrayList<Emp_work>();
        adapter = new EmployeeListAdapter(getApplicationContext(), employeeList);
        listView.setAdapter(adapter);
        String userStore = intent.getStringExtra("userStore");

        long now = System.currentTimeMillis();
        Date now_date = new Date(now);

        monthSpinner = (Spinner)findViewById(R.id.month_spinner);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        int now_month = Integer.parseInt(simpleDateFormat.format(now_date));
        monthSpinner.setSelection(now_month - 1);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                try {
                    employeeList.clear();
                    workArrayList.clear();

                    JSONObject jsonObject = new JSONObject(intent.getStringExtra("worklist"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");

                    int count = 0;
                    String employee_name, work_type, work_start, work_end, store_name = null;
                    SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    while (count < jsonArray.length()) {
                            JSONObject object = jsonArray.getJSONObject(count);

                        if( Integer.parseInt(simpleDateFormat.format(fm.parse(object.getString("work_end")))) == (position + 1)){
                            store_name = object.getString("store_name");
                            if(store_name.equals(userStore)){
                                employee_name = object.getString("employee_name");
                                work_type = object.getString("work_type");
                                work_start = object.getString("work_start");
                                work_end = object.getString("work_end");
                                Emp_work tmpWork = new Emp_work(employee_name, work_type, fm.parse(work_start), fm.parse(work_end));
                                workArrayList.add(tmpWork);
                            }
                        }
                        count++;
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject(intent.getStringExtra("employeeList"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    String store_name, emp_name, emp_position, emp_salary, working_hour = null;
                    while (count < jsonArray.length()) {

                        JSONObject object = jsonArray.getJSONObject(count);
                        store_name = object.getString("store_name");
                        if(store_name.equals(userStore)){
                            emp_name = object.getString("emp_name");
                            emp_position = object.getString("position");
                            emp_salary = object.getString("salary");
                            double work_normal = 0, work_over = 0, work_night = 0;
                            for (int i = 0; i < workArrayList.size(); i++) {
                                if (workArrayList.get(i).getEmp_name().equals(emp_name)) {
                                    if (workArrayList.get(i).getWork_type().equals("기본")) {
                                        double temp = workArrayList.get(i).getWork_end().getTime() - workArrayList.get(i).getWork_start().getTime();
                                        work_normal += temp/ 3600000;
                                    } else if (workArrayList.get(i).getWork_type().equals("연장")) {
                                        double temp = workArrayList.get(i).getWork_end().getTime() - workArrayList.get(i).getWork_start().getTime();
                                        work_over += temp/ 3600000;
                                    } else if (workArrayList.get(i).getWork_type().equals("야간")) {
                                        double temp = workArrayList.get(i).getWork_end().getTime() - workArrayList.get(i).getWork_start().getTime();
                                        work_night += temp/ 3600000;
                                    }
                                }
                            }
                            working_hour = Double.toString((work_normal + work_over + work_night));
                            double total_salary = (work_normal + (work_over * 1.5) + (work_night * 1.3)) * Double.parseDouble(emp_salary);

                            Employee employee = new Employee(emp_name, emp_position, emp_salary, working_hour, total_salary, work_normal, work_over, work_night);
                            employeeList.add(employee);
                        }
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent click_intent = new Intent(ManagementActivity.this,Employee_clicked.class);

                click_intent.putExtra("emp_name",employeeList.get(i).getEmp_name());
                click_intent.putExtra("position",employeeList.get(i).getEmp_position());
                click_intent.putExtra("salary",employeeList.get(i).getEmp_salary());
                click_intent.putExtra("working_hour",employeeList.get(i).getWorking_hour());
                click_intent.putExtra("total_salary",employeeList.get(i).getTotal_salary());
                click_intent.putExtra("work_normal",employeeList.get(i).getNormal());
                click_intent.putExtra("work_over",employeeList.get(i).getOver());
                click_intent.putExtra("work_night",employeeList.get(i).getNight());

                ManagementActivity.this.startActivity(click_intent);
            }
        });

    }


}