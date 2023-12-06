package com.group55.project;

import java.util.zip.Adler32;

public class Coordinator {
    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager;
    
    private PayrollManager payrollManager;
    
    private static Coordinator instance = null;
    public static Coordinator getInstance(){
        if(instance == null){
            instance = new Coordinator(new EmployeeManager(), new PayrollManager(), new DepartmentManager());
        }
        return instance;
    }

    // Constructor
    public Coordinator(EmployeeManager employeeManager, PayrollManager manager, DepartmentManager departmentManager) {
        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;
        this.payrollManager = manager;
    }
    
    public EmployeeManager getEmployeeManager() {
        return employeeManager;
    }
    
    public DepartmentManager getDepartmentManager() {
        return departmentManager;
    }
    
    
    public PayrollManager getPayrollManager() {
        return payrollManager;
    }
    
    public boolean save() {
        boolean r1= employeeManager.save();
        boolean r2 = departmentManager.save();
        boolean r3 = payrollManager.save();
        return r1 && r2 && r3;
        
    }
    
    public boolean load() {
       boolean r1 = employeeManager.load();
       boolean r2 = departmentManager.load();
       boolean r3 =  payrollManager.load();
       return r1 && r2 && r3;
    }
}
