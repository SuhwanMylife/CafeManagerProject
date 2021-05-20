package com.creapple.cafe_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class EmployeeListAdapter extends BaseAdapter {
    private Context context;
    private List<Employee> employeeList;

    public EmployeeListAdapter(Context context, List<Employee> employeeList){
        this.context = context;
        this.employeeList = employeeList;
    }

    @Override
    public int getCount() {
        return employeeList.size();
    }

    @Override
    public Object getItem(int i) {
        return employeeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.employee, null);
        TextView emp_name = (TextView) v.findViewById(R.id.emp_name);
        TextView emp_salary = (TextView) v.findViewById(R.id.emp_info);

        DecimalFormat myFormatter = new DecimalFormat("###,###");

        emp_name.setText(employeeList.get(i).getEmp_name());


        emp_salary.setText(myFormatter.format(employeeList.get(i).getTotal_salary()) + "원");

        v.setTag(employeeList.get(i).getEmp_name());
        return v;
    }
}
