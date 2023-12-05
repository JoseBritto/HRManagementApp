package com.group55.project.ui;

import com.group55.project.Coordinator;
import com.group55.project.Department;
import com.group55.project.DepartmentManager;
import com.group55.project.Employee;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class AddAndEditEmployeeDialog extends Dialog<Employee> {

    private final Coordinator coordinator;
    private String firstName;
    private String lastName;
    private Department department;
    private String position;
    private LocalDate dateOfHire;
    
    public AddAndEditEmployeeDialog(Coordinator coordinator, Employee employee) {
        this.coordinator = coordinator;
        this.setTitle("Edit Employee");
        this.setHeaderText("Edit Employee");

        
        setupDialog(employee);
    }
    
    public AddAndEditEmployeeDialog(Coordinator coordinator) {
        this.coordinator = coordinator;
        this.setTitle("Add Employee");
        this.setHeaderText("Add Employee");

        setupDialog(null);
    }
    
    public void setupDialog(Employee employee) {

        var addEmployeeDialogGrid = new GridPane();
        addEmployeeDialogGrid.setHgap(10);
        addEmployeeDialogGrid.setVgap(10);
        addEmployeeDialogGrid.setPadding(new Insets(20, 150, 10, 10));


        var firstNameLabel = new Label("First Name:");
        var firstNameTextField = new TextField();
        var lastNameLabel = new Label("Last Name:");
        var lastNameTextField = new TextField();
        var departmentLabel = new Label("Department:");
        var departmentComboBox = getDepartmentComboBox(coordinator.getDepartmentManager());
        var positionLabel = new Label("Position:");
        var positionTextField = new TextField();
        var dateOfHireLabel = new Label("Date of Hire:");
        var dateOfHireDatePicker = new DatePicker();
        dateOfHireDatePicker.setValue(LocalDate.now());
        dateOfHireDatePicker.getEditor().setDisable(true);
        
        if(employee != null) {
            firstNameTextField.setText(employee.getFirstName());
            lastNameTextField.setText(employee.getLastName());
            departmentComboBox.setValue(coordinator.getDepartmentManager().getDepartment(employee.getDepartmentId()));
            positionTextField.setText(employee.getPosition());
            dateOfHireDatePicker.setValue(employee.getDateOfHire());
        }

        addEmployeeDialogGrid.add(firstNameLabel, 0, 0);
        addEmployeeDialogGrid.add(firstNameTextField, 1, 0);
        addEmployeeDialogGrid.add(lastNameLabel, 0, 1);
        addEmployeeDialogGrid.add(lastNameTextField, 1, 1);
        addEmployeeDialogGrid.add(departmentLabel, 0, 5);
        addEmployeeDialogGrid.add(departmentComboBox, 1, 5);
        addEmployeeDialogGrid.add(positionLabel, 0, 6);
        addEmployeeDialogGrid.add(positionTextField, 1, 6);
        addEmployeeDialogGrid.add(dateOfHireLabel, 0, 7);
        addEmployeeDialogGrid.add(dateOfHireDatePicker, 1, 7);


        this.getDialogPane().setContent(addEmployeeDialogGrid);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Validation
        var okButton = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            // First and last name must be filled in
            if (firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty()) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("First and last name must be filled in.");
                alert.showAndWait();
                event.consume();
                return;
            }
            // Department must be selected
            if (departmentComboBox.getValue() == null) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Department must be selected.");
                alert.showAndWait();
                event.consume();
                return;
            }
            // Position must be filled in
            if (positionTextField.getText().isEmpty()) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Position must be filled in.");
                alert.showAndWait();
                event.consume();
                return;
            }
            // Date of hire must be selected
            if (dateOfHireDatePicker.getValue() == null) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Date of hire must be selected.");
                alert.showAndWait();
                event.consume();
                return;
            }

            // No strings should contain commas
            if (firstNameTextField.getText().contains(",") || lastNameTextField.getText().contains(",") || positionTextField.getText().contains(",")) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("No input should contain commas.");
                alert.showAndWait();
                event.consume();
                return;
            }

            // No strings should contain new lines
            if (firstNameTextField.getText().contains("\n") || lastNameTextField.getText().contains("\n") || positionTextField.getText().contains("\n")) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("No input should contain new lines.");
                alert.showAndWait();
                event.consume();
                return;
            }

            // No duplicate employees
            for (Employee emp : coordinator.getEmployeeManager().getAllEmployees()) {
                if(employee != null && emp.getEmployeeID() == employee.getEmployeeID()) {
                    continue;
                }
                if (emp.getFirstName().equals(firstNameTextField.getText().trim()) && emp.getLastName().equals(lastNameTextField.getText().trim())) {
                    var alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Employee already exists.");
                    alert.showAndWait();
                    event.consume();

                    return;
                }
            }
        });

        this.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                firstName = firstNameTextField.getText().trim();
                lastName = lastNameTextField.getText().trim();
                department = departmentComboBox.getValue();
                position = positionTextField.getText().trim();
                dateOfHire = dateOfHireDatePicker.getValue();
                if(employee != null) {
                    employee.setFirstName(firstName);
                    employee.setLastName(lastName);
                    employee.setDepartment(department);
                    employee.setPosition(position);
                    employee.setDateOfHire(dateOfHire);
                    return employee;
                }
                return coordinator.getEmployeeManager().addEmployee(firstName, lastName, department, position, dateOfHire);
            }
            return null;
        });
    }
    
    // Getter for choose department drop down menu. Takes in DepartmentManager and return javafx dropdown menu.
    public ComboBox<Department> getDepartmentComboBox(DepartmentManager departmentManager) {
        javafx.scene.control.ComboBox<Department> departmentComboBox = new javafx.scene.control.ComboBox<>();
        departmentComboBox.getItems().addAll(departmentManager.getDepartments());
        departmentComboBox.setPromptText("Choose Department");
        departmentComboBox.setConverter(new javafx.util.StringConverter<Department>() {
            @Override
            public String toString(Department department) {
                return department.getDepartmentName();
            }

            @Override
            public Department fromString(String string) {
                return null;
            }
        });
        return departmentComboBox;
    }
    
}
