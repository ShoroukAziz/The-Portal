/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.instructor;

import functionality.CoursesRelated;
import functionality.Misc;
import gui.RegistrationSystemProject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author shorouk
 */
public class TeachaCourseController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button bk;
    @FXML
    private ComboBox<String> coursesMenue;
    @FXML
    private Button teachButton;
    @FXML
    private ComboBox<String> dayMenue;
    @FXML
    private ComboBox<Integer> periodMenue;
    @FXML
    private ComboBox<Integer> roomMenue;

    @FXML
    private void loadDashBoard(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("InstructorDashBoard.fxml"));
           root.getChildren().setAll(pane);
    }
    
     @FXML
    private void handleteachButtonAction(ActionEvent event) throws SQLException {
        
        String selectedCourse = coursesMenue.getValue();
        String day = dayMenue.getValue();
        int room = roomMenue.getValue();
        int period = periodMenue.getValue();
        selectedCourse = selectedCourse.substring(0,selectedCourse.indexOf(" "));
        int num = Integer.parseInt(selectedCourse);
        CoursesRelated.teachCourse(num,day,room,period);
        
    }
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        RegistrationSystemProject.stage.setResizable(false);
         try {
            coursesMenue.getItems().addAll(CoursesRelated.fillregisterCourseComboBox());
            coursesMenue.getSelectionModel().selectFirst();
            dayMenue.getItems().addAll(Misc.fildaysComboBox());
            dayMenue.getSelectionModel().selectFirst();
            periodMenue.getItems().addAll(Misc.filperiodsComboBox());
            periodMenue.getSelectionModel().selectFirst();
            roomMenue.getItems().addAll(Misc.fillRoomsComboBox());
            roomMenue.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            Logger.getLogger(EditCourseController.class.getName()).log(Level.SEVERE, null, ex);
        
        }

   
    }    
}