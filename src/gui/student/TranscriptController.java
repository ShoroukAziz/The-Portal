
package gui.student;

import db.DatabaseConnection;
import functionality.CoursesObjects;
import functionality.CoursesRelated;
import functionality.ProfilesRelated;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author shorouk
 */
public class TranscriptController implements Initializable
{
    @FXML private AnchorPane root;  
    @FXML
    private Button bk;
    @FXML
    private Label title;
    @FXML
    private TableColumn<CoursesObjects, String> courseName;
    @FXML
    private TableColumn<CoursesObjects, String> semester;
    @FXML
    private TableColumn<CoursesObjects, String> grade;
    @FXML
    private TableView<CoursesObjects> transcriptTable;
  
    private ObservableList <CoursesObjects> transcript;
    
    @FXML private void loadDashBoard (ActionEvent event ) throws IOException {
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("StudentDashBoard.fxml"));
        root.getChildren().setAll(pane); 
    }
    
     
public void buildData(){
    
    transcript = FXCollections.observableArrayList();
    try{      
        Statement statement = DatabaseConnection.connection.createStatement(); 
        
        
        String st = "SELECT courses.courseName , takes.semester , takes.grade FROM courses"
                + " JOIN takes ON courses.courseNumber = takes.courseNumber"
                + " WHERE takes.id = "+ ProfilesRelated.id 
                + " ORDER BY takes.semester , courses.courseName";   
        ResultSet result = statement.executeQuery(st);
               
        while(result.next()){
            CoursesObjects c = new CoursesObjects();
            c.setCourse(result.getString(1));
            c.setSemester(result.getString(2));
            c.setGrade(result.getString(3));
            transcript.add(c);                  
        }
        transcriptTable.setItems(transcript);   
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
        
        buildData();
        courseName.setCellValueFactory(new PropertyValueFactory<CoursesObjects, String>("course"));
        semester.setCellValueFactory(new PropertyValueFactory<CoursesObjects, String>("semester"));
        grade.setCellValueFactory(new PropertyValueFactory<CoursesObjects, String>("grade"));
        

}
    
}
