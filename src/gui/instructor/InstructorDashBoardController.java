/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.instructor;

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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author shorouk
 */
public class InstructorDashBoardController implements Initializable {

    @FXML
    private AnchorPane dashBoard;

    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label deptLabel;
    @FXML
    private ImageView pic;
    @FXML
    private Label deptLabel1;
    @FXML
    private Label idLabel1;
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
    private Button b1;
    @FXML
    private Button b7;
    

     @FXML
    private void logout(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/Home.fxml"));
        dashBoard.getChildren().setAll(pane);
    }   
    
    @FXML
    private void loadInstructorSchedule(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("InstructorSchedule.fxml"));
        dashBoard.getChildren().setAll(pane);
    }

    @FXML
    private void loadViewStudents(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("ViewStudents.fxml"));
        dashBoard.getChildren().setAll(pane);
    }

    @FXML
    private void loadAddNewCourse(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("AddNewCourse.fxml"));
        dashBoard.getChildren().setAll(pane);
    }

    @FXML
    private void loadEditACourse(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("EditCourse.fxml"));
        dashBoard.getChildren().setAll(pane);
        
    }
    
     @FXML
    private void loadTeachNewCourse(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("TeachaCourse.fxml"));
        dashBoard.getChildren().setAll(pane);
    }
    

    @FXML
    private void loadEditProfile(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/EditYourProfile.fxml"));
        dashBoard.getChildren().setAll(pane);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    RegistrationSystemProject.stage.setResizable(false);
    
        nameLabel.setText(ProfilesRelated.firstName +" " +ProfilesRelated.lastName);
        idLabel.setText(ProfilesRelated.idString);
        //hoursLabel
        deptLabel.setText(ProfilesRelated.department);
        pic.setImage(ProfilesRelated.picture);
    }    



   
}
