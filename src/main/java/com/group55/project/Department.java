package com.group55.project;
/*
    1. John Darrell Tamayo, 101452155
    2. Ashish Rajan Sherry, 101423478
    3. Jose Britto Saaji, 101416601
    4. Akorede Osunkoya, 101477407

 */
public class Department implements java.io.Serializable {
    
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
