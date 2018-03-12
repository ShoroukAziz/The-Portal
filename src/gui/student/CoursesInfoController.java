
package gui.student;

import functionality.ProfilesRelated;
import gui.RegistrationSystemProject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author shorouk
 */
public class CoursesInfoController implements Initializable
{
    @FXML private AnchorPane root;  
    @FXML private AnchorPane viewCourseInfoPane;

    
    @FXML private void loadDashBoard (ActionEvent event ) throws IOException {
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("StudentDashBoard.fxml"));
        root.getChildren().setAll(pane);
        
    }
   @FXML private void loadDashBoard2 (ActionEvent event ) throws IOException {
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("StudentDashBoard.fxml"));
         viewCourseInfoPane.getChildren().setAll(pane);
        
    }
  
    /*******************************Navigation**********************************************/
    @FXML private void handelPrereqButton(ActionEvent event) throws IOException {
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("CourseInfoPrereq.fxml"));
        viewCourseInfoPane.getChildren().setAll(pane);
    }
       
    @FXML private void handelDeptInstButton(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("CourseInfoInstructorDepartment.fxml"));
        viewCourseInfoPane.getChildren().setAll(pane);
        
    }

    @FXML private void handeDayPeriodButton(ActionEvent event) throws IOException {
        
         AnchorPane pane = FXMLLoader.load(getClass().getResource("CourseInfoIDayPeriod.fxml"));
        viewCourseInfoPane.getChildren().setAll(pane);
    }
     
       
     
/**********************************************************************************************
*                          view courses info with day / period                     *
***********************************************************************************************/     
     @FXML
    private ChoiceBox<?> scheduleCoursesMenue;
    @FXML
    private TableView<?> allCoursesSchedule;
    
    



    @FXML
    private void loadCoursesInfo2(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    RegistrationSystemProject.stage.setResizable(false);
    }    
}
