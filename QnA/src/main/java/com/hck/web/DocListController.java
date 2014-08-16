package com.hck.web;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.hck.model.Developermaster;
import com.hck.model.User;
import com.hck.service.UserServiceImpl;


public class DocListController extends MultiActionController {
	
	private UserServiceImpl userServiceImpl;
	
	private ArrayList<Developermaster> jiraTicketList;

	
	public ArrayList<Developermaster> getJiraTicketList() {
		return jiraTicketList;
	}

	public void setJiraTicketList(ArrayList<Developermaster> jiraTicketList) {
		this.jiraTicketList = jiraTicketList;
	}
	@Secured("ROLE_USER11")
	public ModelAndView jiraTicket(HttpServletRequest request,
			HttpServletResponse response, Developermaster developermasterForm) throws Exception {
		request.getServletContext().setAttribute("roleName", "Developer");
		
		
		return new ModelAndView("jiraTicket", "developermasterForm", developermasterForm);
	}
	
	public ModelAndView addDeveloper(HttpServletRequest request,
			HttpServletResponse response, Developermaster developermasterForm) throws Exception {
		System.out.println("addDeveloper() method called");
		System.out.println("date value "+developermasterForm.getDevEndDate());
		getUserServiceImpl().persist(developermasterForm);
		return new ModelAndView("jiraTicket", "developermasterForm", developermasterForm);
	}
	public ModelAndView addReview(HttpServletRequest request,
			HttpServletResponse response, Developermaster developermasterForm) throws Exception {
		System.out.println("jiraTicket method called::roleName::"+request.getServletContext().getAttribute("roleName"));
		System.out.println("request evelopermasterForm.getTicketNo()::---"+developermasterForm.getTicketNo());
		getUserServiceImpl().updateReview(developermasterForm);

		System.out.println("addReview() method called");
		return new ModelAndView("jiraTicket", "developermasterForm", developermasterForm);
	}
	public ModelAndView addQA(HttpServletRequest request,
			HttpServletResponse response, Developermaster developermasterForm) throws Exception {
		System.out.println("addQA() method called");
				
		System.out.println("request evelopermasterForm.getTicketNo()::---"+developermasterForm.getTicketNo());
		//developermasterForm.setTicketNo(developermasterForm.getTicketNo());
		getUserServiceImpl().updateQA(developermasterForm);
		return new ModelAndView("jiraTicket", "developermasterForm", developermasterForm);
	}
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Developermaster   developermasterForm = new Developermaster();
		System.out.println(" multiuser Add method called"+request.getParameter("id"));
		/*List<Roles> arrayList=getUserServiceImpl().getAllGuests();
		for (Iterator iterator = arrayList.iterator(); iterator.hasNext();) {
			Roles roles = (Roles) iterator.next();
		System.out.println("roles---"+roles.getRoleDescription());	
		}*/
		int id1;
		String id=request.getParameter("id");
		if(id!=null){
				 id1=Integer.parseInt(id);
		}else{
			 id1=8;
		}
		developermasterForm=getUserServiceImpl().find(id1);
		System.out.println("developermasterForm----"+developermasterForm.getTicketNo());
		return  new ModelAndView("jiraTicket", "developermasterForm", developermasterForm);
	}
	public ModelAndView viewAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		jiraTicketList=(ArrayList<Developermaster>) getUserServiceImpl().getAllDevelopermaster();
		return  new ModelAndView("jiraTicketView", "jiraTicketList", jiraTicketList);
	}
	
	public ModelAndView displayUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("mongo-db-config.xml");
		// TODO Auto-generated method stub
		System.out.println("----"+ctx);
		 MongoOperations mongoOperation = 
	               (MongoOperations) ctx.getBean("mongoTemplate");
		 
		;
		  
		  List<User> jiraTicketList=getUserList();
			return  new ModelAndView("displayUser", "displayUser", jiraTicketList);
		 
	}
	
	public ModelAndView addUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("mongo-db-config.xml");
		// TODO Auto-generated method stub
		System.out.println("----"+ctx);
		 MongoOperations mongoOperation = 
	               (MongoOperations) ctx.getBean("mongoTemplate");
		
		  List<User> jiraTicketList=getUserList();
			return  new ModelAndView("displayUser", "displayUser", jiraTicketList);
		 
	}
	
	
	
	static List<User> getUserList(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("mongo-db-config.xml");
		// TODO Auto-generated method stub
		System.out.println("----"+ctx);
		 MongoOperations mongoOperation = 
	               (MongoOperations) ctx.getBean("mongoTemplate");
		  List<User> listUser = mongoOperation.findAll(User.class, "users");

		  return listUser;
	}
	public ModelAndView projects(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println(":::projects:::");
		return  new ModelAndView("projects", "", "");
	}
	
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response, Developermaster developermasterForm)
			throws Exception {

		

		
		getUserServiceImpl().persist(developermasterForm);
		return new ModelAndView("developermaster", "developermasterForm",
				developermasterForm);
		// return new ModelAndView("guest", "roleList", arrayList);
	}

	public ModelAndView remove(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("multiuser Remove method called");
		return new ModelAndView("multiuser", "message", "Remove method called");
	}

	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    //dateFormat.setLenient(false);
    //binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		
	binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
	    public void setAsText(String value) {
	        try {
	            setValue(new SimpleDateFormat("dd/MM/yyyy").parse(value));
	        	DateFormat sortFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
	            System.out.println("getValue()---"+getValue());
	        } catch(ParseException e) {
	        	System.out.println("ParseException----"+e);
	            setValue(null);
	        }
	    }

	    public String getAsText() {
	        return new SimpleDateFormat("dd/MM/yyyy").format((Date) getValue());
	    }        

	});
}

	
	public ModelAndView report(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		return  new ModelAndView("report", "report","report" );
	}
	
}
