package gui.student;

import functionality.CoursesRelated;
import functionality.ProfilesRelated;
import gui.RegistrationSystemProject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class StudentDashBoardController implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label deptLabel;
    @FXML
    private ImageView pic;
    @FXML
    private Button b4 ;
   
    
/**********************************************************************************************
*                                       NAVIGATION CONTROL                                    *
***********************************************************************************************/    

@FXML private AnchorPane dashBoard;
private AnchorPane root;  
    @FXML
    private Button b7;
    @FXML
    private ImageView transcript;
    @FXML
    private Button b3;
    @FXML
    private ImageView viewYourSchedule;
    @FXML
    private Button b5;
    @FXML
    private ImageView coursesInfo;
    @FXML
    private Button b6;
    @FXML
    private ImageView dropACourse;
    @FXML
    private Button b2;
    @FXML
    private ImageView registerACourse;
    @FXML
    private ImageView requestGraduation;
    @FXML
    private Button b1;
    @FXML
    private Button logOut;

  
     @FXML
    private void logout(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/Home.fxml"));
        dashBoard.getChildren().setAll(pane);
    }   

private void loadDashBoard (ActionEvent event ) throws IOException {
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("StudentDashBoard.fxml"));
        root.getChildren().setAll(pane);
        
    }

    
@FXML
private void loadEditYourProfile (ActionEvent event) throws IOException, SQLException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/EditYourProfile.fxml"));
        dashBoard.getChildren().setAll(pane);

     }
    
@FXML
private void loadRegister (ActionEvent event) throws IOException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Register.fxml"));
         dashBoard.getChildren().setAll(pane);
     }
@FXML
private void loadStudentSchedule (ActionEvent event) throws IOException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("StudentSchedule.fxml"));
         dashBoard.getChildren().setAll(pane);
     }
@FXML
private void loadDropACourse (ActionEvent event) throws IOException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("DropACourse.fxml"));
         dashBoard.getChildren().setAll(pane);
     }
@FXML 
private void loadTranscript (ActionEvent event) throws IOException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Transcript.fxml"));
         dashBoard.getChildren().setAll(pane);
     }
@FXML
private void loadCoursesInfo (ActionEvent event) throws IOException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("CoursesInfo.fxml"));
        dashBoard.getChildren().setAll(pane);
     }




/**********************************************************************************************
*                           Request Graduation and control                                    *
***********************************************************************************************/       
    
     @FXML private void HandelRequestGraduation (ActionEvent event) throws SQLException
     {
         CoursesRelated.graduate();
         b4.setDisable(true);  //disable the button
         
     }
  
     
/**********************************************************************************************
*                                        Initializer                                          *
***********************************************************************************************/   
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RegistrationSystemProject.stage.setResizable(false);
        nameLabel.setText(ProfilesRelated.firstName +" " +ProfilesRelated.lastName);
        idLabel.setText(ProfilesRelated.idString);
        //hoursLabel
        deptLabel.setText(ProfilesRelated.department);
        pic.setImage(ProfilesRelated.picture);
        
        
    }    


    
    
}