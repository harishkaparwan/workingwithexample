package com.hck.service;

import java.util.List;
import java.util.UUID;
 
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.hck.model.Events;



public class DocListServices {
	 private MongoTemplate mongoTemplate;
	  
	 /**
	  * Retrieves all Eventss
	  */
	 public List<Events> getAll() {
	  
	  // Find an entry where pid property exists
	        Query query = new Query(Criteria.where("name").is(1));

	        // Execute the query and find all matching entries
	        
	        List<Events> eventsList =  mongoTemplate.find(query,  Events.class);
	         
	  return eventsList;
	 }
	  
	 /**
	  * Retrieves a single Events
	  */
	 public Events get( String id ) {
	   
	  // Find an entry where pid matches the id
	        Query query = new Query(Criteria.where("name").is(1));
	        // Execute the query and find one matching entry
	       // Events Events = mongoTemplate.find("mycollection", query, Events.class);
	        
	  return null;
	 }
	  
	 /**
	  * Adds a new Events
	  */
	 public Boolean add(Events Events) {
	   
	  try {
		  Events events= new Events();
	   // Set a new value to the pid property first since it's blank

		  // Insert to db
		  mongoTemplate.save(events,"events");
	 
	   return true;
	    
	  } catch (Exception e) {
	   return false;
	  }
	 }
	  
	 /**
	  * Deletes an existing Events
	  */
	 public Boolean delete(String id) {
	   
	  try {
	    
	   // Find an entry where pid matches the id
	         Query query = new Query(Criteria.where("name").is(1));
	         // Run the query and delete the entry
	         mongoTemplate.remove(query);
	          
	   return true;
	    
	  } catch (Exception e) {
	   return false;
	  }
	 }
	  
	 /**
	  * Edits an existing Events
	  */
	 public Boolean edit(Events Events) {
	   
		 Events events= new Events();
	  try {
	    
	   // Find an entry where pid matches the id
	         Query query = new Query(Criteria
	 				.where("id").is("2000"));
	          
	   // Declare an Update object.
	         // This matches the update modifiers available in MongoDB
	       Update update = new Update();
	          
	         update.set("firstName", events.getId());
	         mongoTemplate.updateMulti(query, update,Events.class);
	          
	         update.set("lastName", events.getId());
	         mongoTemplate.updateMulti(query, update,Events.class);
	          
	         
	          
	   return true;
	    
	  } catch (Exception e) {
	   return false;
	  }
	   
	 }
}
