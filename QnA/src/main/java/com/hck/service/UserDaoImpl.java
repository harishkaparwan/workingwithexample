package com.hck.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.hck.model.User;
import com.hck.service.impl.UserDao;

@Component("userDao")
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    public User loadUserByUsername(String username) {
        Query query = this.em
                .createQuery("select u FROM User u where u.username= :username");
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        if (users != null && users.size() == 1) {
            return users.get(0);
        }
        return null;
    }
}