package com.group55.project;

public class Department {
    
        private int departmentID;
        private String departmentName;
    
        public Department(int departmentID, String departmentName) {
            this.departmentID = departmentID;
            this.departmentName = departmentName;
        }


    public int getDepartmentID() {
            return departmentID;
        }
    
        public String getDepartmentName() {
            return departmentName;
        }
        
        public void setDepartmentID(int departmentID) {
            this.departmentID = departmentID;
        }
    
        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }
        
    
}
