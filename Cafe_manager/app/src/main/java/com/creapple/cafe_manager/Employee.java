package com.creapple.cafe_manager;

public class Employee {
    String Emp_name;
    String Emp_position;
    String Emp_salary;
    String Working_hour;

    public Employee(String emp_name, String emp_position, String emp_salary, String working_hour) {
        Emp_name = emp_name;
        Emp_position = emp_position;
        Emp_salary = emp_salary;
        Working_hour = working_hour;
    }


    public String getEmp_name() {
        return Emp_name;
    }

    public void setEmp_name(String emp_name) {
        Emp_name = emp_name;
    }

    public String getEmp_salary() {
        return Emp_salary;
    }

    public void setEmp_salary(String emp_salary) {
        Emp_salary = emp_salary;
    }

    public String getWorking_hour() {
        return Working_hour;
    }

    public void setWorking_hour(String working_hour) {
        Working_hour = working_hour;
    }

    public String getEmp_position() {
        return Emp_position;
    }

    public void setEmp_position(String emp_position) {
        Emp_position = emp_position;
    }
}