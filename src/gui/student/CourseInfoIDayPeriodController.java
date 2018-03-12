/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.student;

import db.DatabaseConnection;
import functionality.CoursesObjects;
import functionality.CoursesRelated;
import functionality.Misc;
import gui.RegistrationSystemProject;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author shorouk
 */
public class CourseInfoIDayPeriodController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button bk;
    @FXML
    private TableView<CoursesObjects> coursesByDayAndPeriod;
    @FXML
    private TableColumn<CoursesObjects, String> course;
    @FXML
    private TableColumn<CoursesObjects, Integer> room;
    @FXML
    private ComboBox<String> dayMenue;
    @FXML
    private ComboBox<Integer> periodMenue;
    @FXML
    private Button ok;

    private ObservableList <CoursesObjects> table1;

    @FXML private void loadCoursesInfo2 (ActionEvent event) throws IOException {
               
        AnchorPane pane = FXMLLoader.load(getClass().getResource("CoursesInfo.fxml"));
        root.getChildren().setAll(pane);
     }
    

    
    
    
public void buildData1(String day , int period){
    
    table1 = FXCollections.observableArrayList();
    try{      
        Statement statement = DatabaseConnection.connection.createStatement(); 
        
        String st = "SELECT courses.courseName , collegeschedule.roomNumber"
                + " FROM collegeschedule JOIN courses"
                + " on courses.courseNumber = collegeschedule.courseNumber"
                + " WHERE day = '"+day+"' "
                + " and period = "+period;
        
        ResultSet result = statement.executeQuery(st);
               
        while(result.next()){
            CoursesObjects c = new CoursesObjects();
            c.setCourse(result.getString(1));
            c.setRoom (result.getInt(2));
            table1.add(c);                  
        }
        coursesByDayAndPeriod.setItems(table1);   
    }
    catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
}
    
    
     @FXML
    private void handelOk(ActionEvent event) {
        
        String d = dayMenue.getValue();
        int p = periodMenue.getValue();
        buildData1(d , p);
        
    }
    
    
    
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
    RegistrationSystemProject.stage.setResizable(false);
    
        try {
            dayMenue.getItems().addAll(Misc.fildaysComboBox());
            periodMenue.getItems().addAll(Misc.filperiodsComboBox());
            dayMenue.getSelectionModel().selectFirst();
            periodMenue.getSelectionModel().selectFirst();
            
            course.setCellValueFactory(new PropertyValueFactory<CoursesObjects, String>("course"));
            room.setCellValueFactory(new PropertyValueFactory<CoursesObjects, Integer>("room"));
            
        } catch (SQLException ex) {
            Logger.getLogger(CourseInfoIDayPeriodController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }    

   
}
