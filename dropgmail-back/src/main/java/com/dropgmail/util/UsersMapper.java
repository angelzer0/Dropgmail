package com.dropgmail.util;

import com.dropgmail.model.dto.UsersCreateDto;
import com.dropgmail.model.dto.UsersDto;
import com.dropgmail.model.dto.UsersUpdateDto;
import com.dropgmail.model.entities.Users;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UsersMapper {
    public Users userDTOToEntity(UsersDto usersDTO) {
        return Users.builder()
                .id(usersDTO.getId())
                .name(usersDTO.getName())
                .username(usersDTO.getUsername())
                .password(usersDTO.getPassword())
                .email(usersDTO.getEmail())
                .role(usersDTO.getRole())
                .available(usersDTO.getAvailable())
                .build();
    }

    public UsersDto userEntityToDTO(Users user) {
        return UsersDto.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .available(user.getAvailable())
                .build();
    }

    public List<Users> listUserDTOtoEntity(List<UsersDto> listUsersDto) {
        return listUsersDto.stream().map(UsersMapper::userDTOToEntity).toList();
    }

    public List<UsersDto> listUserEntityToDto(List<Users> listUsers) {
        return listUsers.stream().map(UsersMapper::userEntityToDTO).toList();
    }

    public Users usersCreateDtoToEntity(UsersCreateDto userCreateDto) {
        return Users.builder()
                .username(userCreateDto.getUsername())
                .password(userCreateDto.getPassword())
                .email(userCreateDto.getEmail())
                .role(userCreateDto.getRole())
                .available(userCreateDto.getAvailable())
                .build();
    }

    public UsersCreateDto usersCreateEntityToDto(Users user) {
        return UsersCreateDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }

    public List<Users> listUserCreateDtoToEntity(List<UsersCreateDto> listUsersCreateDto) {
        return listUsersCreateDto.stream().map(UsersMapper::usersCreateDtoToEntity).toList();
    }

    public List<UsersCreateDto> listUserCreateEntityToDto(List<Users> listUsers) {
        return listUsers.stream().map(UsersMapper::usersCreateEntityToDto).toList();
    }

    public Users usersUpdateDtoToEntity(UsersUpdateDto usersUpdateDto) {
        return Users.builder()
                .name(usersUpdateDto.getName())
                .username(usersUpdateDto.getUsername())
                .password(usersUpdateDto.getPassword())
                .email(usersUpdateDto.getEmail())
                .available(usersUpdateDto.getAvailable())
                .build();
    }


    public UsersUpdateDto usersUpdateEntityToDto(Users user) {
        return UsersUpdateDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .available(user.getAvailable())
                .build();
    }

    public List<Users> listUsersUpdateDtoToEntity(List<UsersUpdateDto> listUsersUpdateDto) {
        return listUsersUpdateDto.stream().map(UsersMapper::usersUpdateDtoToEntity).toList();
    }

    public List<UsersUpdateDto> listUserUpdateEntityToDto(List<Users> listUsers) {
        return listUsers.stream().map(UsersMapper::usersUpdateEntityToDto).toList();
    }
}
