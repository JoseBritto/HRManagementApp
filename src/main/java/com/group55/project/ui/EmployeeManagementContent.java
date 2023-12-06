package com.group55.project.ui;
/*
    1. John Darrell Tamayo, 101452155
    2. Ashish Rajan Sherry, 101423478
    3. Jose Britto Saaji, 101416601
    4. Akorede Osunkoya, 101477407

 */
import com.group55.project.*;
import com.group55.project.ui.AddAndEditEmployeeDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeManagementContent extends VBox {
    
    private TableView<Employee> employeeTable;
    public EmployeeManagementContent() {
        super(10);
        
        this.setPadding(new Insets(10));
        this.getStyleClass().add("content");

        // A search bar to search for employees
        HBox searchBox = new HBox(10);
        searchBox.getStyleClass().add("search-box");
        searchBox.getChildren().addAll(
                new Label("Search:"),
                new TextField()
        );
        
        // Add a refresh button on the same line as the search box but on the right
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> {
            refresh();
            // Clear the search box
            TextField searchField = (TextField) searchBox.getChildren().get(1);
            searchField.setText("");
        });
        HBox.setMargin(refreshButton, new Insets(0, 0, 0, 10));
        searchBox.getChildren().add(refreshButton);


        // A table to display employees
        employeeTable = new TableView<>();
        employeeTable.setPrefHeight(400);
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Create columns for the table
        TableColumn<Employee, Integer> employeeIDColumn = new TableColumn<>("Employee ID");
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        employeeIDColumn.setPrefWidth(100);

        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setPrefWidth(100);

        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setPrefWidth(100);

        TableColumn<Employee, String> departmentColumn = new TableColumn<>("Department");
        // Get department name from department ID using department manager
        departmentColumn.setCellValueFactory(data -> {
            DepartmentManager departmentManager = Coordinator.getInstance().getDepartmentManager();
            Department department = departmentManager.getDepartment(data.getValue().getDepartmentId());
            return new SimpleStringProperty(department.getDepartmentName());
        });

        departmentColumn.setPrefWidth(100);

        TableColumn<Employee, String> positionColumn = new TableColumn<>("Position");
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        positionColumn.setPrefWidth(100);

        TableColumn<Employee, LocalDateTime> dateOfHireColumn = new TableColumn<>("Date of Hire");
        dateOfHireColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfHire"));
        dateOfHireColumn.setPrefWidth(100);

        employeeTable.getColumns().addAll(employeeIDColumn, firstNameColumn, lastNameColumn, departmentColumn, positionColumn, dateOfHireColumn);

        // Add double click listener to the table rows
        employeeTable.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Employee rowData = row.getItem();
                    System.out.println("Double click on: " + rowData);
                    editEmployeeAction();
                }
            });
            return row;
        });

        // Show edit dialog when user presses enter on a row
        employeeTable.setOnKeyPressed(event -> {
            if(event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                editEmployeeAction();
            }
        });
        
        DepartmentManager departmentManager = Coordinator.getInstance().getDepartmentManager();
        EmployeeManager employeeManager = Coordinator.getInstance().getEmployeeManager();
       
        // Add the employees to the table        
        employeeTable.getItems().addAll(employeeManager.getAllEmployees());

        // Add a listener to the search box to filter the table
        TextField searchField = (TextField) searchBox.getChildren().get(1);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            refresh();
            employeeTable.getItems().removeIf(employee -> !employee.getFirstName().toLowerCase().contains(newValue.toLowerCase()) &&
                    !employee.getLastName().toLowerCase().contains(newValue.toLowerCase()) &&
                    !employee.getPosition().toLowerCase().contains(newValue.toLowerCase()) && 
                    !departmentManager.getDepartment(employee.getDepartmentId()).getDepartmentName().toLowerCase().contains(newValue.toLowerCase()));
        });

        // Add an add employee button
        Button addEmployeeButton = new Button("Add New Employee");
        addEmployeeButton.setOnAction(e -> {
            System.out.println("Add employee");
            var dialog = new AddAndEditEmployeeDialog(Coordinator.getInstance());

            var result = dialog.showAndWait();
            result.ifPresent(employee -> {
                employeeTable.getItems().removeAll(employeeTable.getItems());
                employeeTable.getItems().addAll(employeeManager.getAllEmployees());
            });
        });
        
        // Add the search bar and table to the content
        this.getChildren().addAll(
                searchBox,
                employeeTable,
                addEmployeeButton
        );

        // Add a context menu to the table
        ContextMenu contextMenu = createContextMenu(employeeManager);
        employeeTable.setContextMenu(contextMenu);
    }

    private ContextMenu createContextMenu(EmployeeManager employeeManager) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(e -> {
            editEmployeeAction();
        });
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(e -> {
            deleteEmployeeAction(employeeManager);
        });
        contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
        return contextMenu;
    }

    private void deleteEmployeeAction(EmployeeManager employeeManager) {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            // Confirm delete 
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Employee");
            alert.setHeaderText("Delete Employee");
            alert.setContentText("Are you sure you want to remove " + selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName() + " from the system?");
            
            
            alert.getButtonTypes().setAll(ButtonType.NO, ButtonType.YES);
            
            // Make no the default button
            Button noButton = (Button) alert.getDialogPane().lookupButton(ButtonType.NO);
            Button yesButton = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
            noButton.setDefaultButton(true);
            yesButton.setDefaultButton(false);
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    System.out.println("Delete employee: " + selectedEmployee);
                    employeeManager.removeEmployeeByID(selectedEmployee.getEmployeeID());
                    refresh();
                }
            });
        }
    }

    private void editEmployeeAction() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            var dialog = new AddAndEditEmployeeDialog(Coordinator.getInstance(), selectedEmployee);
            var result = dialog.showAndWait();
            if(result.isPresent()) {
                Employee employee = result.get();
                System.out.println("Edit employee: " + employee);
                refresh();
            }
            else {
                System.out.println("Edit employee cancelled");
            }
        }
    }


    public void refresh() {
        EmployeeManager employeeManager = Coordinator.getInstance().getEmployeeManager();
        employeeTable.getItems().removeAll(employeeTable.getItems());
        employeeTable.getItems().addAll(employeeManager.getAllEmployees());
    }
    
    
    
    
}
