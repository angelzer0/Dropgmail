package com.dropgmail.controllers;

import com.dropgmail.model.dto.UsersCreateDto;
import com.dropgmail.model.dto.UsersDto;
import com.dropgmail.model.dto.UsersUpdateDto;
import com.dropgmail.util.UriConstants;
import com.dropgmail.util.UserJwt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UriConstants.USERS)
public interface UsersApi {
    @GetMapping(UriConstants.USER_BY_ID)
    ResponseEntity<UsersDto> getUserById(@PathVariable Integer id);

    @GetMapping
    ResponseEntity<List<UsersDto>> getAllUsers();

    @GetMapping(UriConstants.USERS_SEARCH)
    ResponseEntity<List<UsersDto>> searchUsers(@RequestParam String username);

    @PostMapping
    ResponseEntity<UsersCreateDto> createUser(@RequestBody UsersCreateDto newUser) throws Exception;

    @PostMapping(UriConstants.USERS_LOGIN)
    ResponseEntity<UsersDto> loginUser(@RequestBody UsersDto userDTO);

    @DeleteMapping(UriConstants.USER_BY_ID)
    ResponseEntity<Void> deleteUserById(@PathVariable Integer id);

    @PutMapping(UriConstants.USER_BY_ID)
    ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody UsersUpdateDto updateUser) throws Exception;

    @PostMapping(UriConstants.USERS_AUTH)
    ResponseEntity<UserJwt> authUser(@RequestBody UsersDto userDto) throws Exception;

    @GetMapping(UriConstants.USERS_INFO)
    ResponseEntity<UsersDto> getUserInfo() throws Exception;
}
