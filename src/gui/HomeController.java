
package gui;

import functionality.ProfilesRelated;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.DatabaseConnection;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;


/**
 *
 * @author shorouk
 */
public class HomeController implements Initializable {

    static int id;
    
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField userNameField;
    
    @FXML
    private AnchorPane homeRoot ;
    
    @FXML
    private void loadStudentDashBoard (ActionEvent event) throws IOException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("student/StudentDashBoard.fxml"));
        homeRoot.getChildren().setAll(pane);
     }
    
    
    @FXML
    private void loadInstructorDashBoard (ActionEvent event) throws IOException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("instructor/InstructorDashBoard.fxml"));
        homeRoot.getChildren().setAll(pane);
     }
    
    
    @FXML
    private void login(ActionEvent event) throws SQLException, IOException, InterruptedException {
        
        String usr = userNameField.getText();
        String pass = passwordField.getText();
        Statement statement = DatabaseConnection.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM profiles WHERE userName ='" +usr+"' AND password='"+pass+"';");
        String theid="";
        while (resultSet.next())
        {
            theid = resultSet.getString(1);
        }
        
        if(theid.startsWith("22")) // Student
        {
             id = Integer.parseInt(theid);
             ProfilesRelated.filltheProfile(id); 
             loadStudentDashBoard(event);
        }
        else if (theid.startsWith("11")) //instructor
        {
            
            id = Integer.parseInt(theid);
            System.out.println(id);
            ProfilesRelated.filltheProfile(id);
            loadInstructorDashBoard(event);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Please Enter a valid user name and password");
            
        }
        

        
        
        }
     
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RegistrationSystemProject.stage.setResizable(false);
        
        
    }    
    
}
