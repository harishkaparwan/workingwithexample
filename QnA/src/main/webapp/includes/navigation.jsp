<html>
<head>
<title>jQuery Accordion Style DIV Menu</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"> </script>
<script type="text/javascript">
$(document).ready(function() {
	//ACCORDION BUTTON ACTION (ON CLICK DO THE FOLLOWING)
	$('.accordionButton').click(function() {
		//REMOVE THE ON CLASS FROM ALL BUTTONS
		$('.accordionButton').removeClass('on');
		//NO MATTER WHAT WE CLOSE ALL OPEN SLIDES
	 	$('.accordionContent').slideUp('normal');
		//IF THE NEXT SLIDE WASN'T OPEN THEN OPEN IT
		if($(this).next().is(':hidden') == true) {
			
			//ADD THE ON CLASS TO THE BUTTON
			$(this).addClass('on');
			  
			//OPEN THE SLIDE
			$(this).next().slideDown('normal');
		 } 
		  
	 });
	  
	
	/*** REMOVE IF MOUSEOVER IS NOT REQUIRED ***/
	
	//ADDS THE .OVER CLASS FROM THE STYLESHEET ON MOUSEOVER 
	$('.accordionButton').mouseover(function() {
		$(this).addClass('over');
		
	//ON MOUSEOUT REMOVE THE OVER CLASS
	}).mouseout(function() {
		$(this).removeClass('over');										
	});
	
	/*** END REMOVE IF MOUSEOVER IS NOT REQUIRED ***/
	
	
	/********************************************************************************************************************
	CLOSES ALL S ON PAGE LOAD
	********************************************************************************************************************/	
	$('.accordionContent').hide();

});

</script>
<style type="text/css">
#wrapper {
	width: 200px;
	
	margin-right: auto;
	}

.accordionButton {
	background: url("http://www.cssportal.com/images/bg.png") repeat scroll 0 0 transparent;	
	width: 200px;
	float: left;
	border-bottom: 4px solid #FFFFFF;
	cursor: pointer;
	}
	
.accordionContent {	
	width: 200px;
	float: left;
	background: #95B1CE;
	display: none;
	}
.on {
	background: #888;
	}
	
.over {
	background: #CCCCCC;
	}
</style>


</head>
<body>

<div id="wrapper">
		<div class="accordionButton">Home</div>
		<div class="accordionContent"><a href="BaseActivityCal">Calendar</a>
		<br /><br /><a href="teamActivity.jsp">teamActivity</a>
		<br /><br /><a href="event/displayEvent.htm">Display Event</a>
		<br /><br /><a href="jiraTicket.htm">Create Jira Ticket</a>
		<br /><br /><a href="viewAll.htm">View All Jira Ticket</a>
		
		</div>
	
		<div class="accordionButton">Blog</div>
		<div class="accordionContent">Content 2<br /><br /><br /><br /><br />Medium Example</div>
		<div class="accordionButton">Article</div>
		<div class="accordionContent">Content 1<br />Short Example</div>
		<div class="accordionButton">Contact Us</div>
		<div class="accordionContent">Content 4<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />Extra Long Example</div>
	</div>

</body>


</html>