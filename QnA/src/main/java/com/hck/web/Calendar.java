/*********************************************************************************************
**********************************************************************************************
This Java servlet file should be placed in the WEB-INF/classes directory used by your
servlet engine (e.g., Tomcat or Resin), or in a subdirectory thereof.  If in a subdirectory,
use the package statement below to identify the directory.  Else, delete the package line.

The statements here simply modify variables that control the look and feel of the calendar.
Also, if the MySQL Events table doesn't already exist, this servlet creates it.
The main WebCalendar Java file is contained in the jar file webcalendar-1.12-src.jar, which
should be placed in the lib directory or elsewhere in the Java CLASSPATH used by the servlet
engine.

To compile this servlet, try the following (may need to be adjusted for your operating system
and configuration).
%> cd /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/
%> CLASSPATH=$CLASSPATH:/usr/local/tomcat/lib/webcalendar-1.12-src.jar
%> EXPORT CLASSPATH
%> javac Demo/Calendar.java

If successful, the calendar should now be accessible at the URL
http://yourserver.com/servlet/Demo.Calendar

You can run multiple WebCalendars by changing the subdirectory (which must match the package 
name) or the Java filename (which must match the class name) or both. 
**********************************************************************************************
*********************************************************************************************/

package com.hck.web;  // name of subdirectory holding this file (e.g., ...WEB-INF/classes/Demo)

import java.text.SimpleDateFormat;
import java.sql.*;

public class Calendar extends BaseActivityCal {  // class may be renamed 
                                             //(e.g. ClubCalendar extends WebCalendar)
  public Calendar(){   // Constructor must have same name as the class name above
  // these parameters may be changed to customize the look and feel of the calendar:
    viewOnly = false; // true to disallow changes (for public viewing only)
    eventsDatabase = "jdbc:mysql://127.0.0.1:3307/test"; // location of the MySQL database
    mySQLUser = "root";  // internal MySQL database user/password
    mySQLPass = "";
    authMessage = "For this demo version, the secret phrase is: KilroyWasHere";
    secretPassPhrase = "KilroyWasHere"; // required by users to add,edit,delete events
    cookieMaxAge = 1; // week
    titleBar = "L&T Digital Support Leave Calendar "; // label shown on the top bar of the Calendar
    cTFontsColor = "#FFFF00";  // yellow
    cTopBarColor = "#0000AA";  // dark blue
    cDayBarColor = "#0000AA";  // dark blue
    cOutDayColor = "#DDDDFF";  // light blue
    cTodaysColor = "#E8E8E8";  // light gray
    cBGCellColor = "#FFFFFF";  // white
    cDtFontColor = "#000000";  // black
    //dfTimeField = new SimpleDateFormat("HH:mm");   // or try "h:mma" for AM/PM format
    dfDateField = new SimpleDateFormat("M/d/yy");  // or try "d-M-yyyy" or "dMMMyyyy"

    try {
    	 System.out.println(mySQLPass+"^^^^--harish-^^^^"+mySQLUser);
    
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      System.out.println(DriverManager.getConnection(eventsDatabase,mySQLUser,mySQLPass));
      Connection conn = DriverManager.getConnection(eventsDatabase,mySQLUser,mySQLPass);
      System.out.println("--conn--"+conn);
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("CREATE TABLE Events (EventID INT PRIMARY KEY AUTO_INCREMENT,Sdate "
      + "DATETIME,Edate DATETIME,Description TEXT,Notes TEXT,User VARCHAR(50),Flagged INT)");
      System.out.println("harish--3307"+conn);
    }
    catch (Exception e) {
    }
  }
}  
