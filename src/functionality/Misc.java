/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionality;

import db.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author shorouk
 */
public class Misc {
     private static ArrayList<String> days  = new ArrayList<>() ;
     private static ArrayList<Integer> periods  = new ArrayList<>() ;
     private static ArrayList<Integer> rooms  = new ArrayList<>() ;

    
    public static ArrayList<String> fildaysComboBox () throws SQLException
    {   
         days.clear();
         days.add("Saturday");
         days.add("Sunday");
         days.add("Monday");
         days.add("Tuesday");
         days.add("Wednesday");
        return days;
    }
    
    public static ArrayList<Integer> filperiodsComboBox () throws SQLException
    {   
        periods.clear();
        for(int i = 1 ; i < 13 ; i++ )
        {
            periods.add(i);
        }
        return periods;
    }
    
    public static ArrayList<Integer> fillRoomsComboBox () throws SQLException
    {
       rooms.clear();
        Statement statement = DatabaseConnection.connection.createStatement();
        String st = "Select roomNumber from classrooms";
        ResultSet result = statement.executeQuery(st);
        
        while(result.next())
        {
            int option = result.getInt(1);
            rooms.add(option);  
        }
        return rooms;
    }
    

    
}
