package com.hck.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.hck.model.Developermaster;
import com.hck.model.User;

public class LoginServiceImpl {

	// Injected database connection:
    @PersistenceContext private EntityManager em;
 
  
  
    public User findByUsername(String userNamePassword) {
    	
    	Query query =(Query) em.createQuery(" from User user where "+userNamePassword);
        List<User> listUser= query.getResultList();
        System.out.println(" from User user where "+userNamePassword);
        System.out.println(listUser.iterator().next());
        if(listUser.size()>0){
        return listUser.iterator().next();
        }
        else{
        	throw new  UsernameNotFoundException("Invalid");
        }
    }

}
