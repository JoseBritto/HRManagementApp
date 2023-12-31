package com.group55.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/*
    1. John Darrell Tamayo, 101452155
    2. Ashish Rajan Sherry, 101423478
    3. Jose Britto Saaji, 101416601
    4. Akorede Osunkoya, 101477407

 */
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
    
    public Payroll addPayroll(Payroll payroll) {
        if(getPayroll(payroll.getEmployeeId()) == null) {
            payrolls.add(payroll);
            return payroll;
        }
        return null;
    }
    
    public void removePayroll(Payroll payroll) {
        payrolls.remove(payroll);
    }
    
    public void updatePayroll(int employeeId, double hourlyWage) {
        for (Payroll payroll : payrolls) {
            if (payroll.getEmployeeId() == employeeId) {
                payroll.setHourlyWage(hourlyWage);
            }
        }
    }
    
    public LocalDate getLastPayDate(int employeeId) {
        Payroll payroll = getPayroll(employeeId);
        if (payroll == null) {
            return null;
        }
        List<Paycheck> paychecks = payroll.getPaychecks();
        if (paychecks.size() == 0) {
            return null;
        }
        return paychecks.get(paychecks.size() - 1).getPayPeriodEndDate();
    }
    
    public double getLatestPay(int employeeId) {
        Payroll payroll = getPayroll(employeeId);
        if (payroll == null) {
            return 0;
        }
        List<Paycheck> paychecks = payroll.getPaychecks();
        if (paychecks.size() == 0) {
            return 0;
        }
        Paycheck paycheck = paychecks.get(paychecks.size() - 1);
        return paycheck.getBasePay() + paycheck.getOvertimePay() + paycheck.getBonus() - paycheck.getDeductions();
    }


    public boolean load() {
        var loadedPayrolls = (List<Payroll>) FileUtility.loadFromFile("payrolls");
        if(loadedPayrolls == null) {
            return false;
        }
        payrolls.clear();
        payrolls.addAll(loadedPayrolls);
        return true;
    }
    
    public boolean save() {
        return FileUtility.saveToFile("payrolls", payrolls);
    }
}
