package com.mhl.service;

import com.mhl.dao.EmployeeDAO;
import com.mhl.domain.Employee;

public class EmployeeService {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    //根据empId 和 pwd 返回一个 employee 对象 查询不到返回 null
    public Employee getEmployeeByIdAndPwd(String empId, String pwd){
        return employeeDAO.querySingle("select * from employee where empId=? and pwd=md5(?)", Employee.class, empId, pwd);
    }


}
