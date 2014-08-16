<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title> <decorator:title default="Welcome!"/></title>
<style>
body {
	
	background-color: white;
}
a {
	color: #CCCCCC;
}
#page-container #page-footer div p{
color:#FFFFFF;
font-style: italic;
	font-weight: bold;
}
#page-container {
	width: 100%;
	height: 100%;
	
	background: -webkit-gradient(linear, 77% 88%, 15% 1%, from(#CB7A27), to(#160014));
}
	

#content-container {
	height: 100%;
	text-align: left;
	padding: 2em;
	padding-top:0;
	 background: url("../images/header-blue.jpg") repeat scroll 0 0 transparent !important;
	
}
#page-header  {
	width: 100%;
	text-align: center;
}
#nav-container {
	height: 300px;
	width: 15%;
	
	border-color: #666666 #cccccc #cccccc #666666;
	
	vertical-align: top;
	background: -webkit-gradient(linear, 77% 88%, 15% 1%, from(#CB7A27), to(#160014));
}
}
#nav-container a {
	text-decoration: none;
	color: black;
}
.selected {
	padding: 2px;
	color: black;
	border: 1px yellow dotted;
	font-style: italic;
	font-weight: bold;
}
#nav-container a:visited {
	color: lightGray;
}
#nav-container a:hover {
	color: yellow;
}
#page-footer {
	width: 100%;
	padding: 5px;
	font-size: x-small;	
	
	border-color: #666666 #cccccc #cccccc #666666;
	text-align: center;
}


</style>
    <decorator:head/>
</head>

<body>
	<table id="page-container" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="2" id="page-header">
			<%@ include file="/includes/header.jsp"%>
		</td>
	</tr><tr>
		<td id="nav-container">
			<%@ include file="/includes/navigation.jsp" %>
		</td>
		<td id="content-container">
			<decorator:body/>
		</td>
	</tr><tr>
		<td colspan="2" id="page-footer">
			<%@ include file="/includes/footer.jsp" %>
		</td>
	</tr></table>
</body>
</html>

