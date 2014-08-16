$("#submit").click( function() {
	 alert("bi");

	if( $("#username").val() == "" || $("#pass").val() == "" )
	  $("#ack").html("Username/Password are mandatory fields -- Please Enter.");
	else
	  $.post( $("#myForm").attr("action"),
	         $("#myForm :input").serializeArray(),
			 function(info) {
 
			   $("#ack").empty();
			   $("#ack").html(info);
				clear();
			 });
 
	$("#myForm").submit( function() {
	   return false;	
	});
});
 
function clear() {
 
	$("#myForm :input").each( function() {
	      $(this).val("");
	});
 
}