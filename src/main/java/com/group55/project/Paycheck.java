package com.group55.project;

import java.io.Serializable;
import java.time.LocalDate;
/*
    1. John Darrell Tamayo, 101452155
    2. Ashish Rajan Sherry, 101423478
    3. Jose Britto Saaji, 101416601
    4. Akorede Osunkoya, 101477407

 */
public class Paycheck implements Serializable {
    
    private int employeeId;
    private double hourlyWage;
    private double overtimeHourlyWage;
    
    private double basePay;
    private double overtimePay;
    private double bonus;
    private double deductions;
    
    private double hoursWorked;
    private double overtimeHoursWorked;
    
    private LocalDate payPeriodStartDate;
    private LocalDate payPeriodEndDate;
    
    public Paycheck(int employeeId, double hourlyWage, double hoursWorked, double overtimeHourlyWage,double overtimeHoursWorked, double bonus, double deductions, LocalDate payPeriodStartDate, LocalDate payPeriodEndDate) {
        this.employeeId = employeeId;
        this.hourlyWage = hourlyWage;
        this.overtimeHourlyWage = overtimeHourlyWage;
        this.hoursWorked = hoursWorked;
        this.overtimeHoursWorked = overtimeHoursWorked;
        this.bonus = bonus;
        this.deductions = deductions;
        this.payPeriodStartDate = payPeriodStartDate;
        this.payPeriodEndDate = payPeriodEndDate;
        calculateBasePay();
        calculateOvertimePay();
    }
    
    public void calculateBasePay() {
        basePay = hourlyWage * hoursWorked;
    }

    public void calculateOvertimePay() {
        overtimePay = overtimeHourlyWage * overtimeHoursWorked;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public double getHourlyWage() {
        return hourlyWage;
    }
    
    public double getOvertimeHourlyWage() {
        return overtimeHourlyWage;
    }
    
    public double getBasePay() {
        return basePay;
    }
    
    public double getOvertimePay() {
        return overtimePay;
    }
    
    public double getBonus() {
        return bonus;
    }
    
    public double getDeductions() {
        return deductions;
    }
    
    public double getHoursWorked() {
        return hoursWorked;
    }
    
    public double getOvertimeHoursWorked() {
        return overtimeHoursWorked;
    }
    
    public LocalDate getPayPeriodStartDate() {
        return payPeriodStartDate;
    }
    
    public LocalDate getPayPeriodEndDate() {
        return payPeriodEndDate;
    }
    
    public double getNetPay() {
        // Net salary calculation logic here
        return basePay + overtimePay + bonus - deductions;
    }   
    
}
