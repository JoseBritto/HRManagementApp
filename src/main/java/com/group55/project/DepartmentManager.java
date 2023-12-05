package com.group55.project;

import java.util.ArrayList;
import java.util.List;

public class DepartmentManager {

    private static int nextDepartmentID = 1;
    private List<Department> departments;

    public DepartmentManager() {
        this.departments = new ArrayList<>();
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public Department getDepartment(int departmentID) {
        for (Department department : departments) {
            if (department.getDepartmentID() == departmentID) {
                return department;
            }
        }
        return null;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(String departmentName) {
        Department department = new Department(nextDepartmentID, departmentName);
        departments.add(department);
        nextDepartmentID++;
    }
    
    public void addDepartment(Department department) {
        departments.add(department);
        if(department.getDepartmentID() >= nextDepartmentID) {
            nextDepartmentID = department.getDepartmentID() + 1;
        }
    }
    
    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    public void updateDepartment(int departmentID, String departmentName) {
        for (Department department : departments) {
            if (department.getDepartmentID() == departmentID) {
                department.setDepartmentName(departmentName);
            }
        }
    }

    public void printDepartments() {
        for (Department department : departments) {
            System.out.println(department.getDepartmentID() + " " + department.getDepartmentName());
        }
    }
}
