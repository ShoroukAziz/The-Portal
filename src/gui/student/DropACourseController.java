
package gui.student;

import functionality.CoursesRelated;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author shorouk
 */
public class DropACourseController implements Initializable
{
    @FXML private AnchorPane root;  
    @FXML
    private Button bk;
    @FXML private void loadDashBoard (ActionEvent event ) throws IOException {
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("StudentDashBoard.fxml"));
        root.getChildren().setAll(pane);
        
    }
     @FXML
    ComboBox<String> dropCoursesMenue ;
    @FXML
    Button dropButton ;
    
    @FXML
    private void handleDropButtonAction(ActionEvent event) throws SQLException {
        
        String courseName = dropCoursesMenue.getValue();
        CoursesRelated.dropACourse(courseName);
        
    }
    
     @FXML
    private void courseSelected(ActionEvent event) {
         dropButton.setDisable(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        RegistrationSystemProject.stage.setResizable(false);
        try {
            dropCoursesMenue.getItems().addAll(CoursesRelated.filldropCourseComboBox());
            
        } catch (SQLException ex) {
            Logger.getLogger(DropACourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        dropButton.setDisable(true);

}

   
    
}
