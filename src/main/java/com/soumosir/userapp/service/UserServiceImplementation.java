package com.soumosir.userapp.service;

import com.soumosir.userapp.dto.UserPostRequest;
import com.soumosir.userapp.dto.UserPutRequest;
import com.soumosir.userapp.entity.User;
import com.soumosir.userapp.exception.UserExistException;
import com.soumosir.userapp.exception.UserNotFoundException;
import com.soumosir.userapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImplementation implements  UserService{

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserPostRequest userPostRequest) throws UserExistException {

        if(!userRepository.findByUsername(userPostRequest.getUsername()).isEmpty()){
            throw new UserExistException(String.format("User with same username %s already exists.", userPostRequest.getUsername()));
        }

        User user = User.build(null,
                userPostRequest.getName(),
                userPostRequest.getUsername(),
                userPostRequest.getDateOfBirth(),
                userPostRequest.getAddress());
        log.info("Saving user with username {}.", userPostRequest.getUsername());
        return userRepository.save(user);
    }

    @Override @Transactional
    public User updateUserByFields(String username, UserPutRequest userPutRequest) throws UserNotFoundException {
        User user = findUserByUsername(username);
        User updatedUser  = updateUserByFieldsUtil(user,userPutRequest);
        log.info("Updating user with username {}.",username);
        return updatedUser;
    }

    @Override
    public boolean deleteUser(String username) throws UserNotFoundException {
        User user = findUserByUsername(username);
        userRepository.delete(user);
        log.info("Deleting user with username {}.",username);
        return true;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String username) throws UserNotFoundException{
        return findUserByUsername(username);
    }

    private User findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException(String.format("User with username %s does not exists.",username)));
    }

    private User updateUserByFieldsUtil(User user,UserPutRequest userPutRequest){
        if(userPutRequest.getUsername()!=null) user.setUsername(userPutRequest.getUsername().get());
        if(userPutRequest.getDateOfBirth()!=null) user.setDateOfBirth(userPutRequest.getDateOfBirth().get());
        if(userPutRequest.getName()!=null) user.setName(userPutRequest.getName().get());
        if(userPutRequest.getAddress()!=null) user.setAddress(userPutRequest.getAddress().get());
        return user;
    }

}
