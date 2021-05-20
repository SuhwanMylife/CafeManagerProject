package com.creapple.cafe_manager;

public class Employee {
    String Emp_name;
    String Emp_position;
    String Emp_salary;
    String Working_hour;
    double Total_salary;
    double normal, over, night;

    public Employee(String emp_name, String emp_position, String emp_salary, String working_hour, double total_salary, double normal, double over, double night) {
        Emp_name = emp_name;
        Emp_position = emp_position;
        Emp_salary = emp_salary;
        Working_hour = working_hour;
        Total_salary = total_salary;
        this.normal = normal;
        this.over = over;
        this.night = night;
    }

    public Employee(String emp_name, String emp_position) {
        Emp_name = emp_name;
        Emp_position = emp_position;
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
    public double getTotal_salary() {
        return Total_salary;
    }

    public void setTotal_salary(double total_salary) {
        Total_salary = total_salary;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }

    public double getOver() {
        return over;
    }

    public void setOver(double over) {
        this.over = over;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }
}