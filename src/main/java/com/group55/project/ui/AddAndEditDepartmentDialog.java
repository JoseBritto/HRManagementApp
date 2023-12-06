package com.group55.project.ui;

import com.group55.project.Coordinator;
import com.group55.project.Department;
import javafx.scene.control.Dialog;
/*
    1. John Darrell Tamayo, 101452155
    2. Ashish Rajan Sherry, 101423478
    3. Jose Britto Saaji, 101416601
    4. Akorede Osunkoya, 101477407

 */
public class AddAndEditDepartmentDialog extends Dialog<String> {
    public AddAndEditDepartmentDialog(Coordinator coordinator) {
        this.setTitle("Add Department");
        this.setHeaderText("Add Department");

        setupDialog(coordinator, null);
    }
    
    public AddAndEditDepartmentDialog(Coordinator coordinator, Department department) {
        this.setTitle("Edit Department");
        this.setHeaderText("Edit Department");

        setupDialog(coordinator, department);
    }

    private void setupDialog(Coordinator coordinator, Department department) {
        var addDepartmentDialogGrid = new javafx.scene.layout.GridPane();
        addDepartmentDialogGrid.setHgap(10);
        addDepartmentDialogGrid.setVgap(10);
        addDepartmentDialogGrid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        var departmentNameLabel = new javafx.scene.control.Label("Department Name:");
        var departmentNameTextField = new javafx.scene.control.TextField();
        if(department != null) {
            departmentNameTextField.setText(department.getDepartmentName());
        }

        addDepartmentDialogGrid.add(departmentNameLabel, 0, 0);
        addDepartmentDialogGrid.add(departmentNameTextField, 1, 0);

        this.getDialogPane().setContent(addDepartmentDialogGrid);
        this.getDialogPane().getButtonTypes().addAll(javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);

        var okButton = (javafx.scene.control.Button) this.getDialogPane().lookupButton(javafx.scene.control.ButtonType.OK);
        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if(departmentNameTextField.getText().isEmpty()) {
                event.consume();
                var alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Department name cannot be empty");
                alert.showAndWait();
                return;
            } 
            var existingDepartments = coordinator.getDepartmentManager().getDepartments();
            for(var dpart : existingDepartments) {
                if(department != null && dpart.getDepartmentID() == department.getDepartmentID()) {
                    continue;
                }
                if(dpart.getDepartmentName().equalsIgnoreCase(departmentNameTextField.getText().trim())) {
                    event.consume();
                    var alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Department name already exists");
                    alert.showAndWait();
                    return;
                }
            }
        });

        this.setResultConverter(dialogButton -> {
            if(dialogButton == javafx.scene.control.ButtonType.OK) {
                return departmentNameTextField.getText().trim();
            }
            return null;
        });
    }
}
