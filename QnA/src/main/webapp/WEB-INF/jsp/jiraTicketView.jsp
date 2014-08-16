<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Details</title>
        <link rel="stylesheet" href="./css/displaytag.css" type="text/css">  
<link rel="stylesheet" href="./css/screen.css" type="text/css">  
<link rel="stylesheet" href="./css/site.css" type="text/css"> 
    </head>
    <body>
        <display:table id="jiraTicketList" name="jiraTicketList" requestURI="/viewAll.htm" pagesize="6" export="true" >
            <display:column property="ticketNo" title="Ticket NO" paramId="id" href="view.htm" />
             <display:column property="summary" title="summary" />
            <display:column property="assignee" title="Assigned to" />
            <display:column property="devStartDate" title="devStartDate" />
            <display:column property="devEndDate" title="devEndDate" />
            <display:column property="reviewedBy" title="reviewedBy" />
            <display:column property="reviewedOn" title="reviewedOn" />
            <display:column property="testedBy" title="testedBy" />
            
        </display:table>
    </body>
</html>