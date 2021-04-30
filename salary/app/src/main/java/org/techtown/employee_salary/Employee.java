package org.techtown.employee_salary;

public class Employee {

    String Emp_name;
    String Emp_salary;

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

    public Employee(String emp_name, String emp_salary) {
        Emp_name = emp_name;
        Emp_salary = emp_salary;
    }



}
