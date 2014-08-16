package com.hck.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BaseActivityCal
 */
public class BaseActivityCal extends HttpServlet {
	  protected boolean viewOnly = false;
	  protected String eventsDatabase = "jdbc:mysql://127.0.0.1:3307/test";
	  protected String mySQLUser = "root";
	  protected String mySQLPass = "";
	  protected String secretPassPhrase = "KilroyWasHere";
	  protected String authMessage = "Please see the owner of this calendar to get the secret phrase.";
	  protected int cookieMaxAge = 2;
	  protected String titleBar = "Demo WebCalendar for ";
	  protected String cTFontsColor = "#FFFF00";
	  protected String cTopBarColor = "#0000AA";
	  protected String cDayBarColor = "#0000FF";
	  protected String cOutDayColor = "#0000AA";
	  protected String cTodaysColor = "#E0E0E0";
	  protected String cBGCellColor = "#FFFFFF";
	  protected String cDtFontColor = "#000000";
	  protected SimpleDateFormat dfTimeField = new SimpleDateFormat("HH:mm");
	  protected SimpleDateFormat dfDateField = new SimpleDateFormat("M/d/yy");
	  protected String time0000 = this.dfTimeField.format(new Date(25200000L));
	  protected String time2359 = this.dfTimeField.format(new Date(111540000L));
	  protected SimpleDateFormat dfMonthYear = new SimpleDateFormat("MMMM yyyy");
	  protected SimpleDateFormat dfMonthName = new SimpleDateFormat("MMMM");

	  String thisServletURI = null;
	  protected String mySqlJdbcDriver = "com.mysql.jdbc.Driver";
	  protected SimpleDateFormat dfMySQLDate = new SimpleDateFormat("yyyy-MM-dd");
	  protected SimpleDateFormat dfMySQLTime = new SimpleDateFormat("HH:mm");
	  protected SimpleDateFormat dfDayOfMonth = new SimpleDateFormat("d");

	  public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
	    throws ServletException, IOException
	  {
	    paramHttpServletResponse.setContentType("text/html");
	    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
	    this.thisServletURI = "/QnA"+paramHttpServletRequest.getServletPath();

	    Calendar localCalendar = Calendar.getInstance();
	    Date localDate = new Date();

	    if (paramHttpServletRequest.getParameter("date") != null) {
	      try {
	        localDate = this.dfDateField.parse(paramHttpServletRequest.getParameter("date"));
	        localCalendar.setTime(localDate);
	      } catch (ParseException localParseException) {
	        localPrintWriter.println("Unable to parse date parameter. Using date=" + this.dfDateField.format(localCalendar.getTime()) + "<br>");
	      }

	    }

	    localCalendar.set(5, 1);

	    localPrintWriter.println(htmlHeader());

	    if (paramHttpServletRequest.getParameter("form") == null) {
	      localPrintWriter.println(tableHeader(localCalendar));
	      localPrintWriter.println(tableCells(localCalendar));
	      localPrintWriter.println(tableFooter(localCalendar));
	    }
	    else if (paramHttpServletRequest.getParameter("form").equals("help")) {
	      localPrintWriter.println(instructions());
	    }
	    else if (this.viewOnly) {
	      localPrintWriter.println(declineRequest());
	    }
	    else if (allowEdit(paramHttpServletRequest, paramHttpServletResponse)) {
	      if (paramHttpServletRequest.getParameter("form").equals("new")) {
	        localPrintWriter.println(newEventForm(paramHttpServletRequest.getParameter("value")));
	      }
	      else if (paramHttpServletRequest.getParameter("form").equals("revise"))
	        localPrintWriter.println(reviseEventForm(paramHttpServletRequest.getParameter("value")));
	    }
	    else {
	      localPrintWriter.println(promptForPassword(paramHttpServletRequest));
	    }localPrintWriter.println(htmlFooter());
	  }

	  String htmlHeader()
	  {
	    return "<HTML><head><meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'><meta name='Author' content='Harish Kaparwan'><title>WebCalendar</title><SCRIPT LANGUAGE=JavaScript>function PopupWindow(url,form,value){window.open(url+'?form='+form+'&value='+value,'EventControlPanel','width=370,height=370,dependent,resizable');}</SCRIPT></head>";
	  }

	  String instructions()
	  {
	    StringBuffer localStringBuffer = new StringBuffer("<body>");
	    localStringBuffer.append("<h3>WebCalendar Program</h3><FONT SIZE=-1><OL><LI>To view the previous or next month, click the arrows in the corners of the calendar.<LI>To add a new event to the calendar, click the date numeral for the date desired. You may be prompted for a username and password. A popup form should appear. All fields are required except 'Notes' and 'Description'. Depending on your browser settings, you might have to 'Refresh' the main calendar page to see the change.<LI>If you schedule an event that conflicts with another event, you will receive a warning, but the conflicting event will be scheduled anyway. It is the user's responsiblity to resolve conflicts.<LI>To modify or delete an event, click the blue time numerals for the event you want to change.</OL><HR>Copyright &copy; 2001, <a href=mailto:Chuck.Wight@utah.edu>Harish Kaparwan</a>.<BR>The source code for this Java servlet is distributed as a <a href=http://sourceforge.net/projects/javawebcalendar/>free download</a>,  with NO WARRANTY, under the terms of the <a href='http://www.gnu.org/copyleft/gpl.html' onClick=javascript:opener.document.location='http://www.gnu.org/copyleft/gpl.html';window.close();>GNU General Public License</a>.<BR></FONT>");

	    return localStringBuffer.toString();
	  }

	  String declineRequest() {
	    StringBuffer localStringBuffer = new StringBuffer("<body>");
	    localStringBuffer.append("<h3>Sorry!</h3>The owner/administrator of this WebCalendar servlet has configured it so that you may view the events, but not insert, edit or delete them from the database.  If you are an authorized user of this calendar, then there is probably another version of the servlet located in a password-protected directory.  You should use the other servlet to make any desired changes.");

	    localStringBuffer.append("<FORM><INPUT TYPE=BUTTON VALUE='  OK  ' onClick=javascript:window.close();></FORM>");
	    return localStringBuffer.toString();
	  }

	  String tableHeader(Calendar paramCalendar) {
	    Calendar localCalendar1 = (Calendar)paramCalendar.clone(); localCalendar1.add(2, 1);
	    Calendar localCalendar2 = (Calendar)paramCalendar.clone(); localCalendar2.add(2, -1);
	    return "<body><TABLE BORDER=1 CELLSPACING=0 CELLPADDING=0 WIDTH=100%>  <TR>  <TD BGCOLOR=" + this.cTopBarColor + " ALIGN=CENTER COLSPAN=7>" + "    <TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0>" + "    <TR ALIGN=CENTER>" + "      <TD><A HREF=" + this.thisServletURI + "?date=" + this.dfDateField.format(localCalendar2.getTime()) + "><FONT SIZE=-1 COLOR=" + this.cTFontsColor + "><&#151;&nbsp;" + this.dfMonthName.format(localCalendar2.getTime()) + "</FONT></A></TD>" + "      <TD><FONT SIZE=+1 COLOR=" + this.cTFontsColor + "><B>" + this.titleBar + this.dfMonthYear.format(paramCalendar.getTime()) + "</B></FONT></TD>" + "      <TD><A HREF=" + this.thisServletURI + "?date=" + this.dfDateField.format(localCalendar1.getTime()) + "><FONT SIZE=-1 COLOR=" + this.cTFontsColor + ">" + this.dfMonthName.format(localCalendar1.getTime()) + "&nbsp;&#151;></FONT></A></TD>" + "    </TR>" + "    </TABLE>" + "  </TD>" + "  </TR>" + "  <TR ALIGN=CENTER BGCOLOR=" + this.cDayBarColor + ">" + "  <TD WIDTH=14%><FONT COLOR=" + this.cTFontsColor + "><B>Sun</B></FONT></TD>" + "  <TD WIDTH=14%><FONT COLOR=" + this.cTFontsColor + "><B>Mon</B></FONT></TD>" + "  <TD WIDTH=14%><FONT COLOR=" + this.cTFontsColor + "><B>Tue</B></FONT></TD>" + "  <TD WIDTH=14%><FONT COLOR=" + this.cTFontsColor + "><B>Wed</B></FONT></TD>" + "  <TD WIDTH=14%><FONT COLOR=" + this.cTFontsColor + "><B>Thu</B></FONT></TD>" + "  <TD WIDTH=14%><FONT COLOR=" + this.cTFontsColor + "><B>Fri</B></FONT></TD>" + "  <TD WIDTH=14%><FONT COLOR=" + this.cTFontsColor + "><B>Sat</B></FONT></TD>" + "  </TR>";
	  }

	  String tableCells(Calendar paramCalendar)
	  {
	    StringBuffer localStringBuffer = new StringBuffer();

	    int i = paramCalendar.get(2);
	    int j = 1;

	    Calendar localCalendar1 = Calendar.getInstance();
	    Calendar localCalendar2 = (Calendar)paramCalendar.clone();
	    localCalendar2.set(5, localCalendar2.getActualMaximum(5));
	    localCalendar2.add(5, 7 - localCalendar2.get(7));

	    paramCalendar.add(5, 1 - paramCalendar.get(7));

	    String str1 = this.dfMySQLDate.format(paramCalendar.getTime());
	    String str2 = this.dfMySQLDate.format(localCalendar2.getTime());
	    ResultSet localResultSet = null;
	    try
	    {
	      Class.forName(this.mySqlJdbcDriver).newInstance();
	      Connection localConnection = DriverManager.getConnection(this.eventsDatabase, this.mySQLUser, this.mySQLPass);
	      Statement localStatement = localConnection.createStatement();

	      String str3 = "SELECT * FROM Events WHERE TO_DAYS(Sdate) >= TO_DAYS('" + str1 + "')" + "  AND TO_DAYS(Sdate) <= TO_DAYS('" + str2 + "')" + " ORDER BY Sdate,Edate";

	      localResultSet = localStatement.executeQuery(str3);
	      if (!localResultSet.next()) localResultSet = null; 
	    }
	    catch (Exception localException) {
	      return localException.getMessage();
	    }
	    do
	    {
	      localStringBuffer.append("<TR BORDER=1 VALIGN=TOP>");
	      for (int k = 0; k < 7; k++) {
	        localStringBuffer.append("<TD HEIGHT=100 WIDTH=\"14%\"");
	        if (paramCalendar.get(2) != i) localStringBuffer.append(" BGCOLOR=" + this.cOutDayColor);
	        else if (this.dfDateField.format(paramCalendar.getTime()).equals(this.dfDateField.format(localCalendar1.getTime()))) localStringBuffer.append(" BGCOLOR=" + this.cTodaysColor); else
	          localStringBuffer.append(" BGCOLOR=" + this.cBGCellColor);
	        localStringBuffer.append("><A HREF=# onClick=javascript:PopupWindow('" + this.thisServletURI + "','new','" + this.dfDateField.format(paramCalendar.getTime()) + "');><FONT COLOR=" + this.cDtFontColor + " SIZE=-1><B>" + this.dfDayOfMonth.format(paramCalendar.getTime()) + "</B></FONT></A>");

	        localStringBuffer.append(todaysEvents(localResultSet, this.dfMySQLDate.format(paramCalendar.getTime())));

	        if (j != 0) {
	          localStringBuffer.append("<CENTER><FORM><INPUT TYPE=BUTTON VALUE='Help' onClick=javascript:PopupWindow('" + this.thisServletURI + "','help','yes');></FORM></CENTER>");
	          j = 0;
	        }
	        localStringBuffer.append("</TD>");
	        paramCalendar.add(5, 1);
	      }
	      localStringBuffer.append("</TR>");
	    }while (paramCalendar.getTime().before(localCalendar2.getTime()));

	    paramCalendar.add(2, -1);
	    paramCalendar.set(5, 1);

	    return localStringBuffer.toString();
	  }

	  String todaysEvents(ResultSet paramResultSet, String paramString) {
	    try {
	      if ((paramResultSet == null) || (paramResultSet.isAfterLast())) return "";
	      StringBuffer localStringBuffer = new StringBuffer("<font size=-2>");
	      while (this.dfMySQLDate.format(paramResultSet.getDate("Sdate")).equals(paramString)) {
	        Time localTime1 = paramResultSet.getTime("Sdate");
	        Time localTime2 = paramResultSet.getTime("Edate");
	        String str = null;
	        if ((this.dfTimeField.format(localTime1).equals(this.time0000)) && (this.dfTimeField.format(localTime2).equals(this.time0000))) str = "TODAY:";
	        else if ((this.dfTimeField.format(localTime1).equals(this.time0000)) && (this.dfTimeField.format(localTime2).equals(this.time2359))) str = "ALL DAY:"; else {
	          str = this.dfTimeField.format(paramResultSet.getTime("Sdate")) + "-" + this.dfTimeField.format(paramResultSet.getTime("Edate"));
	        }
	        localStringBuffer.append("<br><A HREF=# onClick=javascript:PopupWindow('" + this.thisServletURI + "','revise','" + paramResultSet.getString("EventID") + "');><font color=#0000FF>" + str + "</font></a>&nbsp;");

	        if (paramResultSet.getBoolean("Flagged")) localStringBuffer.append("<FONT COLOR=#FF0000>");
	        localStringBuffer.append(paramResultSet.getString("Description"));
	        if (paramResultSet.getBoolean("Flagged")) localStringBuffer.append("</FONT>");
	        if (!paramResultSet.next()) return "</font>";
	      }
	      return "</font>";
	    } catch (SQLException localSQLException) {
	    }
	    return "</font>";
	  }

	  String tableFooter(Calendar paramCalendar)
	  {
	    Calendar localCalendar1 = (Calendar)paramCalendar.clone(); localCalendar1.add(2, 1);
	    Calendar localCalendar2 = (Calendar)paramCalendar.clone(); localCalendar2.add(2, -1);

	    return "<TR>  <TD BGCOLOR=" + this.cTopBarColor + " ALIGN=CENTER COLSPAN=7>" + "    <TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0>" + "    <TR ALIGN=CENTER>" + "      <TD><A HREF=" + this.thisServletURI + "?date=" + this.dfDateField.format(localCalendar2.getTime()) + "><FONT SIZE=-1 COLOR=" + this.cTFontsColor + "><&#151;&nbsp;" + this.dfMonthName.format(localCalendar2.getTime()) + "</FONT></A></TD>" + "      <TD><A HREF=" + this.thisServletURI + "><FONT SIZE=-1 COLOR=" + this.cTFontsColor + "><B>Go To Current Month</B></FONT></A></TD>" + "      <TD><A HREF=" + this.thisServletURI + "?date=" + this.dfDateField.format(localCalendar1.getTime()) + "><FONT SIZE=-1 COLOR=" + this.cTFontsColor + ">" + this.dfMonthName.format(localCalendar1.getTime()) + "&nbsp;&#151;></FONT></A></TD>" + "    </TR>" + "    </TABLE>" + "  </TD>" + "</TR>" + "<TR>" + "</TABLE>" + "<FONT SIZE=-2>Copyright &copy; 2001, <a href=mailto:harish.kaparwan@gmail.com>Harish Kaparwan</a>. " + "Distributed as a <a href=http://sourceforge.net/projects/javawebcalendar/>free download</a> " + "under the terms of the <a href=http://www.gnu.org/copyleft/gpl.html>GNU General Public License</a>.</FONT>";
	  }

	  String htmlFooter()
	  {
	    return "</body></HTML>";
	  }

	  boolean allowEdit(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
	    HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
	    String str1 = (String)localHttpSession.getAttribute("code");

	    if ((str1 != null) && 
	      (str1.equals(String.valueOf(this.secretPassPhrase.hashCode())))) {
	      return true;
	    }
	    Cookie[] arrayOfCookie = paramHttpServletRequest.getCookies();
	    if (arrayOfCookie != null) {
	      for (int i = 0; i < arrayOfCookie.length; i++)
	      {
	        str1 = arrayOfCookie[i].getValue();
	        if (str1.startsWith(Integer.toString(this.secretPassPhrase.hashCode()))) {
	        String  str2 = str1.substring(Integer.toString(this.secretPassPhrase.hashCode()).length());
	          localHttpSession.setAttribute("user", str2);
	          localHttpSession.setAttribute("code", Integer.toString(this.secretPassPhrase.hashCode()));
	          return true;
	        }
	      }
	    }

	    str1 = paramHttpServletRequest.getParameter("code");
	    String str2 = paramHttpServletRequest.getParameter("user");
	    if ((str1 != null) && 
	      (str1.equals(this.secretPassPhrase)) && (str2 != null)) {
	      localHttpSession.setAttribute("code", String.valueOf(str1.hashCode()));
	      localHttpSession.setAttribute("user", str2);
	      if (paramHttpServletRequest.getParameter("SetCookie") != null) {
	        Cookie localCookie = new Cookie("WebCalendar", str1.hashCode() + str2);
	        localCookie.setMaxAge(604800 * this.cookieMaxAge);
	        paramHttpServletResponse.addCookie(localCookie);
	      }
	      return true;
	    }

	    return false;
	  }

	  String promptForPassword(HttpServletRequest paramHttpServletRequest) {
	    return "<h3>Authentication</h3><FORM METHOD=GET ACTION='" + this.thisServletURI + "'>" + this.authMessage + "<hr><br>" + "Please type your name:<INPUT TYPE=TEXT SIZE=10 NAME='user'><BR>" + "Enter the secret phrase:<INPUT TYPE=PASSWORD NAME='code'><BR>" + "<INPUT TYPE=CHECKBOX NAME='SetCookie' VALUE='true'>Check here to accept a cookie for automatic login from this location.<BR>" + "<INPUT TYPE=HIDDEN NAME='form' VALUE='" + paramHttpServletRequest.getParameter("form") + "'>" + "<INPUT TYPE=HIDDEN NAME='value' VALUE='" + paramHttpServletRequest.getParameter("value") + "'>" + "<INPUT TYPE=SUBMIT VALUE='Submit'>" + "</FORM>";
	  }

	  String newEventForm(String paramString)
	  {
	    StringBuffer localStringBuffer = new StringBuffer();

	    localStringBuffer.append("<body onLoad=window.focus()><H3>Control Panel</H3><FORM METHOD=POST ACTION='" + this.thisServletURI + "'>" + "<INPUT TYPE=HIDDEN NAME='UserRequest' VALUE='New'>" + "  <TABLE BORDER=0 CELLSPACING=0>" + "  <TR><TD>Date:</TD><TD><INPUT TYPE=TEXT SIZE=8 NAME=EventDate VALUE=" + paramString + "></TD></TR>" + "  <TR><TD ROWSPAN=3 VALIGN=TOP>Time: <BR><FONT SIZE=-2>(e.g., " + this.dfTimeField.format(new Date(4800000L)) + ")</FONT></TD>" + "  <TD><INPUT TYPE=RADIO NAME=EventType VALUE=REGULAR CHECKED>" + "    Start: <INPUT TYPE=TEXT SIZE=8 NAME=STime>" + "    End: <INPUT TYPE=TEXT SIZE=8 NAME=ETime></TD></TR>" + "  <TR><TD><INPUT TYPE=RADIO NAME=EventType VALUE=ALLDAY>All day (" + this.dfTimeField.format(new Date(25200000L)) + " - " + this.dfTimeField.format(new Date(111540000L)) + ")</TD></TR>" + "  <TR><TD><INPUT TYPE=RADIO NAME=EventType VALUE=SPECIAL>No specific time (e.g., birthday or holiday)</TD></TR>" + "  <TR><TD>Description:</TD><TD><INPUT TYPE=TEXT SIZE=34 NAME=Description></TD></TR>" + "  <TR><TD></TD><TD><INPUT TYPE=CHECKBOX NAME=Flagged VALUE='true'>Display the description in red.</TD></TR>" + "  <TR><TD VALIGN=TOP>Notes:<br><FONT SIZE=-2>(can only be viewed in this window)</FONT></TD><TD><TEXTAREA NAME=Notes ROWS=6 COLS=32 WRAP=SOFT></TEXTAREA></TD></TR>" + "  <TR><TD COLSPAN=2>" + "  <INPUT TYPE=SUBMIT VALUE='Create This Event'>" + "  </TD></TR><TR><TD COLSPAN=2>" + "  <FONT SIZE=-2>You may have to click the \"Refresh\" button on your browser to view the changes.</FONT>" + "  </TD></TR>" + "  </TABLE>" + "</FORM>");

	    return localStringBuffer.toString();
	  }

	  String reviseEventForm(String paramString)
	  {
	    String str1 = null;
	    String str2 = null;
	    String str3 = null;
	    String str4 = null;
	    String str5 = null;
	    String str6 = null;
	    boolean bool = false;
	    try
	    {
	      Class.forName(this.mySqlJdbcDriver).newInstance();
	      Connection localConnection = DriverManager.getConnection(this.eventsDatabase, this.mySQLUser, this.mySQLPass);
	      Statement localStatement = localConnection.createStatement();
	      String str7 = "SELECT * FROM Events WHERE EventID=" + paramString;
	      ResultSet localResultSet = localStatement.executeQuery(str7);
	      if (localResultSet.next()) {
	        str1 = this.dfDateField.format(localResultSet.getDate("Sdate"));
	        str2 = this.dfTimeField.format(localResultSet.getTime("Sdate"));
	        str3 = this.dfTimeField.format(localResultSet.getTime("Edate"));
	        str4 = localResultSet.getString("Description");
	        bool = localResultSet.getBoolean("Flagged");
	        str5 = localResultSet.getString("Notes");
	        str6 = localResultSet.getString("User");
	      }
	    } catch (Exception localException) {
	      return localException.getMessage();
	    }

	    StringBuffer localStringBuffer = new StringBuffer();
	    localStringBuffer.append("<body onLoad=window.focus()><H3>Control Panel</H3><FORM METHOD=POST ACTION='" + this.thisServletURI + "'>" + "<INPUT TYPE=HIDDEN NAME='UserRequest' VALUE='Revise'>" + "<INPUT TYPE=HIDDEN NAME='EventID' VALUE=" + paramString + ">" + "  <TABLE BORDER=0 CELLSPACING=0>" + "  <TR><TD>Date:</TD><TD>" + "    <TABLE WIDTH=100% BORDER=0><TR><TD>" + "      <INPUT TYPE=TEXT SIZE=8 NAME='EventDate' VALUE='" + str1 + "'></TD>" + "      <TD ALIGN=RIGHT><FONT SIZE=-2>This event was entered by " + str6 + "</FONT>" + "      </TD></TR></TABLE>" + "  </TD></TR>" + "<TR><TD ROWSPAN=3 VALIGN=TOP>Time: <BR>" + "<FONT SIZE=-2>(e.g., " + this.dfTimeField.format(new Date(91200000L)) + ")</FONT></TD>" + "<TD><INPUT TYPE=RADIO NAME=EventType VALUE=REGULAR CHECKED>" + "Start: <INPUT TYPE=TEXT SIZE=8 NAME=STime VALUE='" + str2 + "'>" + "End: <INPUT TYPE=TEXT SIZE=8 NAME=ETime VALUE='" + str3 + "'></TD></TR>" + "  <TR><TD><INPUT TYPE=RADIO NAME=EventType VALUE=ALLDAY>" + "All day (" + this.time0000 + " - " + this.time2359 + ")</TD></TR>" + "  <TR><TD><INPUT TYPE=RADIO NAME=EventType VALUE=SPECIAL>" + "No specific time (e.g., birthday or holiday)</TD></TR>" + "  <TR><TD>Description:</TD><TD><INPUT TYPE=TEXT SIZE=34 NAME=Description VALUE=\"" + removeDoubleQuotes(str4) + "\"><TD></TR>" + "<TR><TD></TD><TD><INPUT TYPE=CHECKBOX NAME=Flagged VALUE='true'");

	    if (bool) localStringBuffer.append(" CHECKED><FONT COLOR=#FF0000");
	    localStringBuffer.append(">Display the description in red.</FONT></TD></TR><TR><TD VALIGN=TOP>Notes:<br><FONT SIZE=-2>(can only be viewed in this window)</FONT></TD><TD><TEXTAREA NAME=Notes ROWS=6 COLS=32 WRAP=SOFT>" + str5 + "</TEXTAREA></TD></TR>" + "  </TD></TR><TR><TD COLSPAN=2>" + "  <TABLE BORDER=0 CELLSPACING=0><TR>" + "    <TD><INPUT TYPE=SUBMIT VALUE='Modify'></TD>" + "    <TD><INPUT TYPE=SUBMIT VALUE='Save New' OnClick=UserRequest.value='New'></TD>" + "    <TD><INPUT TYPE=SUBMIT VALUE='Delete' OnClick=UserRequest.value='Delete'></TD></FORM>" + "  </TABLE>" + "  </TD></TR><TR><TD COLSPAN=2>" + "  <FONT SIZE=-2>You may have to click the \"Refresh\" button on your browser " + "to view the changes.</FONT>" + "  </TD></TR>" + "  </TABLE>");

	    return localStringBuffer.toString();
	  }

	  public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
	    throws ServletException, IOException
	  {
	    if (this.viewOnly) return;
	    HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
	    String str1 = (String)localHttpSession.getAttribute("user");

	    paramHttpServletResponse.setContentType("text/html");
	    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
	    this.thisServletURI = paramHttpServletRequest.getServletPath();

	    localPrintWriter.println("<HTML><head><title>Control Panel</title>");
	    localPrintWriter.println("<SCRIPT Language=JavaScript>");
	    localPrintWriter.println("function finish(how,date) {");
	    localPrintWriter.println("  if (how == 'OK') {");
	    localPrintWriter.println("    opener.document.location = '" + this.thisServletURI + "?date='+date;");
	    localPrintWriter.println("    window.close();");
	    localPrintWriter.println("  } else if (how == 'conflict') {");
	    localPrintWriter.println("    alert('Warning: this event conflicts with another event.');");
	    localPrintWriter.println("    opener.document.location = '" + this.thisServletURI + "?date='+date;");
	    localPrintWriter.println("    window.close();");
	    localPrintWriter.println("  } else if (how == 'dbError') {");
	    localPrintWriter.println("    alert('An unexpected database error was encountered.');");
	    localPrintWriter.println("    opener.document.location = '" + this.thisServletURI + "?date='+date;");
	    localPrintWriter.println("    history.go(-1);");
	    localPrintWriter.println("  } else if (how == 'bad') {");
	    localPrintWriter.println("    alert('Warning: some data values were missing or formatted incorrectly. Please try again.');");
	    localPrintWriter.println("    history.go(-1);");
	    localPrintWriter.println("  }");
	    localPrintWriter.println("}");
	    localPrintWriter.println("</SCRIPT></head>");

	    String str2 = paramHttpServletRequest.getParameter("UserRequest");
	    String str3 = paramHttpServletRequest.getParameter("EventID");
	    String str4 = null;
	    String str5 = null;
	    Object localObject1;
	    Object localObject2;
	    Object localObject3;
	    if (str2.equals("Delete")) {
	      str4 = "DELETE FROM Events WHERE EventID=" + str3;
	    } else {
	      localObject1 = null;
	      localObject2 = null;
	      localObject3 = removeSingleQuotes(paramHttpServletRequest.getParameter("Description"));
	      String str6 = removeSingleQuotes(paramHttpServletRequest.getParameter("Notes"));
	      boolean bool = Boolean.valueOf(paramHttpServletRequest.getParameter("Flagged")).booleanValue();
	      try {
	        String str7 = paramHttpServletRequest.getParameter("EventType");
	        String str8 = this.dfMySQLDate.format(this.dfDateField.parse(paramHttpServletRequest.getParameter("EventDate")));
	        if (str7.equals("REGULAR")) {
	          String str9 = paramHttpServletRequest.getParameter("STime");
	          String str10 = paramHttpServletRequest.getParameter("ETime");
	          if (str9.equals("")) {
	            localObject1 = str8 + " 00:00";
	            localObject2 = str8 + " 00:00";
	          }
	          else {
	            localObject1 = str8 + " " + this.dfMySQLTime.format(this.dfTimeField.parse(str9));
	            if (str10.equals("")) localObject2 = localObject1; else
	              localObject2 = str8 + " " + this.dfMySQLTime.format(this.dfTimeField.parse(str10));
	          }
	        } else if (str7.equals("SPECIAL")) {
	          localObject1 = str8 + " 00:00";
	          localObject2 = str8 + " 00:00";
	        } else if (str7.equals("ALLDAY")) {
	          localObject1 = str8 + " 00:00";
	          localObject2 = str8 + " 23:59";
	        }
	      }
	      catch (Exception localException2) {
	        localPrintWriter.println("<body onLoad=finish('bad','" + paramHttpServletRequest.getParameter("EventDate") + "');>");
	        localPrintWriter.println("</body></html>");
	        return;
	      }

	      if (str2.equals("New")) {
	        str4 = "INSERT INTO Events (Sdate,Edate,Flagged,User,Description,Notes) VALUES ('" + (String)localObject1 + "','" + (String)localObject2 + "','" + (bool ? 1 : 0) + "','" + str1 + "','" + (String)localObject3 + "','" + str6 + "')";

	        str5 = "SELECT * FROM Events WHERE NOT (Edate<='" + (String)localObject1 + "' OR Sdate>='" + (String)localObject2 + "')";
	      }
	      else if (str2.equals("Revise")) {
	        str4 = "UPDATE Events SET Sdate='" + (String)localObject1 + "',Edate='" + (String)localObject2 + "',Flagged='" + (bool ? 1 : 0) + "',User='" + str1 + "',Description='" + (String)localObject3 + "',Notes='" + str6 + "' WHERE EventID=" + str3;

	        str5 = "SELECT * FROM Events WHERE (NOT (Edate<='" + (String)localObject1 + "' OR Sdate>='" + (String)localObject2 + "') AND " + "EventID<>'" + str3 + "')";
	      }

	    }

	    try
	    {
	      Class.forName(this.mySqlJdbcDriver).newInstance();
	      localObject1 = DriverManager.getConnection(this.eventsDatabase, this.mySQLUser, this.mySQLPass);
	      localObject2 = ((Connection)localObject1).createStatement();

	      if ((str2.equals("New") | str2.equals("Revise")))
	      {
	        localObject3 = ((Statement)localObject2).executeQuery(str5);
	        if (((Statement)localObject2).executeUpdate(str4) != 1)
	          localPrintWriter.println("Caution: possible database error " + ((Statement)localObject2).executeUpdate(str4));
	        if (((ResultSet)localObject3).next()) {
	          localPrintWriter.println("<body onLoad=finish('conflict','" + paramHttpServletRequest.getParameter("EventDate") + "');>");
	          do
	            localPrintWriter.println(((ResultSet)localObject3).getString("Description") + "<br>");
	          while (((ResultSet)localObject3).next());
	        }
	        else {
	          localPrintWriter.println("<body onLoad=finish('OK','" + paramHttpServletRequest.getParameter("EventDate") + "');>");
	        }
	      } else {
	        if (((Statement)localObject2).executeUpdate(str4) != 1)
	          localPrintWriter.println("Caution: possible database error " + ((Statement)localObject2).executeUpdate(str4));
	        localPrintWriter.println("<body onLoad=finish('OK','" + paramHttpServletRequest.getParameter("EventDate") + "');>");
	      }
	      localPrintWriter.println("</body></html>");
	      ((Statement)localObject2).close();
	      ((Connection)localObject1).close();
	    }
	    catch (Exception localException1) {
	      localPrintWriter.println("<body onLoad=finish('dbError','" + paramHttpServletRequest.getParameter("EventDate") + "');>");
	      localPrintWriter.println(localException1.getMessage());
	      localPrintWriter.println("</body></html>");
	    }
	  }

	  String removeSingleQuotes(String paramString) {
	    int i = paramString.indexOf('\'', 0);
	    return i < 0 ? paramString : removeSingleQuotes(new StringBuffer(paramString).insert(i, '\\').toString(), i + 2);
	  }

	  String removeSingleQuotes(String paramString, int paramInt) {
	    int i = paramString.indexOf('\'', paramInt);
	    return i < 0 ? paramString : removeSingleQuotes(new StringBuffer(paramString).insert(i, '\\').toString(), i + 2);
	  }

	  String removeDoubleQuotes(String paramString) {
	    int i = paramString.indexOf('"', 0);
	    return i < 0 ? paramString : removeDoubleQuotes(new StringBuffer(paramString).replace(i, i + 1, "&quot;").toString(), i + 5);
	  }

	  String removeDoubleQuotes(String paramString, int paramInt) {
	    int i = paramString.indexOf('"', paramInt);
	    return i < 0 ? paramString : removeDoubleQuotes(new StringBuffer(paramString).replace(i, i + 1, "&quot;").toString(), i + 5);
	  }
	  }
