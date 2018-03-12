/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.instructor;

import db.DatabaseConnection;
import functionality.CoursesRelated;
import functionality.ProfilesObjects;
import gui.RegistrationSystemProject;
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
public class ViewStudentsController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button bk;
    @FXML
    private ComboBox<String> selectCourseToViewStudents;
    @FXML
    private TableView<ProfilesObjects> studentsTable;

    private ObservableList <ProfilesObjects> profilesTable;
   
    @FXML
    private TableColumn<ProfilesObjects, Integer> Id;
    @FXML
    private TableColumn<ProfilesObjects, String> studentName;


    
    @FXML
    private void loadDashBoard(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("InstructorDashBoard.fxml"));
        root.getChildren().setAll(pane);
    }
    
    
 

public void buildData(int courseNumber){
    
    profilesTable = FXCollections.observableArrayList();
    try{      
        Statement statement = DatabaseConnection.connection.createStatement(); 
        String st = "Select id , firstName , lastName  from profiles WHERE"
                + " id IN ( select id FROM takes WHERE courseNumber = "+courseNumber +" ) ";      
        ResultSet result = statement.executeQuery(st);
               
        while(result.next()){
            ProfilesObjects p = new ProfilesObjects();
            p.setFullName(result.getString("firstName")+" "+result.getString("lastName"));
            p.setId(result.getInt("id"));
            
            profilesTable.add(p);                  
        }
        studentsTable.setItems(profilesTable);
    }
    catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
}
    
 

 @FXML
    private void loadStudents(ActionEvent event) {
        
       String  course = selectCourseToViewStudents.getValue();
       course = course.substring(0,course.indexOf(" "));
       int courseNumber = Integer.parseInt(course);
       buildData(courseNumber);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RegistrationSystemProject.stage.setResizable(false);
        
         try {
            selectCourseToViewStudents.getItems().addAll(CoursesRelated.fillCoursesByInstructorCombobox());
        } catch (SQLException ex) {
            Logger.getLogger(EditCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Id.setCellValueFactory(new PropertyValueFactory<ProfilesObjects, Integer>("id"));
        studentName.setCellValueFactory(new PropertyValueFactory<ProfilesObjects, String>("fullName"));

         
    }    

   
    
}
