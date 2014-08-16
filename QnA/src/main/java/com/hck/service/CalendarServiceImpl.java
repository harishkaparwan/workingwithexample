package com.hck.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hck.model.Event;

public class CalendarServiceImpl {

	// Injected database connection:
    @PersistenceContext 
    private EntityManager em;
 
    
   

	// Stores new ticket details:
    @Transactional
    public void persist(Event event) {
    	em.persist(event);
    }

   
  
   

}
