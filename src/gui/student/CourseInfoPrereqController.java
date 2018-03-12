/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.student;

import db.DatabaseConnection;
import functionality.CoursesObjects;
import functionality.ProfilesObjects;
import gui.RegistrationSystemProject;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author shorouk
 */
public class CourseInfoPrereqController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button bk;
    @FXML
    private Label title;
    @FXML
    private TableView<CoursesObjects> prerequisitesTable;
    @FXML
    private TableColumn<CoursesObjects, String> course;
    @FXML
    private TableColumn<CoursesObjects, String> prerreq;

    private ObservableList <CoursesObjects> table;
    

    @FXML private void loadCoursesInfo2 (ActionEvent event) throws IOException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("CoursesInfo.fxml"));
        root.getChildren().setAll(pane);
     }
    
    
    
public void buildData(){
    
    table = FXCollections.observableArrayList();
    try{      
        Statement statement = DatabaseConnection.connection.createStatement(); 
        String st ="select P.courseNumberCourse, C1.courseName as co,"
                + " P.courseNumberPrerequisite , C2.courseName as pr"
                + " from prerequisite as P"
                + " join courses as C1 on P.courseNumberCourse = c1.courseNumber"
                + " join courses as C2 on P.courseNumberPrerequisite = c2.courseNumber";
        
        ResultSet result = statement.executeQuery(st);
               
        while(result.next()){
            CoursesObjects c = new CoursesObjects();
            c.setCourse(result.getString(1)+"  "+result.getString(2));
            c.setPrerequisite(result.getString(3)+"  "+result.getString(4));
            
            table.add(c);                  
        }
        prerequisitesTable.setItems(table);
    }
    catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
}
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    RegistrationSystemProject.stage.setResizable(false);
    buildData();
    course.setCellValueFactory(new PropertyValueFactory<CoursesObjects, String>("course"));
    prerreq.setCellValueFactory(new PropertyValueFactory<CoursesObjects, String>("prerequisite"));
    
    
    }    
    
}
