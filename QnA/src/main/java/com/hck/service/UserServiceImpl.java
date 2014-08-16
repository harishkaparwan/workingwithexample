package com.hck.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.hck.model.Developermaster;

public class UserServiceImpl {

	// Injected database connection:
    @PersistenceContext private EntityManager em;
 
    // Stores new ticket details:
    @Transactional
    public void persist(Developermaster developermasterForm) {
        em.merge(developermasterForm);
    }
    
    @Transactional
    public void merge(Developermaster developermasterForm) {
        em.merge(developermasterForm);
    }
 
    @Transactional
    public void updateQA(Developermaster developermasterForm) {
        
    	Developermaster developermaster=em.find(Developermaster.class, developermasterForm.getTicketNo());
    	developermaster.setQAComments(developermasterForm.getQAComments());
    	developermaster.setQAEndDate(developermasterForm.getQAEndDate());
    	developermaster.setQAInstructions(developermasterForm.getQAInstructions());
    	developermaster.setQAIssuesfound(developermasterForm.getQAIssuesfound());
    	developermaster.setQAStartDate(developermasterForm.getQAStartDate());
    	em.merge(developermaster);
        
    }
 
    @Transactional
    public void updateReview(Developermaster developermasterForm) {
        
    	Developermaster developermaster=em.find(Developermaster.class, developermasterForm.getTicketNo());
    	developermaster.setReviewComments(developermasterForm.getReviewComments());
    	developermaster.setReviewedBy(developermasterForm.getReviewedBy());
    	developermaster.setReviewedOn(developermasterForm.getReviewedOn());
    	em.merge(developermaster);
        
    }
  
    public Developermaster find(final Object id) {
        return (Developermaster) this.em.find(Developermaster.class, id);
    }

    public List<Developermaster> getAllDevelopermaster() {
    	ArrayList<Developermaster> linkedList = new ArrayList<Developermaster>();
    	String queryString="Select developermaster from Developermaster developermaster";
    	linkedList =   (ArrayList<Developermaster>) this.em.createQuery(queryString).getResultList();
    	System.out.println("linkedList----"+linkedList);
        return linkedList;
    }
}
