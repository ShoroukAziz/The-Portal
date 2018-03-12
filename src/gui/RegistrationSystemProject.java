package gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import db.DatabaseConnection;
import functionality.CoursesRelated;

public class RegistrationSystemProject extends Application {

    public static Stage stage;

    
    @Override
    public void start(Stage stage) throws Exception {
        RegistrationSystemProject.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (DatabaseConnection.connect() == 0) 
            launch(args);
        
                
    }
    
}