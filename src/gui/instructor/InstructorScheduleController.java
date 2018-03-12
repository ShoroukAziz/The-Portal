/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.instructor;

import db.DatabaseConnection;
import functionality.ProfilesRelated;
import functionality.Schedule;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class InstructorScheduleController implements Initializable {

    @FXML
    private AnchorPane root;
    private TableView<?> studentScheduleTable;
    @FXML
    private Button bk;
    @FXML
    private Label title;
    @FXML
    private TableView<Schedule> InstructorScheduleTable;
    @FXML
    private TableColumn<Schedule, String> sat;
    @FXML
    private TableColumn<Schedule, String> sun;
    @FXML
    private TableColumn<Schedule, String> mon;
    @FXML
    private TableColumn<Schedule, String> tue;
    @FXML
    private TableColumn<Schedule, String> wed;

    private ObservableList <Schedule> table1;
    
    @FXML
    private void loadDashBoard(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("InstructorDashBoard.fxml"));
        root.getChildren().setAll(pane);
    }
    
    
     public  void fillInstructorTable () throws SQLException
     {
         table1 = FXCollections.observableArrayList(); 
        try{      
        Statement statement = DatabaseConnection.connection.createStatement(); 
        
        String st = "SELECT collegeschedule.day , collegeschedule.period ,"
                + " collegeschedule.roomNumber , courses.courseName"
                + " FROM collegeschedule JOIN courses JOIN teaches"
                + " ON courses.courseNumber = collegeschedule.courseNumber"
                + " and teaches.courseNumber = courses.courseNumber"
                + " WHERE teaches.id = "+ ProfilesRelated.id
                + " and teaches.semester ='spring 17' ORDER BY period";
        
        ResultSet result = statement.executeQuery(st);
               
        while(result.next()){
            Schedule s = new Schedule();
            String day = (result.getString(1));
            int i , j;
            
            if (day.contains("Saturday"))
            {
                s.setSaturday("Period "+result.getInt(2)+"\n"+result.getString(4) +"\n Room:"+result.getInt(3));
                
            }
            else if (day.contains("Sunday"))
            {
                s.setSunday("Period "+result.getInt(2)+"\n"+result.getString(4) +"\n Room:"+result.getInt(3));
            }
            else if (day.contains("Monday"))
            {
                s.setMonday("Period "+result.getInt(2)+"\n"+result.getString(4) +"\n Room:"+result.getInt(3));
            }
            else if (day.contains("Tuesday"))
            {
                s.setTuesday("Period "+result.getInt(2)+"\n"+result.getString(4) +"\n Room:"+result.getInt(3));
            }
            else if (day.contains("Wednesday"))
            {
                s.setWednesday("Period "+result.getInt(2)+"\n"+result.getString(4) +"\n Room:"+result.getInt(3));
            }
            table1.add(s);
        }
        InstructorScheduleTable.setItems(table1);   
    }
        
    catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }       
        
    

     }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RegistrationSystemProject.stage.setResizable(false);
        
        try {
            fillInstructorTable ();
            sat.setCellValueFactory(new PropertyValueFactory<Schedule, String>("Saturday"));
             sun.setCellValueFactory(new PropertyValueFactory<Schedule, String>("Sunday"));
             mon.setCellValueFactory(new PropertyValueFactory<Schedule, String>("Monday"));
             tue.setCellValueFactory(new PropertyValueFactory<Schedule, String>("Tuesday"));
             wed.setCellValueFactory(new PropertyValueFactory<Schedule, String>("Wednesday"));
            
        } catch (SQLException ex) {
            Logger.getLogger(InstructorScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
