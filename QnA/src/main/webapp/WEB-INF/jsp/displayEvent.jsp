<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.hck.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="../css/FormStyle.css" rel="stylesheet" type="text/css"  />
<link href="../css/calendar.css" rel="stylesheet" type="text/css" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

</head>
<body>
<form:form method="POST" action="addEvent.htm"  commandName="event" name="event" >

 Description:: <form:input path="description" /> <br/>
 End Date:: <form:input path="edate" /> <br/>
 Start Date:: <form:input path="sdate" /> <br/>
 User Name:: <form:input path="user" /> <br/>
 flagged:: <form:input path="flagged" /> <br/>
<input type="submit" value="Submit" />
<input type="reset" value="Reset"/>

    </form:form>	
</body>
</html>
