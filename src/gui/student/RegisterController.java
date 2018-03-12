
package gui.student;

import functionality.CoursesRelated;
import gui.RegistrationSystemProject;
import gui.instructor.EditCourseController;
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
 *
 * @author shorouk
 */
public class RegisterController implements Initializable
{
    @FXML private AnchorPane root;  
    @FXML
    private Button bk;
    @FXML private void loadDashBoard (ActionEvent event ) throws IOException {
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("StudentDashBoard.fxml"));
        root.getChildren().setAll(pane);
        
    }
    
    @FXML
    ComboBox<String> coursesMenue ;
    @FXML
    Button registerButton ;
    
    @FXML
    private void handleRegisterButtonAction(ActionEvent event) throws SQLException, IOException {
        
        String selectedCourse = coursesMenue.getValue();
        selectedCourse = selectedCourse.substring(0,selectedCourse.indexOf(" "));
        int num = Integer.parseInt(selectedCourse);
        CoursesRelated.registerCourse(num);
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RegistrationSystemProject.stage.setResizable(false);
        
        try {
            coursesMenue.getItems().addAll(CoursesRelated.fillregisterCourseComboBox());
        } catch (SQLException ex) {
            Logger.getLogger(EditCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    
}
