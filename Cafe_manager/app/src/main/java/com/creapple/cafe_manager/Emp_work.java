package com.creapple.cafe_manager;

import java.util.Date;

public class Emp_work {
    String emp_name;
    String work_type;
    Date work_start;
    Date work_end;

    public Emp_work(String emp_name, String work_type, Date work_start, Date work_end) {
        this.emp_name = emp_name;
        this.work_type = work_type;
        this.work_start = work_start;
        this.work_end = work_end;

    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public Date getWork_start() {
        return work_start;
    }

    public void setWork_start(Date work_start) {
        this.work_start = work_start;
    }

    public Date getWork_end() {
        return work_end;
    }

    public void setWork_end(Date work_end) {
        this.work_end = work_end;
    }

}
