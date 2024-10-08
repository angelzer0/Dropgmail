package com.dropgmail.controllers.impl;

import com.dropgmail.controllers.UsersApi;
import com.dropgmail.model.dto.UsersCreateDto;
import com.dropgmail.model.dto.UsersDto;
import com.dropgmail.model.dto.UsersUpdateDto;
import com.dropgmail.services.UsersService;
import com.dropgmail.util.UserJwt;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {

    private final UsersService usersService;

    @Override
    public ResponseEntity<UsersDto> getUserById(Integer id) {
        UsersDto user = usersService.getUserById(id).get();
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<UsersDto>> getAllUsers() {
        List<UsersDto> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<List<UsersDto>> searchUsers(String username) {
        List<UsersDto> listName = usersService.searchUsersByUserName(username);
        return ResponseEntity.ok(listName);
    }

    @Override
    public ResponseEntity<UsersCreateDto> createUser(UsersCreateDto newUser) throws Exception {
        try {
            UsersCreateDto createdUser = usersService.createUser(newUser).get();
            return ResponseEntity.ok(createdUser);
        } catch (ExecutionControl.UserException userException) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Integer id) {
        usersService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateUser(Integer id, UsersUpdateDto updateUser) throws Exception {
        usersService.updateUser(id, updateUser);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UsersDto> loginUser(UsersDto userDTO) {
        Optional<UsersDto> loggedInUser = usersService.loginUser(userDTO.getEmail(), userDTO.getPassword());
        return loggedInUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<UserJwt> authUser(@RequestBody UsersDto userDTO) throws Exception {
        UserJwt userJwt = usersService.createAuthenticationToken(userDTO);
        return ResponseEntity.ok(userJwt);
    }

    @Override
    public ResponseEntity<UsersDto> getUserInfo() {
        UsersDto usersDTO = usersService.getUserInformation().get();
        return ResponseEntity.ok(usersDTO);
    }
}
