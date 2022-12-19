package com.soumosir.userapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.soumosir.userapp.dto.UserPostRequest;
import com.soumosir.userapp.dto.UserPutRequest;
import com.soumosir.userapp.entity.User;
import com.soumosir.userapp.exception.UserExistException;
import com.soumosir.userapp.exception.UserNotFoundException;
import com.soumosir.userapp.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImplementation.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplementationTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @Test
    void testSaveUser() throws UserExistException {
        User user = new User();
        user.setAddress("3412 Tulane Drive, Maryland");
        user.setDateOfBirth(LocalDate.ofEpochDay(1L));
        user.setId(123L);
        user.setName("Name");
        user.setUsername("soumosir");

        User user1 = new User();
        user1.setAddress("3412 Tulane Drive, Maryland");
        user1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        user1.setId(123L);
        user1.setName("Name");
        user1.setUsername("soumosir");
        Optional<User> ofResult = Optional.of(user1);
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult);

        UserPostRequest userPostRequest = new UserPostRequest();
        userPostRequest.setAddress("3412 Tulane Drive, Maryland");
        userPostRequest.setDateOfBirth(LocalDate.ofEpochDay(1L));
        userPostRequest.setName("Name");
        userPostRequest.setUsername("soumosir");
        assertThrows(UserExistException.class, () -> this.userServiceImplementation.saveUser(userPostRequest));
        verify(this.userRepository).findByUsername((String) any());
    }

    @Test
    void testSaveUserUsernameAlreadyExist() throws UserExistException {
        User user = new User();
        user.setAddress("3412 Tulane Drive, Maryland");
        user.setDateOfBirth(LocalDate.ofEpochDay(1L));
        user.setId(123L);
        user.setName("Name");
        user.setUsername("soumosir");
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.findByUsername((String) any())).thenReturn(Optional.empty());

        UserPostRequest userPostRequest = new UserPostRequest();
        userPostRequest.setAddress("3412 Tulane Drive, Maryland");
        userPostRequest.setDateOfBirth(LocalDate.ofEpochDay(1L));
        userPostRequest.setName("Name");
        userPostRequest.setUsername("soumosir");
        assertSame(user, this.userServiceImplementation.saveUser(userPostRequest));
        verify(this.userRepository).save((User) any());
        verify(this.userRepository).findByUsername((String) any());
    }

    @Test
    void testUpdateUserByFields() throws UserNotFoundException {
        User user = new User();
        user.setAddress("3412 Tulane Drive, Maryland");
        user.setDateOfBirth(LocalDate.ofEpochDay(1L));
        user.setId(123L);
        user.setName("Name");
        user.setUsername("soumosir");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult);

        UserPutRequest userPutRequest = new UserPutRequest();
        userPutRequest.setAddress(Optional.of("42 north street"));
        userPutRequest.setDateOfBirth(Optional.of(LocalDate.ofEpochDay(1L)));
        userPutRequest.setName(Optional.of("42tanSoumosir"));
        userPutRequest.setUsername(Optional.of("42tanSoumosir"));
        User actualUpdateUserByFieldsResult = this.userServiceImplementation.updateUserByFields("soumosir", userPutRequest);
        assertSame(user, actualUpdateUserByFieldsResult);
        assertEquals("42 north street", actualUpdateUserByFieldsResult.getAddress());
        assertEquals("42tanSoumosir", actualUpdateUserByFieldsResult.getUsername());
        assertEquals("42tanSoumosir", actualUpdateUserByFieldsResult.getName());
        assertEquals("1970-01-02", actualUpdateUserByFieldsResult.getDateOfBirth().toString());
        verify(this.userRepository).findByUsername((String) any());
    }


    @Test
    void testDeleteUser() throws UserNotFoundException {
        User user = new User();
        user.setAddress("3412 Tulane Drive, Maryland");
        user.setDateOfBirth(LocalDate.ofEpochDay(1L));
        user.setId(123L);
        user.setName("Name");
        user.setUsername("soumosir");
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(this.userRepository).delete((User) any());
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult);
        assertTrue(this.userServiceImplementation.deleteUser("soumosir"));
        verify(this.userRepository).findByUsername((String) any());
        verify(this.userRepository).delete((User) any());
    }

    @Test
    void testDeleteUserDoesNotExist() throws UserNotFoundException {
        doNothing().when(this.userRepository).delete((User) any());
        when(this.userRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.userServiceImplementation.deleteUser("soumosir"));
        verify(this.userRepository).findByUsername((String) any());
    }

    @Test
    void testGetAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        when(this.userRepository.findAll()).thenReturn(userList);
        List<User> actualAllUsers = this.userServiceImplementation.getAllUsers();
        assertSame(userList, actualAllUsers);
        assertTrue(actualAllUsers.isEmpty());
        verify(this.userRepository).findAll();
    }

    @Test
    void testGetUser() throws UserNotFoundException {
        User user = new User();
        user.setAddress("3412 Tulane Drive, Maryland");
        user.setDateOfBirth(LocalDate.ofEpochDay(1L));
        user.setId(123L);
        user.setName("Name");
        user.setUsername("soumosir");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult);
        assertSame(user, this.userServiceImplementation.getUser("soumosir"));
        verify(this.userRepository).findByUsername((String) any());
    }

    @Test
    void testGetUserDoesNotExist() throws UserNotFoundException {
        when(this.userRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.userServiceImplementation.getUser("soumosir"));
        verify(this.userRepository).findByUsername((String) any());
    }
}

