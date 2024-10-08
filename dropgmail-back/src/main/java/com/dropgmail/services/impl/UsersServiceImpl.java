package com.dropgmail.services.impl;

import com.dropgmail.jwt.JwtUserDetailsService;
import com.dropgmail.model.dto.UsersCreateDto;
import com.dropgmail.model.dto.UsersDto;
import com.dropgmail.model.dto.UsersUpdateDto;
import com.dropgmail.model.entities.Users;
import com.dropgmail.repository.UsersRepository;
import com.dropgmail.services.UsersService;
import com.dropgmail.util.MessageConstants;
import com.dropgmail.util.UserJwt;
import com.dropgmail.util.UsersMapper;
import com.dropgmail.util.exception.UserException;
import com.dropgmail.util.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static com.dropgmail.model.entities.Role.USER;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Optional<UsersDto> getUserById(Integer id) {
        return Optional.ofNullable(usersRepository.findUserByIdAndAvailableTrue(id))
                .map(UsersMapper::userEntityToDTO)
                .or(() -> {
                    throw new NoSuchElementException(MessageConstants.USER_NOT_AVAILABLE);
                });
    }

    @Override
    public List<UsersDto> getAllUsers() {
        return UsersMapper.listUserEntityToDto(usersRepository.findAllByAvailableTrue());
    }

    @Override
    public List<UsersDto> searchUsersByUserName(String userName) {
        List<Users> usersToFind = usersRepository.findUserByUsernameContainingIgnoreCase(userName);

        return UsersMapper.listUserEntityToDto(usersToFind
                .stream()
                .filter(Users::getAvailable)
                .toList());
    }

    @Override
    public Optional<UsersDto> findUserByUsername(String userName) {
        Users user = usersRepository.findUserByUsernameEqualsIgnoreCase(userName);
        if (user.getAvailable()) {
            return Optional.of(UsersMapper.userEntityToDTO(user));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UsersCreateDto> createUser(UsersCreateDto newUser) throws Exception {
        newUser.setRole(USER);
        newUser.setAvailable(true);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if (Objects.nonNull(usersRepository.findUserByUsernameEqualsIgnoreCase(newUser.getUsername()))) {
            throw UserException.userNameExistingException();
        }

        if (Objects.nonNull(usersRepository.findUserByEmailEqualsIgnoreCase(newUser.getEmail()))) {
            throw UserException.emailExistingException();
        }

        Users savedUser = usersRepository.save(UsersMapper.usersCreateDtoToEntity(newUser));
        return Optional.of(UsersMapper.usersCreateEntityToDto(savedUser));
    }


    @Override
    public void deleteUserById(Integer userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(MessageConstants.USER_NOT_FOUND));
        user.setAvailable(false);
        usersRepository.save(user);
    }

    @Override
    public Optional<UsersUpdateDto> updateUser(Integer userId, UsersUpdateDto updateUser) throws Exception {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.USER_NOT_FOUND));

        Users updatedUser = UsersMapper.usersUpdateDtoToEntity(updateUser);
        updatedUser.setId(userId);
        updatedUser.setRole(user.getRole());
        updatedUser.setAvailable(true);

        Users savedUser = usersRepository.save(updatedUser);
        return Optional.of(UsersMapper.usersUpdateEntityToDto(savedUser));
    }

    @Override
    public Optional<UsersDto> loginUser(String identifier, String password) {
        Optional<Users> user = usersRepository.findByEmail(identifier)
                .or(() -> usersRepository.findByUsernameIgnoreCase(identifier));

        return user.filter(u -> password.equals(u.getPassword()))
                .map(UsersMapper::userEntityToDTO);
    }

    @Override
    public UserJwt createAuthenticationToken(UsersDto authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return UserJwt.builder()
                .token(token)
                .build();
    }

    @Override
    public Optional<UsersDto> getUserInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Users> dbUser = usersRepository.findByUsernameIgnoreCase(user.getUsername());

        return Optional.ofNullable(UsersMapper.userEntityToDTO(dbUser.get()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
