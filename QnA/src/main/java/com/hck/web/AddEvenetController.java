package com.hck.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.hck.model.Event;
import com.hck.service.CalendarServiceImpl;

public class AddEvenetController implements Controller{

	
	CalendarServiceImpl calendarServiceImpl;
	public ModelAndView handleRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(":::AddEvenetController:::");
		Event event= new Event();
		event.setDescription(httpServletRequest.getParameter("description"));
		event.setUser(httpServletRequest.getParameter("user"));

		getCalendarServiceImpl().persist(event);
		return new ModelAndView();
	}
	public CalendarServiceImpl getCalendarServiceImpl() {
		return calendarServiceImpl;
	}
	public void setCalendarServiceImpl(CalendarServiceImpl calendarServiceImpl) {
		this.calendarServiceImpl = calendarServiceImpl;
	}

	
}
	
