   (function($) {
	 
    var d = new Date();
    d.setDate(d.getDate() - d.getDay());
    var year = d.getFullYear();
    var month = d.getMonth();
    var day = d.getDate();
    var html;
    var json = (function () {
        var json = null;
        $.ajax({
            type: "GET",
            'async': false,
            dataType: "json",
            url: "activityLog",// where you wanna post
            success: function(data) {
             console.log("####success :#######"+data);
                json=data
         	 } ,
       		error: function (XMLHttpRequest, textStatus, errorThrown) {
            	json=XMLHttpRequest.responseText;
            return json;
         }
         
        });
        return json;
    })(); 
 
 
  
   $(document).ready(function() {
	   
    console.log("***hey I am ready*******");
    var $calendar = $('#calendar');
      var id = 10;
      console.log("***$calendar*******"+$calendar);
      $calendar.weekCalendar({
        data: json,
        timeslotsPerHour: 4,
        scrollToHourMillis : 0,
         allowCalEventOverlap : true,
        overlapEventsSeparate: true,
        height: function($calendar){
          return $(window).height() - $('h1').outerHeight(true);
        },
        eventRender : function(calEvent, $event) {
         console.log("***eventRender called*******");

          if (calEvent.end.getTime() < new Date().getTime()) {
            $event.css('backgroundColor', '#aaa');
            $event.find('.wc-time').css({
              backgroundColor: '#999',
              border:'1px solid #888'
            });
          }
        },
        draggable : function(calEvent, $event) {
          console.log("***draggable called*******"+calEvent+$event);
         return calEvent.readOnly != true;
      },
      resizable : function(calEvent, $event) {
       console.log("***resizable called*******"+calEvent+$event);

         return calEvent.readOnly != true;
      },
        eventNew : function(calEvent, $event, FreeBusyManager, calendar) {
          console.log("***eventNew called*******"+calEvent+$event);
         var $dialogContent = $("#event_edit_container");
         resetForm($dialogContent);
         var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
         var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
         var titleField = $dialogContent.find("input[name='title']");
         var bodyField = $dialogContent.find("textarea[name='body']");
         console.log("***titleField.value called*******"+titleField);

         $dialogContent.dialog({
            modal: true,
            title: "New Calendar Event",
            close: function() {
               $dialogContent.dialog("destroy");
               $dialogContent.hide();
               $('#calendar').weekCalendar("removeUnsavedEvents");
            },
            buttons: {
               save : function() {
                  calEvent.id = id;
                  id++;
                  calEvent.start = new Date(startField.val());
                  calEvent.end = new Date(endField.val());
                  calEvent.title = titleField.val();
                  calEvent.body = bodyField.val();
                  

                  $calendar.weekCalendar("removeUnsavedEvents");
                  $calendar.weekCalendar("updateEvent", calEvent);
                
                  $.ajax({
                      type: "POST",
                      url: "activityLog",// where you wanna post
                      data : "title=" + calEvent.title + "&start=" + calEvent.start + "&end=" + calEvent.end + "&body="+ calEvent.body+"&userId="+calEvent.userId+"&id="+calEvent.id+"&methodType=insert", 
                     
                   //   processData: false,
                     // contentType: false,
                      success: function(data) {console.log(data)} 
                  });
                  console.log("***insert method::calEvent.userId called*******"+calEvent.userId);
                  console.log("***insert method::calEvent.id called*******"+calEvent.id);
                  console.log("***insert method::calEvent.body called*******"+calEvent.body);
                  console.log("***insert method::calEvent.title called*******"+calEvent.title);
                  console.log("***insert method::calEvent.start called*******"+calEvent.start);
                  console.log("***insert method::calEvent.end called*******"+calEvent.end);
                  $dialogContent.dialog("close");
               },
               cancel : function() {
                  $dialogContent.dialog("close");
               }
            }
         }).show();

         
         $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
         setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));

      },
      eventDrop : function(calEvent, $event) {
        
      },
      eventResize : function(calEvent, $event) {
      },
      eventClick : function(calEvent, $event) {

         if (calEvent.readOnly) {
            return;
         }

         var $dialogContent = $("#event_edit_container");
         resetForm($dialogContent);
         var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
         var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
         var titleField = $dialogContent.find("input[name='title']").val(calEvent.title);
         var bodyField = $dialogContent.find("textarea[name='body']");
         bodyField.val(calEvent.body);

         $dialogContent.dialog({
            modal: true,
            title: "Edit - " + calEvent.title,
            close: function() {
               $dialogContent.dialog("destroy");
               $dialogContent.hide();
               $('#calendar').weekCalendar("removeUnsavedEvents");
            },
            buttons: {
               save : function() {

                  calEvent.start = new Date(startField.val());
                  calEvent.end = new Date(endField.val());
                  calEvent.title = titleField.val();
                  calEvent.body = bodyField.val();

                  $.ajax({
                      type: "POST",
                      url: "activityLog",// where you wanna post
                      data : "title=" + calEvent.title + "&start=" + calEvent.start + "&end=" + calEvent.end + "&body="+ calEvent.body+"&userId="+calEvent.userId+"&id="+calEvent.id+"&methodType=update", 
                     
                   //   processData: false,
                     // contentType: false,
                      success: function(data) {console.log(data)} 
                  });
                  
                  $calendar.weekCalendar("updateEvent", calEvent);
                  console.log("***insert method::calEvent.id called*******"+calEvent.id);
                  console.log("***update method::calEvent.body called*******"+calEvent.body);
                  console.log("***update method::calEvent.title called*******"+calEvent.title);
                  console.log("***update method::calEvent.start called*******"+calEvent.start);
                  console.log("***update method::calEvent.end called*******"+calEvent.end);
                  $dialogContent.dialog("close");
               },
               "delete" : function() {
                  $calendar.weekCalendar("removeEvent", calEvent.id);
                  $dialogContent.dialog("close");
               },
               cancel : function() {
                  $dialogContent.dialog("close");
               }
            }
         }).show();

         var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
         var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
         $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
         setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));
         $(window).resize().resize(); //fixes a bug in modal overlay size ??

      },
      eventMouseover : function(calEvent, $event) {
      },
      eventMouseout : function(calEvent, $event) {
      },
      noEvents : function() {

      },
     
      data : function(start, end, callback) {
                var dataSource ="1";
          if (dataSource === '1') {
            callback(json);
          } else if(dataSource === '2') {
            callback(json);
          } else {
            callback({
              options: {
                defaultFreeBusy: {
                  free:true
                }
              },
              events: ['Working with Harish', 'In Progress tickets']
            });
        }  
      }, 
        users: ['Akhil', 'Chandan', 'Beena', 'Ratheesh', 'Karthik','Nitesh Kumar V', 'Vellu', 'QA Team', 'Pandharinath', 'Harish','Sudhir', 'Kurian', 'Margratte', 'Nitesh Anand', 'Chinmay'],
        //showAsSeparateUser: true,
        displayOddEven: true,
        displayFreeBusys: true,
        daysToShow: 1,
        headerSeparator: ' ',
        useShortDayNames: true,
        // I18N
        
        dateFormat: 'd F y'
      });  // Webcalendar

      function resetForm($dialogContent) {
      $dialogContent.find("input").val("");
      $dialogContent.find("textarea").val("");
   }

   function getEventData() {
    console.log("***getEventData() called*******");
   
      return {
         events : [
			   {"id":1, "start": new Date(year, month, day, 12), "end": new Date(year, month, day, 13, 30),"title":"Lunch with Mike"},
			   {"id":2, "start": new Date(year, month, day, 14), "end": new Date(year, month, day, 14, 45),"title":"Dev Meeting"},
			   {"id":3, "start": new Date(year, month, day + 1, 18), "end": new Date(year, month, day + 1, 18, 45),"title":"Hair cut"},
			   {"id":4, "start": new Date(year, month, day - 1, 8), "end": new Date(year, month, day - 1, 9, 30),"title":"Team breakfast"},
			   {"id":5, "start": new Date(year, month, day + 1, 14), "end": new Date(year, month, day + 1, 15),"title":"Product showcase"}
			]
      };
   }


   /*
    * Sets up the start and end time fields in the calendar event
    * form for editing based on the calendar event being edited
    */
   function setupStartAndEndTimeFields($startTimeField, $endTimeField, calEvent, timeslotTimes) {

      for (var i = 0; i < timeslotTimes.length; i++) {
         var startTime = timeslotTimes[i].start;
         var endTime = timeslotTimes[i].end;
         var startSelected = "";
         if (startTime.getTime() === calEvent.start.getTime()) {
            startSelected = "selected=\"selected\"";
         }
         var endSelected = "";
         if (endTime.getTime() === calEvent.end.getTime()) {
            endSelected = "selected=\"selected\"";
         }
         $startTimeField.append("<option value=\"" + startTime + "\" " + startSelected + ">" + timeslotTimes[i].startFormatted + "</option>");
         $endTimeField.append("<option value=\"" + endTime + "\" " + endSelected + ">" + timeslotTimes[i].endFormatted + "</option>");

      }
      $endTimeOptions = $endTimeField.find("option");
      $startTimeField.trigger("change");
   }

   var $endTimeField = $("select[name='end']");
   var $endTimeOptions = $endTimeField.find("option");

   //reduces the end time options to be only after the start time options.
   $("select[name='start']").change(function() {
      var startTime = $(this).find(":selected").val();
      var currentEndTime = $endTimeField.find("option:selected").val();
      $endTimeField.html(
            $endTimeOptions.filter(function() {
               return startTime < $(this).val();
            })
            );

      var endTimeSelected = false;
      $endTimeField.find("option").each(function() {
         if ($(this).val() === currentEndTime) {
            $(this).attr("selected", "selected");
            endTimeSelected = true;
            return false;
         }
      });

      if (!endTimeSelected) {
         //automatically select an end date 2 slots away.
         $endTimeField.find("option:eq(1)").attr("selected", "selected");
      }

   });


   var $about = $("#about");

   $("#about_button").click(function() {
      $about.dialog({
         title: "About this calendar demo",
         width: 600,
         close: function() {
            $about.dialog("destroy");
            $about.hide();
         },
         buttons: {
            close : function() {
               $about.dialog("close");
            }
         }
      }).show();
   });
      
   
  });
  })(jQuery); 
   
 