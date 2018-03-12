
package functionality;

import db.DatabaseConnection;
import static functionality.ProfilesRelated.filltheProfile;
import static functionality.ProfilesRelated.id;
import gui.instructor.EditCourseController;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import static org.eclipse.persistence.jpa.rs.util.JPARSLogger.exception;

/**
 *
 * @author shorouk
 */

public class CoursesRelated {

   
    private static int courseNumber=0;
    private static String courseName="";
    private static int term=0;
    private static int credits=0;
    private static int cap=0;
    private static String textbook="";
    private static String prerequisite="";
    
    private static String day="";
    private static int period=0;
    private static int room=0;
    
    private static int roomCap=0 ;
 
    private static int dept =0 ;
    
    
    public static String getPrerequisite() {
        return prerequisite;
    }

    public static void setPrerequisite(String prerequisite) {
        CoursesRelated.prerequisite = prerequisite;
    }
    

    public static int getCap() {
        return cap;
    }

    public static void setCap(int cap) {
        CoursesRelated.cap = cap;
    }

    public static int getCourseNumber() {
        return courseNumber;
    }

    public static void setCourseNumber(int courseNumber) {
        CoursesRelated.courseNumber = courseNumber;
    }

    public static String getCourseName() {
        return courseName;
    }

    public static void setCourseName(String courseName) {
        CoursesRelated.courseName = courseName;
    }

    public static int getTerm() {
        return term;
    }

    public static void setTerm(int term) {
        CoursesRelated.term = term;
    }

    public static int getCredits() {
        return credits;
    }

    public static void setCredits(int credits) {
        CoursesRelated.credits = credits;
    }

    public static String getTextbook() {
        return textbook;
    }

    public static void setTextbook(String textbook) {
        CoursesRelated.textbook = textbook;
    }

    public static String getDay() {
        return day;
    }

    public static void setDay(String day) {
        CoursesRelated.day = day;
    }

    public static int getPeriod() {
        return period;
    }

    public static void setPeriod(int period) {
        CoursesRelated.period = period;
    }

    public static int getRoom() {
        return room;
    }

    public static void setRoom(int room) {
        CoursesRelated.room = room;
    }

    public static int getRoomCap() {
        return roomCap;
    }

    public static void setRoomCap(int roomCap) {
        CoursesRelated.roomCap = roomCap;
    }
    

    private static ArrayList<String> options  = new ArrayList<>() ;
    private static ArrayList<String> deptOptions  = new ArrayList<>() ;
    private static ArrayList<String> coursesByInstructor  = new ArrayList<>() ;
    private static ArrayList<String> allDepartments  = new ArrayList<>() ;
    private static ArrayList<String> allInstructors  = new ArrayList<>() ;
    private static ArrayList<String> dropCourse  = new ArrayList<>() ;
    private static ArrayList<String> registerCourse  = new ArrayList<>() ;
    
    //fill a list of all courses avilable
    public static ArrayList<String> filAllCourseslComboBox () throws SQLException
    {
        Statement statement = DatabaseConnection.connection.createStatement();
        String st = "select courseNumber , courseName from courses;"; 
        ResultSet result = statement.executeQuery(st);
        options.clear();
        while(result.next())
        {
            String option = result.getString(1)+"  "+result.getString(2);
            options.add(option);  
        }

        return options;
    }
    
     public static ArrayList<String> fillregisterCourseComboBox () throws SQLException
    {
       registerCourse.clear();
        Statement statement = DatabaseConnection.connection.createStatement();
        String st = "SELECT courses.courseNumber , courses.courseName FROM courses"
                + " WHERE courseNumber IN"
                + " (SELECT courseNumber FROM coursesdepartments"
                + " WHERE deptId IN"
                + " (SELECT deptId FROM profilesdepartments WHERE id ="+ ProfilesRelated.id +" ))";
        ResultSet result = statement.executeQuery(st);
        
        while(result.next())
        {
            String option = result.getString(1)+" "+result.getString(2);
            registerCourse.add(option);  
        }

        return registerCourse;
    }
    
    public static ArrayList<String> filldropCourseComboBox () throws SQLException
    {
       dropCourse.clear();
        Statement statement = DatabaseConnection.connection.createStatement();
        String st = "select courses.courseName FROM takes"
                + " JOIN courses JOIN profiles"
                + " ON courses.courseNumber = takes.courseNumber"
                + " and profiles.id = takes.id"
                + " WHERE takes.semester = \"spring 17\" AND profiles.id ="+ ProfilesRelated.id;
        ResultSet result = statement.executeQuery(st);
        
        while(result.next())
        {
            String option = result.getString(1);
            dropCourse.add(option);  
        }

        return dropCourse;
    }
    
    
    public static ArrayList<String> fillAllDepartmentsComboBox () throws SQLException
    {
        allDepartments.clear();
        Statement statement = DatabaseConnection.connection.createStatement();
        String st = "select deptName from departments;"; 
        ResultSet result = statement.executeQuery(st);
        while(result.next())
        { 
            allDepartments.add(result.getString(1));  
        }
        return allDepartments;
        
    }
    
    
    
    public static ArrayList<String> fillAllInstructors () throws SQLException
    {
        allInstructors.clear();
        Statement statement = DatabaseConnection.connection.createStatement();
        String st = "select firstName, lastName from profiles where id <200;"; 
        ResultSet result = statement.executeQuery(st);
        
        while(result.next())
        { 
            allInstructors.add(result.getString(1) +" "+result.getString(2));  
        }
        return allInstructors;
        
    }
    
    
    /*fills the departmnts of the instructor when adding a new course so they can add it to ONLY their depts*/
     public static ArrayList<String> fillDeptComboBox () throws SQLException
    {
        deptOptions.clear();
        Statement statement = DatabaseConnection.connection.createStatement();
        
        String st1 = "SELECT deptName FROM departments where deptId IN ( SELECT deptId FROM profilesdepartments where id =" +ProfilesRelated.id+ ")";
        ResultSet result = statement.executeQuery(st1);
        while (result.next())
        {
            deptOptions.add(result.getString("deptName"));
        }
        return deptOptions;
    }
    
    /*gets the list of courses tought by an Instructor */
   public static ArrayList<String> fillCoursesByInstructorCombobox () throws SQLException
   {
      coursesByInstructor.clear();
      Statement statement = DatabaseConnection.connection.createStatement();
      String st ="SELECT courseNumber , courseName FROM courses where courseNumber "
              + "IN (SELECT courseNumber from teaches WHERE id = "+ProfilesRelated.id +" )";
      ResultSet result = statement.executeQuery(st); 
       while (result.next())
        {
            coursesByInstructor.add(result.getString("courseNumber")+"  "+result.getString("courseName"));
        }
      return coursesByInstructor ;
   }
    
    /* fills the text fields in the GUI  after the user selects  a course to edit */
     public static void fillEditCourse (int courseNumber) throws SQLException, IOException
    {
        
        Statement statement = DatabaseConnection.connection.createStatement();
        String st = "Select * from courses "
                + " where courseNumber =" ;

        ResultSet resultSet = statement.executeQuery(st+courseNumber+";");
        
        resultSet.next();
        setCourseNumber(Integer.parseInt(resultSet.getString(1)));
        setCourseName(resultSet.getString(2));
        setTerm(Integer.parseInt(resultSet.getString("term")));
        setCredits(Integer.parseInt(resultSet.getString("credits")));
        setCap(Integer.parseInt(resultSet.getString("cap")));
        setTextbook(resultSet.getString("textbook"));

    }
     
     /*gets the room , room cap , day , period of the course */
     public static void  getCoursesRoomandTime () throws SQLException
     {
        Statement statement = DatabaseConnection.connection.createStatement();
        String st = "SELECT day , period , roomNumber FROM collegeschedule WHERE courseNumber =";
        ResultSet resultSet = statement.executeQuery(st+courseNumber+";");
        
        resultSet.next(); 
         
        
         setDay(resultSet.getString("day"));
         setPeriod(resultSet.getInt("period"));
         setRoom(resultSet.getInt("roomNumber"));      
         
         String st2 = "SELECT capacity from classrooms where roomNumber =";
         ResultSet resultSet2 = statement.executeQuery(st2+room+";");
         resultSet2.next();
         setRoomCap(resultSet2.getInt("capacity"));
     }
     
     /*updates the course record in the database*/
     public static void updateThecourse (String cname , int cnum , int t , int crdt , int cap, String txt) throws SQLException, IOException
     {
         Statement statement = DatabaseConnection.connection.createStatement();
         
         String st = "UPDATE courses SET courseName = \""+cname+"\" ";
         st += ", courseNumber = "+cnum;
         st+=" , term = "+t;
         st+=" , cap = "+cap;
         st+=" , credits = "+crdt;
         st+=" , textbook = \""+txt+"\"";
         st+=" WHERE courseNumber = "+courseNumber;
         st+=" ;";
         
         statement.executeUpdate(st);
         JOptionPane.showMessageDialog(null, "The Course has been Succesfully Updated");
         filAllCourseslComboBox ();        
     }
     
     /*adds new course record in the db*/
      public static void addNewcourse (String cname , int cnum , int t , int crdt , int cap, String txt) throws SQLException, IOException
     {
         Statement statement = DatabaseConnection.connection.createStatement();
         
         String st = "Insert Into courses (courseNumber , courseName , term , credits , cap , textbook) values ( ";
         st += cnum+" , '"+cname+"' , "+t+" , "+crdt+" , "+cap+" , '"+txt+"' );" ;
         statement.executeUpdate(st);
         JOptionPane.showMessageDialog(null, "The Course has been Succesfully Added");
               
     }
     
      
      /*removes record from takes table*/
      public static void dropACourse (String courseName) throws SQLException
      {
          Statement statement = DatabaseConnection.connection.createStatement();
          String st = "DELETE FROM takes WHERE courseNumber IN "
                  + "( SELECT courseNumber FROM courses"
                  + " WHERE courseName = '"+courseName+"')"
                  + " and id = "+ ProfilesRelated.id;
         statement.executeUpdate(st);
          JOptionPane.showMessageDialog(null, "The Course has been Succesfully dropped");
      }
    
      public static boolean isThereARoomForANewStudent () throws SQLException
      {
          //Make sure There's room for the new Student
         Statement statement = DatabaseConnection.connection.createStatement();
         String st = "SELECT COUNT(id) FROM takes WHERE courseNumber =" + courseNumber
                 + " AND semester =\"spring 17\"";
         ResultSet resultSet = statement.executeQuery(st);
         resultSet.next(); 
         int currentCountOfStudents = resultSet.getInt(1);
         
         if ((currentCountOfStudents +1) > cap  || (currentCountOfStudents +1) > roomCap )
         {
             JOptionPane.showMessageDialog(null, "You Can't register this course it's Full ");
             return false;
         }
         else return true ;
      }
      
      public static boolean didTheStudentFinishThePre () throws SQLException
      {
         Statement statement = DatabaseConnection.connection.createStatement();
          String st2 ="SELECT courseNumberPrerequisite FROM prerequisite WHERE courseNumberCourse ="+courseNumber;
         ResultSet resultSet = statement.executeQuery(st2);
         int prereqN ;
         try{ 
             resultSet.next(); 
            prereqN = resultSet.getInt(1);
         }
         catch (Exception ex)  //the course has no prerequisite
        {
            
            return true;
        }
         
         
         String st3 = "Select COUNT(courseNumber) FROM takes "
                 + " WHERE id = " + ProfilesRelated.id
                 + " AND courseNumber = "+prereqN ;
         resultSet = statement.executeQuery(st3);
         resultSet.next(); 
         int taken = resultSet.getInt(1);
         
         if(taken == 0)
         {
              JOptionPane.showMessageDialog(null, "You Can't register this course. Finish its preriquisite first  ");
              return false;
         }
         else
         {
              String st4 = "Select COUNT(courseNumber) FROM takes "
                 + " WHERE id = " + ProfilesRelated.id
                 + " AND courseNumber = "+prereqN
                 + " AND semester = 'spring 17' ";
             
            resultSet = statement.executeQuery(st4);
            resultSet.next(); 
            int takenThisSem = resultSet.getInt(1);
            if (takenThisSem == 1)
            {
                JOptionPane.showMessageDialog(null, "You Can't register this course. Finish its preriquisite first  ");
                return false;
            }
            else return true ;
         }
         
      }
      
      
    public static boolean isTheRoomOccupied () throws SQLException{
         
         Statement statement = DatabaseConnection.connection.createStatement();
         String st;
         st = "select COUNT(courseNumber) from collegeschedule"
                 + " WHERE day = '" +day+"'" 
                 + " and period = "+period
                 + " and roomNumber = "+room;
         ResultSet resultSet = statement.executeQuery(st);
         resultSet.next(); 
         int count = resultSet.getInt(1);
         
         if(count == 1){
             JOptionPane.showMessageDialog(null, "The room is alread occupied chose another one  ");
              return false;
         }
         else
             return true;
     }
             
      
      
      
      public static boolean isThereATimeConflict (String who) throws SQLException
      {
         Statement statement = DatabaseConnection.connection.createStatement();
         String st;
         if (who.matches("student"))
         {
             st ="SELECT day , period , roomNumber"
                  + " FROM collegeschedule "
                  + "where courseNumber in (SELECT courseNumber"
                  + " FROM takes WHERE id = "+ProfilesRelated.id 
                  + " and semester = \"spring 17\")" ;
         }
         else
         {
             st ="SELECT day , period , roomNumber"
                  + " FROM collegeschedule "
                  + "where courseNumber in (SELECT courseNumber"
                  + " FROM teaches WHERE id = "+ProfilesRelated.id 
                  + " and semester = \"spring 17\")" ;
             
         }
          
 
         ResultSet resultSet = statement.executeQuery(st);
         
         while(resultSet.next())
         {
             String d; 
             int r , p ;
             d = resultSet.getString(1);
             p = resultSet.getInt(2);
             r = resultSet.getInt(3);
             if ( d.contains(day) &&  p == period)
             {
                JOptionPane.showMessageDialog(null, "You Can't add this course. Ther's a time conflict ");
                return false;
             }

         }
         return true ;
         
      }
      
      
      
      public static boolean canTheStudentRegisterAnotherCourse () throws SQLException
      {
          Statement statement = DatabaseConnection.connection.createStatement();
          String st ="SELECT count(courseNumber) from takes"
                  + " WHERE id ="+ ProfilesRelated.id
                  + "  and semester ='spring 17'";
         ResultSet resultSet = statement.executeQuery(st);
         resultSet.next(); 
         int counter = resultSet.getInt(1);
         if (counter >= 3)
         {
             JOptionPane.showMessageDialog(null, "You Can't register this course.You've registered three courses already ");
             return false;
         }
         else
             return true;
          
      }
      
      
      public static void registerCourse (int courseNumber) throws SQLException, IOException
      {
         boolean a = true;
          fillEditCourse(courseNumber);
         
         try{
              getCoursesRoomandTime ();
         }
         catch (Exception ex)
         {
             JOptionPane.showMessageDialog(null, "The Courseis not avilable this semester");
             a = false;
             roomCap = 10;
         }
        
         
         boolean r = isThereARoomForANewStudent ();
         boolean p = didTheStudentFinishThePre () ;
         boolean c = isThereATimeConflict ("student") ; 
         boolean n = canTheStudentRegisterAnotherCourse ();
         boolean o= false;
         
         if (r && p && c && n && a)
         {
            Statement statement = DatabaseConnection.connection.createStatement();
            String st = "Insert Into takes (id , courseNumber , semester , grade) values ( ";
            st += ProfilesRelated.id+" , '"+courseNumber+"' ,  'spring 17' , 'U' );" ;
            try{
                statement.executeUpdate(st);
            }
            catch (Exception ex )
            {
                 JOptionPane.showMessageDialog(null, "You've alrady passed this course");
                 o = true;
            }
            if (o == false)
            JOptionPane.showMessageDialog(null, "The Course has been Succesfully registered");
         }

         
      }
      
    public static void teachCourse(int num, String d, int r, int p) throws SQLException {
        
        setDay(d);
        setRoom(r);
        setPeriod(p);
        
        boolean c = isThereATimeConflict ("instructor") ; 
        boolean o = isTheRoomOccupied ();
        if(c && o)
        {
            Statement statement = DatabaseConnection.connection.createStatement();
            String st = "Insert Into teaches (id , courseNumber , semester ) values ( " 
                    +ProfilesRelated.id +" , "+num+" , 'spring 17')";
            try{
                statement.executeUpdate(st);
            }
            catch (Exception ex)
            {
                // the instructo already teaches the course so no need for updating the table teach
            }
            
            
            String st2 ="Insert Into collegeschedule (day , period , roomNumber , courseNumber) values ( '"
                    +d+"' , "+p+" , "+r+" , "+num+" )";
            System.out.println(st2);
            statement.executeUpdate(st2);
             JOptionPane.showMessageDialog(null, "The Course has been Succesfully added to your list");
        }
        
    }
      
      
     /*
     public static void updateCourseDepartment (String deptname , int course) throws SQLException
     {
         int deptid;
         Statement statement = DatabaseConnection.connection.createStatement();
         ResultSet r = statement.executeQuery("Select deptId from departments where deptName = ' "+deptname+" '");
         r.next();
         deptid = r.getInt("deptId");
         String st = "Insert Into coursesdepartments ( courseNumnber , courseNumnber ) values ( ";
         st+= course+" , "+deptid+" ;";
         System.out.println(st);
         statement.executeUpdate(st);
         
     }
     */
     
     /*Decide wether a course cap can be increased according to the room it's in*/
     public static boolean canCourseCapBeIncreased (int newCourseCap ) throws SQLException
     {
         getCoursesRoomandTime ();
         if (newCourseCap <= getRoomCap() )
             return true ;
         else
             return false;
     }
     
     /*Decides wether the course can be renamed  */
     public static boolean canCourseNameBeChanged (String newCourseName) throws SQLException
     {
        Statement statement = DatabaseConnection.connection.createStatement();
        String st = "SELECT courseName from courses where courseName = '"+newCourseName+"'";
        
        ResultSet resultSet = statement.executeQuery(st);
        
        
        if (resultSet.next()) // if there's a course with the same name
        {
            if (newCourseName.matches(getCourseName()) ) //the user didn't change the course name
            {
                return true; 
            }
            else
                return false;  // there exist  course with the same name already
        }
        else  //the user entered a new name for the course
            return true;
     }
     
     /*Decides wether the course number  be changed */
     public static boolean canCourseNumberBeChanged (int newCourseNumber) throws SQLException
     {
         Statement statement = DatabaseConnection.connection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT courseNumber from courses where courseNumber=" +newCourseNumber+";");
        if ( resultSet.next() )  //if ther's a course with the same id in the text field
        {
            if(newCourseNumber == getCourseNumber())  //the user didn't change the course id
            {
                return true;
            }
            else return false;  // there exist  course with the same id already
        }
        else return true;   //the user entered a new id for the course
     }
     
     public static void graduate () throws SQLException
     {
         int id = ProfilesRelated.id ;

        Statement statement = DatabaseConnection.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(grade) FROM `takes` WHERE id ="+id+ "  and grade LIKE 'U'");
        resultSet.next();
        int not = resultSet.getInt(1);
        resultSet = statement.executeQuery("SELECT COUNT(grade) FROM `takes` WHERE id ="+id );
        resultSet.next();
        int all = resultSet.getInt(1);
        int total = all - not;
        if (total < 10)
        {
            JOptionPane.showMessageDialog(null, "You have finished less than 10 courses\n Your request is denied");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Your request has been forwarded to your advisor");
        }
            
     }



     
}
