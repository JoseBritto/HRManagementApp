package com.group55.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
    1. John Darrell Tamayo, 101452155
    2. Ashish Rajan Sherry, 101423478
    3. Jose Britto Saaji, 101416601
    4. Akorede Osunkoya, 101477407

 */
public class EmployeeManager {
    
    private final List<Employee> employees;
    private static int nextEmployeeID = 1;
    
    // Constructor
    public EmployeeManager() {
        this.employees = new ArrayList<>();
    }
    
    // Methods
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    
    public Employee addEmployee(String firstName, String lastName, Department department, String position, LocalDate dateOfHire) {
        Employee employee = new Employee(nextEmployeeID, firstName, lastName, department.getDepartmentID(), position, dateOfHire);
        addEmployee(employee);
        nextEmployeeID++;
        return employee;
    }
    
    public Employee getEmployeeByID(int employeeID) {
        for (Employee employee : employees) {
            if (employee.getEmployeeID() == employeeID) {
                return employee;
            }
        }
        return null; // Employee not found
    }
    
    public boolean removeEmployeeByID(int employeeID) {
        Employee employee = getEmployeeByID(employeeID);
        if (employee != null) {
            employees.remove(employee);
            return true;
        }
        return false;
    }
    
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
    
    public int getNextEmployeeID() {
        return nextEmployeeID;
    }
    
    public void setNextEmployeeID(int nextEmployeeID) {
        this.nextEmployeeID = nextEmployeeID;
    }
    
    public void incrementNextEmployeeID() {
        nextEmployeeID++;
    }
    
    public void decrementNextEmployeeID() {
        nextEmployeeID--;
    }
    
    public void resetNextEmployeeID() {
        nextEmployeeID = 1;
    }
    
    public void resetEmployees() {
        employees.clear();
    }


    public void removeEmployeesByDepartmentID(int departmentID) {
        var iterator = employees.iterator();
        while (iterator.hasNext()) {
            var employee = iterator.next();
            if (employee.getDepartmentId() == departmentID) {
                iterator.remove();
            }
        }
    }

    public boolean save() {
        return FileUtility.saveToFile("employees", employees);
    }
    
    public boolean load() {
        var loadedEmployees = (List<Employee>) FileUtility.loadFromFile("employees");
        if(loadedEmployees == null) {
            return false;
        }
        loadedEmployees.forEach(employee -> {
            if(employee.getEmployeeID() >= nextEmployeeID) {
                nextEmployeeID = employee.getEmployeeID() + 1;
            }
        });
        employees.clear();
        employees.addAll(loadedEmployees);
        return true;
    }
    
    public List<Employee> getEmployeesByDepartmentID(int departmentID) {
        var employeesInDepartment = new ArrayList<Employee>();
        for (Employee employee : employees) {
            if (employee.getDepartmentId() == departmentID) {
                employeesInDepartment.add(employee);
            }
        }
        return employeesInDepartment;
    }
}
