package com.hck.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

public class MongoService {

	public static MongoOperations getMongoOperations(){
	
		ApplicationContext ctx = new ClassPathXmlApplicationContext("mongo-db-config.xml");
		// TODO Auto-generated method stub
		 MongoOperations mongoOperation = 
	               (MongoOperations) ctx.getBean("mongoTemplate");
	
		 return mongoOperation;
	}
	
}
