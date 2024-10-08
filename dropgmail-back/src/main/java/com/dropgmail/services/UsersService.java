package com.dropgmail.services;

import com.dropgmail.model.dto.UsersCreateDto;
import com.dropgmail.model.dto.UsersDto;
import com.dropgmail.model.dto.UsersUpdateDto;
import com.dropgmail.util.UserJwt;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    Optional<UsersDto> getUserById(Integer id);

    List<UsersDto> getAllUsers();

    List<UsersDto> searchUsersByUserName(String userName);

    Optional<UsersDto> findUserByUsername(String Username);

    Optional<UsersCreateDto> createUser(UsersCreateDto newUser) throws Exception;

    void deleteUserById(Integer userId);

    Optional<UsersUpdateDto> updateUser(Integer UserId, UsersUpdateDto updateUser) throws Exception;

    Optional<UsersDto> loginUser(String identifier, String password);

    UserJwt createAuthenticationToken(@RequestBody UsersDto authenticationRequest) throws Exception;

    Optional<UsersDto> getUserInformation();
}
