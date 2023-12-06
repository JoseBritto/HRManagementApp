package com.group55.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Payroll {
    
    private int employeeId;
    private double hourlyWage;    
    private List<Paycheck> paychecks;

    
    public Payroll(int employeeId, double hourlyWage, List<Paycheck> paychecks) {
        this.employeeId = employeeId;
        this.hourlyWage = hourlyWage;
        this.paychecks = paychecks;
    }
    public Payroll(int employeeId, double hourlyWage) {
        this(employeeId, hourlyWage, new ArrayList<>());
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }
    
    public List<Paycheck> getPaychecks() {
        return paychecks;
    }
    
    public void setPaychecks(List<Paycheck> paychecks) {
        this.paychecks = paychecks;
    }
    
    public void addPaycheck(Paycheck paycheck) {
        paychecks.add(paycheck);
    }
    
    public void removePaycheck(Paycheck paycheck) {
        paychecks.remove(paycheck);
    }
    
    public LocalDate getLatestPayPeriodEndDate() {
        LocalDate latestPayPeriodEndDate = LocalDate.MIN;
        for (Paycheck paycheck : paychecks) {
            if (paycheck.getPayPeriodEndDate().isAfter(latestPayPeriodEndDate)) {
                latestPayPeriodEndDate = paycheck.getPayPeriodEndDate();
            }
        }
        return latestPayPeriodEndDate;
    }
    
    public LocalDate getNextPayStartDate() {
        LocalDate latestPayPeriodEndDate = getLatestPayPeriodEndDate();
        if (latestPayPeriodEndDate == LocalDate.MIN) {
            return LocalDate.now();
        }
        return latestPayPeriodEndDate.plusDays(1);
    }
    
    public LocalDate getNextPayEndDate() {
        return getNextPayStartDate().plusDays(13);
    }
}
