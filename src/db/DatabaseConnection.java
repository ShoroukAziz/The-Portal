
package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author shorouk
 */
public class DatabaseConnection {
    public static Connection connection ;
    public static int connect() 
    {
        String host = "jdbc:mysql://localhost/registrationsystem";
        try {
             connection = DriverManager.getConnection(host,"root","");
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        System.out.println("Connected to DB !!");
        return 0;
    }
    
}
