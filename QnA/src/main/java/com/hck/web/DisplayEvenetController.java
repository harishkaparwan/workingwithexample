package com.hck.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.hck.model.Event;
import com.hck.service.CalendarServiceImpl;

public class DisplayEvenetController extends MultiActionController {

	private CalendarServiceImpl calendarServiceImpl;
	public ModelAndView displayEvent(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("DisplayEvenetController::::handleRequest::::");
		Event event= new Event();
		return new ModelAndView("displayEvent","event",event);
	}
	
	
	public ModelAndView addEvent(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,Event event) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("DisplayEvenetController::::addevent::::"+event.getDescription());
		List  listofDates=getDates(event.getSdate(),event.getEdate());
		System.out.println("CalendarServiceImpl:listofDates:::"+listofDates);

		//for(int i=0;i<listofDates.size();i++){
		//	String lDate =(String) listofDates.get(i);
			//Date  startDate = (Date)formatter.parse(lDate);
		    System.out.println("::List of dates:::"+event.getDescription());
			//event.setEdate(lDate);
			//event.setSdate(lDate);
		 calendarServiceImpl.persist(event);
		
	//	}
		
		return new ModelAndView("displayEvent","event",event);
	}
	public CalendarServiceImpl getCalendarServiceImpl() {
		return calendarServiceImpl;
	}
	public void setCalendarServiceImpl(CalendarServiceImpl calendarServiceImpl) {
		this.calendarServiceImpl = calendarServiceImpl;
	}
	public List getDates(String str_date,String end_date) throws ParseException {
		// TODO Auto-generated method stub
		List dates = new ArrayList();
		List dates1 = new ArrayList();
		//String str_date ="27/08/2010";
		//String end_date ="02/09/2010";

		DateFormat formatter ; 

		formatter = new SimpleDateFormat("dd/MM/yy");
		Date  startDate = (Date)formatter.parse(str_date); 
		Date  endDate = (Date)formatter.parse(end_date);
		long interval = 24*1000 * 60 * 60; // 1 hour in millis
		long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
		    dates.add(new Date(curTime));
		    curTime += interval;
		}
		for(int i=0;i<dates.size();i++){
		    Date lDate =(Date)dates.get(i);
		    String ds = formatter.format(lDate);    
		    System.out.println(" Date is ..." + ds);
		    dates1.add(ds);
		}


		return dates1;
	}

	
}
