$(document).ready(function() {
	
	// Expand Panel
	$("#open").click(function(){
		alert("open");
		$("div#panel").slideDown("slow");
	
	});	
	
	// Collapse Panel
	$("#close").click(function(){
		alert("close");
		$("div#panel").slideUp("slow");	
	});		

	// Switch buttons from "Log In | Register" to "Close Panel" on click
	$("#toggle a").click(function () {
		alert("toggle a");
		$("#toggle a").toggle();
	});	
		
});