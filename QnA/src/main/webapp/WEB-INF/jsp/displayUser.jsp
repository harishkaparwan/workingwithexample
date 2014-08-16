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
        <display:table id="displayUser" name="displayUser" requestURI="/displayUser.htm" pagesize="6" export="true" >
            <display:column property="id" title="User ID" paramId="id" href="displayUser.htm"/>
             <display:column property="firstName" title="first Name" />
            <display:column property="lastName" title="last Name" />
            <display:column property="username" title="user name" />
                        <display:column property="role.role" title="Role" />
            
        </display:table>
    </body>
</html>