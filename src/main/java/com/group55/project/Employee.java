package com.group55.project;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
/*
    1. John Darrell Tamayo, 101452155
    2. Ashish Rajan Sherry, 101423478
    3. Jose Britto Saaji, 101416601
    4. Akorede Osunkoya, 101477407

 */
public class Employee implements Serializable {

    private int EmployeeID;
    private String firstName;
    private String lastName;
    private int departmentId;
    private String position;
    private LocalDate dateOfHire;

    public Employee(int employeeID, String firstName, String lastName, int departmentId, String position, LocalDate dateOfHire) {
        EmployeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.position = position;
        this.dateOfHire = dateOfHire;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartment(Department department)
    {
        this.departmentId = department.getDepartmentID();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(LocalDate dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "EmployeeID=" + EmployeeID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", Department='" + departmentId + '\'' +
                ", position='" + position + '\'' +
                ", dateOfHire='" + dateOfHire + '\'' +
                '}';
    }
    
    public String toCSV() {
        return EmployeeID + "," + firstName + "," + lastName + "," + departmentId + "," + position + "," + dateOfHire;
    }
    
    /*public static Employee fromCSV(String csv) {
        String[] values = csv.split(",");
        return new Employee(Integer.parseInt(values[0]), values[1], values[2], Department.valueOf(values[3]), values[4], values[5]);
    }*/
}
