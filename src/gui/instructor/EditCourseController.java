/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.instructor;

import db.DatabaseConnection;
import functionality.CoursesRelated;
import gui.RegistrationSystemProject;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author shorouk
 */
public class EditCourseController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button updateCourseButton;
    @FXML
    private TextField courseNum;
    @FXML
    private TextField courseName;
    @FXML
    private TextField term;
    @FXML
    private TextField credits;
    @FXML
    private TextField textbook;
    @FXML
    private Button bk;
    @FXML
    private TextField cap;
    @FXML
    private ComboBox<String> selectCourseToEditComboBox;



    
    @FXML
    private void handelUbdateCourseButton(ActionEvent event) throws SQLException, IOException {
        
        int cnum = 0;
        String cname ="";
        int t = 0;
        int cp = 0;
        int crdt = 0;
        String txt =""; 
        
       
        
        // if any of them is empty 
        if ( courseNum.getText().isEmpty() || courseName.getText().isEmpty() ||
                term.getText().isEmpty() || cap.getText().isEmpty() 
                || credits.getText().isEmpty() || textbook.getText().isEmpty())
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
                txt = textbook.getText();
                if(t >= 1 && t <= 5 && crdt >= 1 && crdt <= 3 && CoursesRelated.canCourseCapBeIncreased(cp)                        
                    && CoursesRelated.canCourseNameBeChanged(cname) && CoursesRelated.canCourseNumberBeChanged(cnum) )
                {
                    CoursesRelated.updateThecourse(cname, cnum, t, crdt, cp, txt);
                }
                else if(t < 1 || t > 5)  // check the value of the term
                {
                    JOptionPane.showMessageDialog(null, "term should be from 1 to 5");
                }
                else if (crdt < 1 || crdt > 3)  // check the value of the credits 
                {
                    JOptionPane.showMessageDialog(null, "Credits should be from 1 to 3");
                }
                else if (CoursesRelated.canCourseCapBeIncreased(cp) == false)
                {
                    JOptionPane.showMessageDialog(null, "Course Capacity Can't be increased");
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



    @FXML
    private void handelSelectCourseToEdit(ActionEvent event) throws SQLException, IOException {
        String selection = selectCourseToEditComboBox.getValue();
        selection = selection.substring(0,selection.indexOf(" "));
        int num = Integer.parseInt(selection);
        CoursesRelated.fillEditCourse(num);
        courseNum.setText(Integer.toString(CoursesRelated.getCourseNumber()));
        courseName.setText(CoursesRelated.getCourseName());
        term.setText(Integer.toString(CoursesRelated.getTerm()));
        credits.setText(Integer.toString(CoursesRelated.getCredits()));
        textbook.setText(CoursesRelated.getTextbook());
        cap.setText(Integer.toString(CoursesRelated.getCap()));
        
      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         RegistrationSystemProject.stage.setResizable(false);
         
        try {
            selectCourseToEditComboBox.getItems().addAll(CoursesRelated.filAllCourseslComboBox());
        } catch (SQLException ex) {
            Logger.getLogger(EditCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
}
