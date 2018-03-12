/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.student;

import db.DatabaseConnection;
import functionality.CoursesRelated;
import functionality.CoursesObjects;
import functionality.ProfilesObjects;
import gui.RegistrationSystemProject;
import gui.instructor.EditCourseController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author shorouk
 */
public class CourseInfoInstructorDepartmentController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button bk;
    @FXML
    private ComboBox<String> departmentsMenue;
    @FXML
    private ComboBox<String> instructorsMenue;
    @FXML
    private TableView<CoursesObjects> departmentsList;
    @FXML
    private TableView<CoursesObjects> InstructorsList;

    private ObservableList <CoursesObjects> table1;
    private ObservableList <CoursesObjects> table2;
    
    @FXML
    private TableColumn<CoursesObjects, String> courses1;
    @FXML
    private TableColumn<CoursesObjects, String> courses2;
    

    @FXML private void loadCoursesInfo2 (ActionEvent event) throws IOException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("CoursesInfo.fxml"));
        root.getChildren().setAll(pane);
     }
    
  
    
    
public void buildData1(String deptName){
    
    table1 = FXCollections.observableArrayList();
    try{      
        Statement statement = DatabaseConnection.connection.createStatement(); 
        
        
        String st = "SELECT courseName FROM courses WHERE courseNumber IN"
                + " (SELECT courseNumber FROM coursesdepartments WHERE deptId IN"
                + " ( SELECT deptId FROM departments WHERE deptName = '"+deptName+"'))";   
        ResultSet result = statement.executeQuery(st);
               
        while(result.next()){
            CoursesObjects c = new CoursesObjects();
            c.setCourse(result.getString(1));
            table1.add(c);                  
        }
        departmentsList.setItems(table1);   
    }
    catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
}
    

public void buildData2(String fname , String lname){
    
    table2 = FXCollections.observableArrayList();
    try{      
        Statement statement = DatabaseConnection.connection.createStatement(); 
        
        String st = "SELECT courseName FROM courses WHERE courseNumber IN"
                + " (SELECT courseNumber FROM teaches WHERE id IN "
                + "( SELECT id FROM profiles WHERE firstName = '"+fname
                + "' AND lastName = '"+lname+ "' ))" ;

        ResultSet result = statement.executeQuery(st);
               
        while(result.next()){
            CoursesObjects c = new CoursesObjects();
            c.setCourse(result.getString(1));
            table2.add(c);                  
        }
        InstructorsList.setItems(table2);   
    }
    catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
}


    @FXML
    private void loadCoursesByDept(ActionEvent event) {
        
       String  dept = departmentsMenue.getValue();
       buildData1(dept);
    }

    @FXML
    private void loadCoursesByInstructor(ActionEvent event) {
        
        String fullName = instructorsMenue.getValue();
        String fname="" , lname="" ;
        fname = fullName.substring(0,fullName.indexOf(" "));
        lname = fullName.substring(fullName.indexOf(" ")+1,fullName.length());
        buildData2(fname,lname);
        
    }
    
    
      @Override
    public void initialize(URL url, ResourceBundle rb) {
    RegistrationSystemProject.stage.setResizable(false);
    
    try {
            departmentsMenue.getItems().addAll(CoursesRelated.fillAllDepartmentsComboBox());
            instructorsMenue.getItems().addAll(CoursesRelated.fillAllInstructors());
            
            courses1.setCellValueFactory(new PropertyValueFactory<CoursesObjects, String>("course"));
            courses2.setCellValueFactory(new PropertyValueFactory<CoursesObjects, String>("course"));
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(EditCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
