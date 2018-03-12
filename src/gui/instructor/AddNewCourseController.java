/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.instructor;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author shorouk
 */
public class AddNewCourseController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button addCourseButton;
    @FXML
    private Button bk;
    @FXML
    private TextField courseName;
    @FXML
    private TextField courseNum;
    @FXML
    private TextField term;
    @FXML
    private TextField cap;
    @FXML
    private TextField credits;
    @FXML
    private TextField textbook;
    @FXML
    private ComboBox<String> prereqComboBox;
    @FXML
    private ComboBox<String> deptComboBox;
    @FXML
    private TextField courseNumber;



   
    @FXML
    private void handelAddCourse(ActionEvent event) throws SQLException, IOException {
        
        int cnum = 0;
        String cname ="";
        int t = 0;
        int cp = 0;
        int crdt = 0;
        String txtbk =""; 
        int prereq;
        
        // if any of them is empty 
        
        if ( courseNum.getText().isEmpty() || courseName.getText().isEmpty() ||
                term.getText().isEmpty() || cap.getText().isEmpty() 
                || credits.getText().isEmpty() || textbook.getText().isEmpty()
              ||deptComboBox.getSelectionModel().isEmpty() || prereqComboBox.getSelectionModel().isEmpty() )
        {
            JOptionPane.showMessageDialog(null, "Please Enter Valid Values");
        }
        else
        {
            try
            {
                cnum  = Integer.parseInt(courseNum.getText()) ;
                cname = courseName.getText();
                t =Integer.parseInt(term.getText()) ;
                cp = Integer.parseInt(cap.getText()) ;
                crdt = Integer.parseInt(credits.getText()) ;
                txtbk = textbook.getText();
                String temp =prereqComboBox.getValue();
                String temp2=deptComboBox.getValue();
                temp = temp.substring(0,temp.indexOf(" "));
                prereq = Integer.parseInt(temp);
                
                if(t >= 1 && t <= 5 && crdt >= 1 && crdt <= 3 && cp >= 7 && cp <= 15 
                    && CoursesRelated.canCourseNameBeChanged(cname) && CoursesRelated.canCourseNumberBeChanged(cnum))
                {
                    CoursesRelated.addNewcourse(cname, cnum, t, crdt, cp, txtbk);
                    //Courses.updateCourseDepartment(temp2,cnum );
                }
                else if(t < 1 || t > 5)  // check the value of the term
                {
                    JOptionPane.showMessageDialog(null, "term should be from 1 to 5");
                }
                else if (crdt < 1 || crdt > 3)  // check the value of the credits 
                {
                    JOptionPane.showMessageDialog(null, "Credits should be from 1 to 3");
                }
                else if (cp < 7 || cp > 15 )
                {
                    JOptionPane.showMessageDialog(null, "Course Capacity must be between 7 and 15");
                }
                else if(CoursesRelated.canCourseNameBeChanged(cname) == false)
                {
                    JOptionPane.showMessageDialog(null, "There's a course with the same name");
                }
                else if(CoursesRelated.canCourseNumberBeChanged(cnum) == false)
                {
                    JOptionPane.showMessageDialog(null, "There's a course with the same Course Number");
                }
            }
             catch(NumberFormatException ex) //if it's not numbers
            {
                JOptionPane.showMessageDialog(null, "Please Enter Valid Values");
            }
        }
        
        
    }

    @FXML
    private void loadDashBoard(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("InstructorDashBoard.fxml"));
           root.getChildren().setAll(pane);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    RegistrationSystemProject.stage.setResizable(false);
    
    try {
            deptComboBox.getItems().addAll(CoursesRelated.fillDeptComboBox());
            prereqComboBox.getItems().addAll(CoursesRelated.filAllCourseslComboBox());
        } catch (SQLException ex) {
            Logger.getLogger(EditCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }    
    
}
