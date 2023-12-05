package com.group55.project;

public class Payroll {
    
    private int employeeId;
    private double salary;
    private double bonus;
    private double deductions;
    
    

    public Payroll(int employeeId, double salary, double bonus, double deductions) {
        this.employeeId = employeeId;
        this.salary = salary;
        this.bonus = bonus;
        this.deductions = deductions;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public double getBonus() {
        return bonus;
    }

    public double getDeductions() {
        return deductions;
    }
    

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
    
    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }
    
    public double calculateNetSalary() {
        // Net salary calculation logic here
        return salary + bonus - deductions;
    }

}
