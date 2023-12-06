package com.group55.project.ui;

import com.group55.project.Coordinator;
import com.group55.project.Department;
import com.group55.project.Employee;
import com.group55.project.Payroll;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PayrollManagementContent extends VBox {

    private TableView<Department> departmentTable;
    private TableView<Employee> employeeTable;

    public PayrollManagementContent() {
        super(10);
        this.setPadding(new Insets(10));
        this.getStyleClass().add("content");
        
        // A search bar to search for employees
        var searchBox = new HBox(10);
        searchBox.getStyleClass().add("search-box");
        searchBox.getChildren().addAll(
                new Label("Search:"),
                new javafx.scene.control.TextField()
        );
        
        // Add a refresh button on the same line as the search box but on the right
        var refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> {
            refresh();
            // Clear the search box
            var searchField = (javafx.scene.control.TextField) searchBox.getChildren().get(1);
            searchField.setText("");
        });
        HBox.setMargin(refreshButton, new Insets(0, 0, 0, 10));
        searchBox.getChildren().add(refreshButton);
        
        // A table to display employees
        employeeTable = new TableView<Employee>();
        employeeTable.setPrefHeight(400);
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        
        // Create columns for the table
        var employeeIDColumn = new TableColumn<Employee, Integer>("Employee ID");
        employeeIDColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("employeeID"));
        
        var employeeNameColumn = new TableColumn<Employee, String>("Employee Name");
        employeeNameColumn.setCellValueFactory(cellData -> {
            var employee = cellData.getValue();
            return new SimpleStringProperty(employee.getFirstName() + " " + employee.getLastName());
        });
        
        var departmentNameColumn = new TableColumn<Employee, String>("Department Name");
        departmentNameColumn.setCellValueFactory(cellData -> {
            var employee = cellData.getValue();
            var departmentManager = Coordinator.getInstance().getDepartmentManager();
            var departmentName = departmentManager.getDepartment(employee.getDepartmentId()).getDepartmentName();
            return new SimpleStringProperty(departmentName);
        });
        
        var lastPayDateColumn = new TableColumn<Employee, String>("Last Pay Date");
        lastPayDateColumn.setCellValueFactory(cellData -> {
            var employee = cellData.getValue();
            var payManager = Coordinator.getInstance().getPayrollManager();
            var lastPayDate = payManager.getLastPayDate(employee.getEmployeeID());
            if(lastPayDate == null) {
                return new SimpleStringProperty("N/A");
            }
            return new SimpleStringProperty(lastPayDate.toString());
        });
        
        var lastPayAmountColumn = new TableColumn<Employee, String>("Last Pay Amount");
        lastPayAmountColumn.setCellValueFactory(cellData -> {
            var employee = cellData.getValue();
            var payManager = Coordinator.getInstance().getPayrollManager();
            var lastPayAmount = payManager.getLatestPay(employee.getEmployeeID());
            if(lastPayAmount == 0) {
                return new SimpleStringProperty("N/A");
            }
            return new SimpleStringProperty(lastPayAmount + "");
        });
        
        // Add columns to the table
        employeeTable.getColumns().addAll(employeeIDColumn, employeeNameColumn, 
                departmentNameColumn, lastPayDateColumn, lastPayAmountColumn);
        
        
        // Add double click listener to the table rows
        employeeTable.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Employee rowData = row.getItem();
                    System.out.println("Double click on: " + rowData);
                    /*createPaycheckAction(null);*/
                    new PayReportWindow(Coordinator.getInstance(), rowData).showAndWait();
                }
            });
            return row;
        });

        // Show edit dialog when user presses enter on a row
        employeeTable.setOnKeyPressed(event -> {
            if(event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                createPaycheckAction(null);
            }
        });
        
        // Add a listener to the search box to filter the table
        TextField searchField = (TextField) searchBox.getChildren().get(1);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            refresh();
            employeeTable.getItems()
                    .removeIf(employee -> !employee.getFirstName().toLowerCase().contains(newValue.toLowerCase())
                            && !employee.getLastName().toLowerCase().contains(newValue.toLowerCase()));
        });
        
        // Add context menu to create paychecks
        var contextMenu = new ContextMenu();
        var createPaycheckMenuItem = new MenuItem("Create Paycheck");
        var showPayReportMenuItem = new MenuItem("Show Pay Report");
        createPaycheckMenuItem.setOnAction(event -> {
            createPaycheckAction(null);
        });
        showPayReportMenuItem.setOnAction(event -> {
            var employee = employeeTable.getSelectionModel().getSelectedItem();
            if(employee == null) {
                return;
            }
            new PayReportWindow(Coordinator.getInstance(), employee).showAndWait();
        });
        
        contextMenu.getItems().add(createPaycheckMenuItem);
        contextMenu.getItems().add(showPayReportMenuItem);
        employeeTable.setContextMenu(contextMenu);
        
        // Add a departments table to the content
        departmentTable = new TableView<Department>();
        departmentTable.setPrefHeight(400);
        departmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        
        // Create columns for the table
        var departmentIDColumn = new TableColumn<Department, Integer>("Department ID");
        departmentIDColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("departmentID"));
        
        var departmentNameColumn2 = new TableColumn<Department, String>("Department Name");
        departmentNameColumn2.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("departmentName"));
        
        // Add columns to the table
        departmentTable.getColumns().addAll(departmentIDColumn, departmentNameColumn2);
        
        // Add double click listener to the table rows
        departmentTable.setRowFactory(tv -> {
            TableRow<Department> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Department rowData = row.getItem();
                    System.out.println("Double click on: " + rowData);
                    new PayReportWindow(Coordinator.getInstance(), rowData).showAndWait();
                }
            });
            return row;
        });
        
        var contextMenu2 = new ContextMenu();
        var showPayReportMenuItem2 = new MenuItem("Show Pay Report");
        showPayReportMenuItem2.setOnAction(event -> {
            var department = departmentTable.getSelectionModel().getSelectedItem();
            if(department == null) {
                return;
            }
            new PayReportWindow(Coordinator.getInstance(), department).showAndWait();
        });
        
        contextMenu2.getItems().add(showPayReportMenuItem2);
        departmentTable.setContextMenu(contextMenu2);
        
        
        this.getChildren().addAll(searchBox, employeeTable, departmentTable);
        refresh();
    }

    private void createPaycheckAction(Employee employee) {
        if(employee == null)
            employee = employeeTable.getSelectionModel().getSelectedItem();
        if(employee == null) {
            return;
        }
        var dialog = new CreatePaycheckDialog(Coordinator.getInstance(), employee);
        var paycheck = dialog.showAndWait();
        if(paycheck.isPresent()) {
            // show a confirmation dialog
            var alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirmation");
            alert.setContentText("Are you sure you want to create this paycheck?\n" +
                    "Employee: " + employee.getFirstName() + " " + employee.getLastName() + "\n" +
                            "Pay Period: " + paycheck.get().getPayPeriodStartDate() + "-" + paycheck.get().getPayPeriodEndDate() + "\n" +
                            "Pay Amount: " + paycheck.get().getNetPay());
            var result = alert.showAndWait();
            if(result.isEmpty() || result.get() != ButtonType.OK) {
                return;
            }
            var payrollManager = Coordinator.getInstance().getPayrollManager();
            var payroll = payrollManager.getPayroll(employee.getEmployeeID());
            if(payroll == null) {
                payroll = payrollManager.addPayroll(new Payroll(employee.getEmployeeID(), paycheck.get().getHourlyWage()));
            }
            payroll.addPaycheck(paycheck.get());
        }
        refresh();
    }

    private void refresh() {
        employeeTable.getItems().clear();
        employeeTable.getItems().addAll(Coordinator.getInstance().getEmployeeManager().getAllEmployees());
        departmentTable.getItems().clear();
        departmentTable.getItems().addAll(Coordinator.getInstance().getDepartmentManager().getDepartments());
    }
}
