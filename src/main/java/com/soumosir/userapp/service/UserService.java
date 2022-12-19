package com.soumosir.userapp.service;

import com.soumosir.userapp.dto.UserPostRequest;
import com.soumosir.userapp.dto.UserPutRequest;
import com.soumosir.userapp.entity.User;
import com.soumosir.userapp.exception.UserExistException;
import com.soumosir.userapp.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface UserService {


    List<User> getAllUsers();
    User getUser(String username) throws UserNotFoundException;

    User saveUser(UserPostRequest userPostRequest) throws UserExistException;
    User updateUserByFields(String username, UserPutRequest userPutRequest) throws UserNotFoundException;
    boolean deleteUser(String username) throws UserNotFoundException;
}
