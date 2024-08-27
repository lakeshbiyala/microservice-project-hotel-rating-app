package com.example.user.service.service;

import com.example.user.service.entity.User;

import java.util.List;

public interface UserService {

    //user operations


    //create
    User saveUser(User user);

    //get all users
    List<User> getAllUsers();

    //get single user by id
    User getUser(String userId);

    //TODO:
    //delete
    //update

}
