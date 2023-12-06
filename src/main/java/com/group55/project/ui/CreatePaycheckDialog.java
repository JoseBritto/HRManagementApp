package com.group55.project.ui;
/*
    1. John Darrell Tamayo, 101452155
    2. Ashish Rajan Sherry, 101423478
    3. Jose Britto Saaji, 101416601
    4. Akorede Osunkoya, 101477407

 */
import com.group55.project.Coordinator;
import com.group55.project.Employee;
import com.group55.project.Paycheck;
import com.group55.project.Payroll;
import javafx.scene.control.Dialog;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
public class CreatePaycheckDialog extends Dialog<Paycheck> {
    public CreatePaycheckDialog(Coordinator coordinator, Employee employee) {
        this.setTitle("Create Paycheck");
        this.setHeaderText("Create Paycheck");
        
        var editPayrollDialogGrid = new GridPane();
        
        editPayrollDialogGrid.setHgap(10);
        editPayrollDialogGrid.setVgap(10);
        editPayrollDialogGrid.setPadding(new Insets(20, 150, 10, 10));
        
        var payroll = coordinator.getPayrollManager().getPayroll(employee.getEmployeeID());
        if(payroll == null) {
            payroll = coordinator.getPayrollManager().addPayroll(new Payroll(employee.getEmployeeID(), 0));
        }
        
        var employeeNameLabel = new javafx.scene.control.Label("Employee Name: " + employee.getFirstName() + " " + employee.getLastName());
        
        var hoursWorkedLabel = new javafx.scene.control.Label("Hours Worked:");
        var hoursWorkedTextField = new javafx.scene.control.TextField();
        
        var overtimeLabel = new javafx.scene.control.Label("Overtime (hrs):");
        var overtimeTextField = new javafx.scene.control.TextField();
        
        var hourlyRateLabel = new javafx.scene.control.Label("Hourly Rate:");
        var hourlyRateTextField = new javafx.scene.control.TextField();
        
        var overtimeRateLabel = new javafx.scene.control.Label("Overtime Rate:");
        var overtimeRateTextField = new javafx.scene.control.TextField();
        
        var bonusLabel = new javafx.scene.control.Label("Bonus:");
        var bonusTextField = new javafx.scene.control.TextField();
        
        var deductionsLabel = new javafx.scene.control.Label("Deductions:");
        var deductionsTextField = new javafx.scene.control.TextField();
        
        var payStartDateLabel = new javafx.scene.control.Label("Pay Start Date:");
        var payStartDateDatePicker = new javafx.scene.control.DatePicker();
        payStartDateDatePicker.setValue(payroll.getNextPayStartDate());
        payStartDateDatePicker.getEditor().setDisable(true);
        
        var payEndDateLabel = new javafx.scene.control.Label("Pay End Date:");
        var payEndDateDatePicker = new javafx.scene.control.DatePicker();
        payEndDateDatePicker.setValue(payroll.getSuggestedPayEndDate());
        payEndDateDatePicker.getEditor().setDisable(true);
        
        // Add the labels and text fields to the grid
        editPayrollDialogGrid.add(employeeNameLabel, 0, 0);
        editPayrollDialogGrid.add(hoursWorkedLabel, 0, 1);
        editPayrollDialogGrid.add(hoursWorkedTextField, 1, 1);
        editPayrollDialogGrid.add(overtimeLabel, 0, 2);
        editPayrollDialogGrid.add(overtimeTextField, 1, 2);
        editPayrollDialogGrid.add(hourlyRateLabel, 0, 3);
        editPayrollDialogGrid.add(hourlyRateTextField, 1, 3);
        editPayrollDialogGrid.add(overtimeRateLabel, 0, 4);
        editPayrollDialogGrid.add(overtimeRateTextField, 1, 4);
        editPayrollDialogGrid.add(bonusLabel, 0, 5);
        editPayrollDialogGrid.add(bonusTextField, 1, 5);
        editPayrollDialogGrid.add(deductionsLabel, 0, 6);
        editPayrollDialogGrid.add(deductionsTextField, 1, 6);
        editPayrollDialogGrid.add(payStartDateLabel, 0, 7);
        editPayrollDialogGrid.add(payStartDateDatePicker, 1, 7);
        editPayrollDialogGrid.add(payEndDateLabel, 0, 8);
        editPayrollDialogGrid.add(payEndDateDatePicker, 1, 8);
        
        this.getDialogPane().setContent(editPayrollDialogGrid);
        this.getDialogPane().getButtonTypes().addAll(javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);
        
        var okButton = (javafx.scene.control.Button) this.getDialogPane().lookupButton(javafx.scene.control.ButtonType.OK);
        
        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if(hoursWorkedTextField.getText().isEmpty() || overtimeTextField.getText().isEmpty() || hourlyRateTextField.getText().isEmpty() || overtimeRateTextField.getText().isEmpty() || bonusTextField.getText().isEmpty() || deductionsTextField.getText().isEmpty()) {
                event.consume();
                var alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("All fields must be filled in");
                alert.showAndWait();
                return;
            }
            
            // Hours worked must be an integer or a decimal number
            if(!isPositiveNumber(hoursWorkedTextField.getText().trim()) || !isPositiveNumber(overtimeTextField.getText().trim()) || !isPositiveNumber(hourlyRateTextField.getText().trim()) || !isPositiveNumber(overtimeRateTextField.getText().trim()) || !isPositiveNumber(bonusTextField.getText().trim()) || !isPositiveNumber(deductionsTextField.getText().trim())) {
                event.consume();
                var alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Hours worked, overtime, hourly rate, overtime rate, bonus, and deductions must be positive numbers");
                alert.showAndWait();
                return;
            }
            
            if(payStartDateDatePicker.getValue().isAfter(payEndDateDatePicker.getValue()) || payStartDateDatePicker.getValue().isEqual(payEndDateDatePicker.getValue())) {
                event.consume();
                var alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Pay start date cannot be after or the same day as pay end date");
                alert.showAndWait();
                return;
            }
        });
        
        this.setResultConverter(dialogButton -> {
            if(dialogButton == javafx.scene.control.ButtonType.OK) {
                var hoursWorked = Double.parseDouble(hoursWorkedTextField.getText().trim());
                var overtime = Double.parseDouble(overtimeTextField.getText().trim());
                var hourlyRate = Double.parseDouble(hourlyRateTextField.getText().trim());
                var overtimeRate = Double.parseDouble(overtimeRateTextField.getText().trim());
                var bonus = Double.parseDouble(bonusTextField.getText().trim());
                var deductions = Double.parseDouble(deductionsTextField.getText().trim());
                var payStartDate = payStartDateDatePicker.getValue();
                var payEndDate = payEndDateDatePicker.getValue();
                
                return new Paycheck(employee.getEmployeeID(), hourlyRate, hoursWorked, overtimeRate, overtime, 
                        bonus, deductions, payStartDate, payEndDate); 
            }
            return null;
        });
        
        
        
    }
    
    private static boolean isPositiveNumber(String str) {
        return str.matches("\\+?[0-9]+(\\.)?([0-9]+)?");
    }
}
