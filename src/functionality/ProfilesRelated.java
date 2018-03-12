/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionality;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import db.DatabaseConnection;


public class ProfilesRelated {
    public static String firstName ;
    public static String lastName;
    public static String adress;
    public static String userName;
    public static String password;
    public static String idString;
    public static String department;
    public static int id;
    public static Image picture ;

    
    public static void filltheProfile (int id) throws SQLException, IOException
    {
        ProfilesRelated.id = id;
        Statement statement = DatabaseConnection.connection.createStatement();
        String st = "Select profiles.firstName , profiles.lastName , profiles.localAdress ,"
                + " profiles.userName , profiles.password , departments.deptName"
                + " from profiles JOIN profilesdepartments ON profiles.id = profilesdepartments.id"
                + " JOIN departments on profilesdepartments.deptId = departments.deptId";
        
        ResultSet resultSet = statement.executeQuery(st+" where profiles.id="+id+";");
        
        Statement st2 = DatabaseConnection.connection.createStatement();
        ResultSet r2 = st2.executeQuery("select picture from profiles where id="+id+";") ;
        r2.next();
        Blob blob = r2.getBlob(1);
        InputStream in = blob.getBinaryStream();  
        BufferedImage bufferedImage = ImageIO.read(in);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);

        resultSet.next();
        firstName = resultSet.getString(1);
        lastName = resultSet.getString(2);
        adress = resultSet.getString(3);
        userName = resultSet.getString(4);
        password = resultSet.getString(5);
        idString = Integer.toString(id);
        department = resultSet.getString(6);
        picture = image ;
   
    }
    
    
     public static void updateTheProfile (String fn , String ln , String a , String u , String p) throws SQLException, IOException
     {
         Statement statement = DatabaseConnection.connection.createStatement();
         
         statement.executeUpdate("UPDATE profiles SET firstName = '"+fn+"' , lastName = '"+ln+"' , localAdress ='"
         +a+ "' , userName ='"+ u+"' , password ='"+p+"' WHERE id ="+id+";");
         JOptionPane.showMessageDialog(null, "Your Profile has been Succesfully Updated");
         filltheProfile (id);
         
     }
    
}
