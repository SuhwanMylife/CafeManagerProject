package com.creapple.cafe_manager;

import java.util.Date;

public class Emp_work {
    String emp_name;
    Date work_start;

    public Emp_work(String emp_name, Date work_start, Date work_end) {
        this.emp_name = emp_name;
        this.work_start = work_start;
        this.work_end = work_end;
    }

    Date work_end;


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
