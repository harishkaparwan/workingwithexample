package com.hck.service.impl;

import com.hck.model.User;


public interface UserDao extends GenericDao<User> {
    /**
     * Returns an User object that matches the username given
     *
     * @param username
     * @return
     */
    public User loadUserByUsername(String username);
}