
package gui;

import functionality.ProfilesRelated;
import static functionality.ProfilesRelated.id;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import db.DatabaseConnection;

/**
 *
 * @author shorouk
 */
public class EditYourProfileController implements Initializable
{
    @FXML private AnchorPane root;  
    @FXML private void loadDashBoard (ActionEvent event ) throws IOException {
        
       if ( ProfilesRelated.id >= 220 )
       {
           
           AnchorPane pane = FXMLLoader.load(getClass().getResource("student/StudentDashBoard.fxml"));
           root.getChildren().setAll(pane);
       }
       else
       {
           
           AnchorPane pane = FXMLLoader.load(getClass().getResource("instructor/InstructorDashBoard.fxml"));
           root.getChildren().setAll(pane);
       }
        
        
    }
    
    
    @FXML private Button updateProfileButton;
    @FXML private TextField firstNameFiels;
    @FXML private TextField lastNameField;
    @FXML private TextField adressField;
    @FXML private TextField userNameEditField;
    @FXML private TextField passwordEditField;
    
   

    @FXML
    private void handelUbdateProfileButton(ActionEvent event) throws SQLException, IOException {
	String fn = firstNameFiels.getText();
        String ln = lastNameField.getText();
        String a = adressField.getText();
        String u = userNameEditField.getText();
        String p = passwordEditField.getText();
        
        
        if ( fn.isEmpty() || ln.isEmpty() || a.isEmpty() || u.isEmpty() || p.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please Enter Valid Values");
        }
        else // chicking to see if the username is valid 
        {
            Statement statement = DatabaseConnection.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id , userName FROM profiles WHERE userName ='" +u+"';");
            String theid="";
            String user="";
            while (resultSet.next())
            {
                theid = resultSet.getString(1);
                user = resultSet.getString(2);
            }
            if(theid.startsWith("11")||theid.startsWith("22") )
            {
                if (user.matches(ProfilesRelated.userName)){
                    ProfilesRelated.updateTheProfile(fn, ln, a, u, p);
                }
                else
                    JOptionPane.showMessageDialog(null, "The user name already exists. please chose another one");
            }
            else //update profile
            {
               ProfilesRelated.updateTheProfile(fn, ln, a, u, p);

            }
        }
        
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RegistrationSystemProject.stage.setResizable(false);
        firstNameFiels.setText(ProfilesRelated.firstName);
        lastNameField.setText(ProfilesRelated.lastName);
        adressField.setText(ProfilesRelated.adress);
        userNameEditField.setText(ProfilesRelated.userName);
        passwordEditField.setText(ProfilesRelated.password);
}
    
}
