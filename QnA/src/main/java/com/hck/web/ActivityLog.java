package com.hck.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.hck.model.Events;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * Servlet implementation class ActivityLog
 */
public class ActivityLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivityLog() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("mongo-db-config.xml");
		// TODO Auto-generated method stub
		System.out.println("doGet-www-");
		 MongoOperations mongoOperation = 
	               (MongoOperations) ctx.getBean("mongoTemplate");
		  List<Events> listUser = mongoOperation.findAll(Events.class, "events");
		  	ObjectMapper mapper = new ObjectMapper();
			 String str=mapper.writeValueAsString(listUser.toString());
			 response.setContentType("application/json");
			 String strreplace=str.replace("GMT ", "GMT+");
			 String finalString= "{ 'events' : "+ strreplace + " } ";
			 String f2=finalString.replace("\"", " ");
			 String f3=f2.replace("'", "\"");
			 response.getWriter().write(f3);
			 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 System.out.println("--System.getenv()---"+System.getenv());
		 
		 System.out.println("--System.getenv(mongo.local.hostname)---"+System.getenv("mongo.local.hostname"));

		 System.out.println("--System.getenv(mongo.local.port)---"+System.getenv("mongo.local.port"));

		 System.out.println("--System.getenv(mongo.remote.hostname)---"+System.getenv("mongo.remote.hostname"));
		 System.out.println("--System.getenv(mongo.remote.port)---"+System.getenv("mongo.remote.port"));



		String methodType=request.getParameter("methodType");
		if(methodType.equalsIgnoreCase("insert")){
		 insertActivity(request,response);
		}
		else if(methodType.equalsIgnoreCase("update")) {
			updateActivity(request,response);
		}
		
	}
	protected void updateActivity(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("mongo-db-config.xml");
		// TODO Auto-generated method stub
		 MongoOperations mongoOperation = 
	               (MongoOperations) ctx.getBean("mongoTemplate");
	 System.out.println("--id---"+request.getParameter("id"));
	 System.out.println("--mongoOperation---"+mongoOperation);
	 
	 Events events = new Events();
 	 events.setId(Integer.parseInt(request.getParameter("id")));
	 events.setStart(request.getParameter("start"));
	 events.setEnd(request.getParameter("end"));
	 events.setTitle(request.getParameter("title"));
	 events.setBody(request.getParameter("title"));
	 events.setUserId(Integer.parseInt(request.getParameter("userId")));
	 mongoOperation.save(events,"events");
	}
	
	
	protected void insertActivity(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{

		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("mongo-db-config.xml");
		// TODO Auto-generated method stub
		 MongoOperations mongoOperation = 
	               (MongoOperations) ctx.getBean("mongoTemplate");
		 System.out.println("--ctx---"+ctx);
		 System.out.println("--mongoOperation---"+mongoOperation);
		 	 Events events = new Events();
		 	 events.setId(Integer.parseInt(getId()));
			 events.setStart(request.getParameter("start"));
			 events.setEnd(request.getParameter("end"));
			 events.setTitle(request.getParameter("title"));
			 events.setBody(request.getParameter("title"));
			 events.setUserId(Integer.parseInt(request.getParameter("userId")));
			
			 mongoOperation.save(events,"events");
	
	}
	
	/**
	 * Get the next unique ID for a named sequence.
	 * @param db Mongo database to work with
	 * @param seq_name The name of your sequence (I name mine after my collections)
	 * @return The next ID
	 */
	public static String getNextId(DB db, String seq_name) {
	    String sequence_collection = "events"; // the name of the sequence collection
	    String sequence_field = "_id"; // the name of the field which holds the sequence
	 
	    DBCollection seq = db.getCollection(sequence_collection); // get the collection (this will create it if needed)
	 
	    // this object represents your "query", its analogous to a WHERE clause in SQL
	    DBObject query = new BasicDBObject();
	    query.put("_id", seq_name); // where _id = the input sequence name
	 
	    // this object represents the "update" or the SET blah=blah in SQL
	    DBObject change = new BasicDBObject(sequence_field, 1);
	    DBObject update = new BasicDBObject("$inc", change); // the $inc here is a mongodb command for increment
	 
	    // Atomically updates the sequence field and returns the value for you
	    DBObject res = seq.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
	    return res.get(sequence_field).toString();
	}
	
	
	private int increaseCounter(String counterName){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("mongo-db-config.xml");
		// TODO Auto-generated method stub
		 MongoOperations mongoOperation = 
	               (MongoOperations) ctx.getBean("mongoTemplate");
        Query query = new Query(Criteria.where("name").is(counterName));
        Update update = new Update().inc("sequence", 1);
        Events counter = mongoOperation.findAndModify(query, update, Events.class); // return old Counter object
        System.out.println("counter.getId()--"+counter.getId());
        return counter.getId();
    }

public String getId(){
	ApplicationContext ctx = new ClassPathXmlApplicationContext("mongo-db-config.xml");
	// TODO Auto-generated method stub
	 MongoOperations mongoOperation = 
               (MongoOperations) ctx.getBean("mongoTemplate");
	DBCollection seq = mongoOperation.getCollection("counters1");
  	DBObject query = new BasicDBObject();
    query.put("_id", "userid"); 
    DBObject change = new BasicDBObject("userid", 2);
    DBObject update = new BasicDBObject("$inc", change);
    DBObject res = seq.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
    System.out.println("res.get(sequence_field).toString();---"+res.get("userid").toString());
    return res.get("userid").toString();
}

}


