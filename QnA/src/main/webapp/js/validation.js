function update()
	{	alert( "Inside Ajax update function kkkk  " );
		jQuery.ajax({
				type:POST,
				url:"updateTicketDetails.htm",
				
				success: function(){
	         	alert( "Data updated "  ); }
			});
	}



function disableText(){ 
	alert("Inside Show function");
	jQuery('.text').attr('disabled','true');
}

function enableText(){
	alert("Inside enble text function ");
	jQuery('.text').removeAttr('disabled','true');
}
