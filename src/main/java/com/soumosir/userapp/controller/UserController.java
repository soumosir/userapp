package com.soumosir.userapp.controller;

import com.soumosir.userapp.dto.UserRestResponse;
import com.soumosir.userapp.dto.UserPostRequest;
import com.soumosir.userapp.dto.UserPutRequest;
import com.soumosir.userapp.entity.User;
import com.soumosir.userapp.exception.UserExistException;
import com.soumosir.userapp.exception.UserNotFoundException;
import com.soumosir.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/userapp/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<UserRestResponse> saveUser(@RequestBody @Valid UserPostRequest userPostRequest) throws UserExistException {
        return new ResponseEntity<>(new UserRestResponse(userService.saveUser(userPostRequest)), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<UserRestResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers().stream().map(UserRestResponse::new).collect(Collectors.toList()));
    }


    @GetMapping("/{username}")
    public ResponseEntity<UserRestResponse> getUser(@PathVariable String username) throws UserNotFoundException {
        return ResponseEntity.ok(new UserRestResponse(userService.getUser(username)));
    }


    @PutMapping("/{username}")
    public ResponseEntity<UserRestResponse> updateUserFields(@PathVariable String username,@RequestBody @Valid UserPutRequest userPutRequest) throws UserNotFoundException {
        return new ResponseEntity<>(new UserRestResponse(userService.updateUserByFields(username,userPutRequest)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String username) throws UserNotFoundException {
        return  new ResponseEntity<>(userService.deleteUser(username), HttpStatus.OK);
    }

}
