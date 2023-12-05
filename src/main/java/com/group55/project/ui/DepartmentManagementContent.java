package com.group55.project.ui;

import com.group55.project.Coordinator;
import com.group55.project.Department;
import com.group55.project.ui.AddAndEditDepartmentDialog;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DepartmentManagementContent extends VBox {
    
    private TableView<Department> departmentTable;
    public DepartmentManagementContent(){
        super(10);


        this.setPadding(new Insets(10));
        this.getStyleClass().add("content");

        // A search bar to search for department names
        HBox searchBox = new HBox(10);
        searchBox.getStyleClass().add("search-box");
        searchBox.getChildren().addAll(
                new Label("Search:"),
                new TextField()
        );


        // A table to display departments
        departmentTable = new TableView<>();
        departmentTable.setPrefHeight(400);
        departmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        
        // Create columns for the table
        javafx.scene.control.TableColumn<Department, Integer> departmentIDColumn = new javafx.scene.control.TableColumn<>("Department ID");
        departmentIDColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("departmentID"));
        departmentIDColumn.setPrefWidth(20);
        
        javafx.scene.control.TableColumn<Department, String> departmentNameColumn = new javafx.scene.control.TableColumn<>("Department Name");
        departmentNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("departmentName"));
        departmentNameColumn.setPrefWidth(100);
        
        departmentTable.getColumns().addAll(departmentIDColumn, departmentNameColumn);
        
        this.getChildren().addAll(searchBox, departmentTable);

        // Add double click listener to the table rows
        departmentTable.setRowFactory(tv -> {
            TableRow<Department> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Department rowData = row.getItem();
                    System.out.println("Double click on: " + rowData);
                    editDepartmentAction();
                }
            });
            return row;
        });
        
        departmentTable.getItems().addAll(Coordinator.getInstance().getDepartmentManager().getDepartments());
        
        // Add a listener to the search box to filter the table
        TextField searchField = (TextField) searchBox.getChildren().get(1);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            refresh();
            departmentTable.getItems()
                    .removeIf(department -> !department.getDepartmentName().toLowerCase().contains(newValue.toLowerCase()));
        });
        
        // Add a button to add a new department
        var addButton = new javafx.scene.control.Button("Add Department");
        addButton.setOnAction(event -> {
            addDepartmentAction();
        });
        
        this.getChildren().add(addButton);
        
        // Add context menu to edit and delete departments
        var contextMenu = createTableContextMenu();
        departmentTable.setContextMenu(contextMenu);
        
    }

    private ContextMenu createTableContextMenu() {
        var contextMenu = new ContextMenu();
        var editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(event -> {
            editDepartmentAction();
        });

        var deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> {
            deleteDepartmentAction();
        });

        contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
        return contextMenu;
    }

    private void addDepartmentAction() {
        var addDepartmentDialog = new AddAndEditDepartmentDialog(Coordinator.getInstance());
        var result = addDepartmentDialog.showAndWait();
        result.ifPresent(department -> {
            Coordinator.getInstance().getDepartmentManager().addDepartment(department);
            refresh();
        });
    }

    private void deleteDepartmentAction() {
        // Confirm delete
        var selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();
        if(selectedDepartment == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Department");
        alert.setHeaderText("Delete Department");
        alert.setContentText("Are you sure you want to remove " + selectedDepartment.getDepartmentName() + " from the system?\n" +
                "All employees in this department will be removed as well.");

        alert.getButtonTypes().setAll(ButtonType.NO, ButtonType.YES);

        // Make no the default button
        Button yesButton = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
        Button noButton = (Button) alert.getDialogPane().lookupButton(ButtonType.NO);
        yesButton.setDefaultButton(false);
        noButton.setDefaultButton(true);

        var result = alert.showAndWait();
        result.ifPresent(response -> {
            if (response == ButtonType.YES) {
                System.out.println("Delete department: " + selectedDepartment);
                Coordinator.getInstance().getEmployeeManager().removeEmployeesByDepartmentID(selectedDepartment.getDepartmentID());
                Coordinator.getInstance().getDepartmentManager().removeDepartment(selectedDepartment);
                refresh();
            }
        });
    }

    private void editDepartmentAction() {
        var selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();
        if(selectedDepartment == null) {
            return;
        }
        var editDepartmentDialog = new AddAndEditDepartmentDialog(Coordinator.getInstance(), selectedDepartment);
        var result = editDepartmentDialog.showAndWait();
        result.ifPresent(departmentName -> {
            Coordinator.getInstance().getDepartmentManager()
                    .updateDepartment(selectedDepartment.getDepartmentID(), departmentName);
            refresh();
        });
    }

    public void refresh() {
        departmentTable.getItems().clear();
        departmentTable.getItems().addAll(Coordinator.getInstance().getDepartmentManager().getDepartments());
    }
    
}
