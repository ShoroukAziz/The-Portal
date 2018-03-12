
package gui.student;

import db.DatabaseConnection;
import functionality.CoursesObjects;
import functionality.ProfilesRelated;
import functionality.Schedule;
import gui.RegistrationSystemProject;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
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

/**
 *
 * @author shorouk
 */
public class StudentSchudeleController implements Initializable
{
    @FXML private AnchorPane root;  
    @FXML
    private Button bk;
    @FXML
    private Label title;
    @FXML
    private TableColumn<Schedule, String> saturday;
    @FXML
    private TableColumn<Schedule, String> sunday;
    @FXML
    private TableColumn<Schedule, String> monday;
    @FXML
    private TableColumn<Schedule, String> tuesday;
    @FXML
    private TableColumn<Schedule, String> wednesday;
    @FXML private void loadDashBoard (ActionEvent event ) throws IOException {
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("StudentDashBoard.fxml"));
        root.getChildren().setAll(pane);
        
    }
    
     @FXML
    private TableView<Schedule> studentScheduleTable;
     
    private ObservableList <Schedule> table1;
     
     public  void fillStudentTable () throws SQLException
     {
         table1 = FXCollections.observableArrayList(); 
        try{      
        Statement statement = DatabaseConnection.connection.createStatement(); 
        
        String st = "SELECT collegeschedule.day , collegeschedule.period ,"
                + " collegeschedule.roomNumber , courses.courseName"
                + " FROM collegeschedule JOIN courses JOIN takes"
                + " ON courses.courseNumber = collegeschedule.courseNumber"
                + " and takes.courseNumber = courses.courseNumber"
                + " WHERE takes.id = "+ProfilesRelated.id
                + " and takes.semester ='spring 17' ORDER BY period" ;
        
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
        studentScheduleTable.setItems(table1);   
    }
        
    catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }       
        
    

     }
     
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RegistrationSystemProject.stage.setResizable(false);
        try {
            fillStudentTable ();
             saturday.setCellValueFactory(new PropertyValueFactory<Schedule, String>("Saturday"));
             sunday.setCellValueFactory(new PropertyValueFactory<Schedule, String>("Sunday"));
             monday.setCellValueFactory(new PropertyValueFactory<Schedule, String>("Monday"));
             tuesday.setCellValueFactory(new PropertyValueFactory<Schedule, String>("Tuesday"));
             wednesday.setCellValueFactory(new PropertyValueFactory<Schedule, String>("Wednesday"));
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentSchudeleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

}
    
}
