package com.group55.project;

import java.util.ArrayList;
import java.util.List;

public class PayrollManager {
    private List<Payroll> payrolls;
    
    public PayrollManager(List<Payroll> payrolls) {
        this.payrolls = payrolls;
    }
    
    public PayrollManager() {
        this.payrolls = new ArrayList<>();
    }
    
    public List<Payroll> getPayrolls() {
        return payrolls;
    }
    
    public Payroll getPayroll(int employeeId) {
        for (Payroll payroll : payrolls) {
            if (payroll.getEmployeeId() == employeeId) {
                return payroll;
            }
        }
        return null;
    }
    
    public void setPayrolls(List<Payroll> payrolls) {
        this.payrolls = payrolls;
    }
    
    public void addPayroll(Payroll payroll) {
        payrolls.add(payroll);
    }
    
    public void removePayroll(Payroll payroll) {
        payrolls.remove(payroll);
    }
    
    public void updatePayroll(int employeeId, double salary, double bonuses, double deductions) {
        for (Payroll payroll : payrolls) {
            if (payroll.getEmployeeId() == employeeId) {
                payroll.setSalary(salary);
                payroll.setBonus(bonuses);
                payroll.setDeductions(deductions);
            }
        }
    }
    
    
}
