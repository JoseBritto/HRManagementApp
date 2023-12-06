package com.group55.project.ui;

import com.group55.project.*;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PayReportWindow extends Stage {
    
    public PayReportWindow(Coordinator coordinator, Employee employee) {
        this.setTitle("Pay Report");
        this.setWidth(800);
        this.setHeight(600);
        this.setResizable(false);
        this.centerOnScreen();
        
    
    
        var payRoll = coordinator.getPayrollManager().getPayroll(employee.getEmployeeID());
        if(payRoll == null) {
            payRoll = coordinator.getPayrollManager().addPayroll(new Payroll(employee.getEmployeeID(), 0));
        }
        
        var payReportPane = new VBox();
        
        payReportPane.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        var employeeNameLabel = new javafx.scene.control.Label("Employee Name: " + employee.getFirstName() + " " + employee.getLastName());
        
        // Show graphs of pay over time
        var payGraph = new javafx.scene.chart.LineChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        payGraph.setTitle("Pay Over Time");
        var paySeries = new javafx.scene.chart.XYChart.Series<String, Number>();
        paySeries.setName("Pay");
        for (var pay : payRoll.getPaychecks()) {
            paySeries.getData().add(new javafx.scene.chart.XYChart.Data<>(pay.getPayPeriodEndDate().toString(), pay.getNetPay()));
        }
        payGraph.getData().add(paySeries);
        
        // Show graphs of hours worked over time
        var hoursWorkedGraph = new javafx.scene.chart.LineChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        hoursWorkedGraph.setTitle("Hours Worked Over Time");
        var hoursWorkedSeries = new javafx.scene.chart.XYChart.Series<String, Number>();
        hoursWorkedSeries.setName("Hours Worked");
        for (var pay : payRoll.getPaychecks()) {
            hoursWorkedSeries.getData().add(new javafx.scene.chart.XYChart.Data<>(pay.getPayPeriodEndDate().toString(), pay.getHoursWorked()));
        }
        hoursWorkedGraph.getData().add(hoursWorkedSeries);
        
        // Show graphs of overtime hours worked over time
        var overtimeHoursWorkedGraph = new javafx.scene.chart.LineChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        overtimeHoursWorkedGraph.setTitle("Overtime Hours Worked Over Time");
        var overtimeHoursWorkedSeries = new javafx.scene.chart.XYChart.Series<String, Number>();
        overtimeHoursWorkedSeries.setName("Overtime Hours Worked");
        for (var pay : payRoll.getPaychecks()) {
            overtimeHoursWorkedSeries.getData().add(new javafx.scene.chart.XYChart.Data<>(pay.getPayPeriodEndDate().toString(), pay.getOvertimeHoursWorked()));
        }
        overtimeHoursWorkedGraph.getData().add(overtimeHoursWorkedSeries);
        
        // Show graphs of bonus over time
        var bonusGraph = new javafx.scene.chart.LineChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        bonusGraph.setTitle("Bonus Over Time");
        var bonusSeries = new javafx.scene.chart.XYChart.Series<String, Number>();
        bonusSeries.setName("Bonus");
        for (var pay : payRoll.getPaychecks()) {
            bonusSeries.getData().add(new javafx.scene.chart.XYChart.Data<>(pay.getPayPeriodEndDate().toString(), pay.getBonus()));
        }
        bonusGraph.getData().add(bonusSeries);
        
        // Show graphs of deductions over time
        var deductionsGraph = new javafx.scene.chart.LineChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        deductionsGraph.setTitle("Deductions Over Time");
        var deductionsSeries = new javafx.scene.chart.XYChart.Series<String, Number>();
        deductionsSeries.setName("Deductions");
        for (var pay : payRoll.getPaychecks()) {
            deductionsSeries.getData().add(new javafx.scene.chart.XYChart.Data<>(pay.getPayPeriodEndDate().toString(), pay.getDeductions()));
        }
        deductionsGraph.getData().add(deductionsSeries);

        payReportPane.getChildren().addAll(employeeNameLabel, payGraph, hoursWorkedGraph, overtimeHoursWorkedGraph, bonusGraph, deductionsGraph);
        
        var scrollPane = new javafx.scene.control.ScrollPane(payReportPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        this.setScene(new javafx.scene.Scene(scrollPane, 800, 600));
    }
    
    
    public PayReportWindow(Coordinator coordinator, Department department){
        this.setTitle("Pay Report");
        this.setWidth(800);
        this.setHeight(600);
        this.setResizable(false);
        this.centerOnScreen();

        
        var payReportDialogGrid = new VBox();

        var payRollManger = coordinator.getPayrollManager();
        var departmentManager = coordinator.getDepartmentManager();
        var employeeManager = coordinator.getEmployeeManager();
        var payReportPane = new VBox();

        payReportPane.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        var nameLabel = new javafx.scene.control.Label("Department Name: " + department.getDepartmentName());

        // Show graphs to compare salary of employees in the department
        var salaryGraph = new javafx.scene.chart.BarChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        salaryGraph.setTitle("Salary Comparison");
        var salarySeries = new javafx.scene.chart.XYChart.Series<String, Number>();
        salarySeries.setName("Salary");
        for (var employee : employeeManager.getEmployeesByDepartmentID(department.getDepartmentID())) {
            var payroll = payRollManger.getPayroll(employee.getEmployeeID());
            List<Paycheck> paychecks = new ArrayList<>();
            if(payroll != null) 
                paychecks= payRollManger.getPayroll(employee.getEmployeeID()).getPaychecks();
            if(!paychecks.isEmpty())
                salarySeries.getData().add(new XYChart.Data<>(employee.getFirstName(), paychecks.getLast().getNetPay()));
            else 
                salarySeries.getData().add(new XYChart.Data<>(employee.getFirstName(), 0));
                
        }
        salaryGraph.getData().add(salarySeries);
        
        // Show graphs to compare hours worked of employees in the department
        var hoursWorkedGraph = new javafx.scene.chart.BarChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        hoursWorkedGraph.setTitle("Hours Worked Comparison");
        var hoursWorkedSeries = new javafx.scene.chart.XYChart.Series<String, Number>();
        hoursWorkedSeries.setName("Hours Worked");
        for (var employee : employeeManager.getEmployeesByDepartmentID(department.getDepartmentID())) {
            var payroll = payRollManger.getPayroll(employee.getEmployeeID());
            List<Paycheck> paychecks = new ArrayList<>();
            if(payroll != null)
                paychecks= payRollManger.getPayroll(employee.getEmployeeID()).getPaychecks();
            if(!paychecks.isEmpty())
                hoursWorkedSeries.getData().add(new XYChart.Data<>(employee.getFirstName(), paychecks.getLast().getHoursWorked()));
            else 
                hoursWorkedSeries.getData().add(new XYChart.Data<>(employee.getFirstName(), 0));
        }
        hoursWorkedGraph.getData().add(hoursWorkedSeries);
        
        // Show graphs to compare overtime hours worked of employees in the department
        var overtimeHoursWorkedGraph = new javafx.scene.chart.BarChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        overtimeHoursWorkedGraph.setTitle("Overtime Hours Worked Comparison");
        var overtimeHoursWorkedSeries = new javafx.scene.chart.XYChart.Series<String, Number>();
        overtimeHoursWorkedSeries.setName("Overtime Hours Worked");
        for (var employee : employeeManager.getEmployeesByDepartmentID(department.getDepartmentID())) {
            var payroll = payRollManger.getPayroll(employee.getEmployeeID());
            List<Paycheck> paychecks = new ArrayList<>();
            if(payroll != null)
                paychecks= payRollManger.getPayroll(employee.getEmployeeID()).getPaychecks();
            if(!paychecks.isEmpty())
                overtimeHoursWorkedSeries.getData().add(new XYChart.Data<>(employee.getFirstName(), paychecks.getLast().getOvertimeHoursWorked()));
            else 
                overtimeHoursWorkedSeries.getData().add(new XYChart.Data<>(employee.getFirstName(), 0));
        }
        overtimeHoursWorkedGraph.getData().add(overtimeHoursWorkedSeries);
        
        // Add the labels and text fields to the grid
        payReportPane.getChildren().addAll(nameLabel, salaryGraph, hoursWorkedGraph, overtimeHoursWorkedGraph);
        
        var scrollPane = new javafx.scene.control.ScrollPane(payReportPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        this.setScene(new javafx.scene.Scene(scrollPane, 800, 600));
        
    }
    
        
}
