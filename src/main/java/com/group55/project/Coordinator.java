package com.group55.project;

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
    
    
}
