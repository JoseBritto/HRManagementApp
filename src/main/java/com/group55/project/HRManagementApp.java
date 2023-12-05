package com.group55.project;

import com.group55.project.ui.DepartmentManagementContent;
import com.group55.project.ui.EmployeeManagementContent;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HRManagementApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("HR Management Application");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Create a menu bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> primaryStage.close());
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);

        // Create tabs for different sections
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab employeeTab = new Tab("Employees");
        employeeTab.setContent(createEmployeeManagementContent());

        Tab departmentsTab = new Tab("Departments");
        departmentsTab.setContent(createDepartmentsContent());
        
        Tab payrollTab = new Tab("Payroll Processing");
        payrollTab.setContent(createPayrollProcessingContent());

        Tab reportingTab = new Tab("Reporting");
        reportingTab.setContent(createReportingContent());

        tabPane.getTabs().addAll(employeeTab, departmentsTab, payrollTab, reportingTab);

        //TODO: Remove this menu bar completely
        //root.setTop(menuBar);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createDepartmentsContent() {
        return new DepartmentManagementContent();
    }

    private VBox createEmployeeManagementContent() {
        return new EmployeeManagementContent();
    }

    private VBox createPayrollProcessingContent() {
        VBox payrollContent = new VBox(10);
        payrollContent.setPadding(new Insets(10));
        payrollContent.getStyleClass().add("content");

        payrollContent.getChildren().addAll(
                new Label("Payroll Processing Content"),
                new Button("Process Payroll"),
                new Button("Generate Payroll Report")
        );

        return payrollContent;
    }

    private VBox createReportingContent() {
        VBox reportingContent = new VBox(10);
        reportingContent.setPadding(new Insets(10));
        reportingContent.getStyleClass().add("content");

        reportingContent.getChildren().addAll(
                new Label("Reporting Content"),
                new Button("Generate Report"),
                new Button("View Reports")
        );

        return reportingContent;
    }
}
