package com.dropgmail.repository;


import com.dropgmail.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Users que proporciona operaciones CRUD.
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findUserByEmailEqualsIgnoreCase(String email);

    Users findUserByUsernameEqualsIgnoreCase(String userName);

    List<Users> findUserByUsernameContainingIgnoreCase(String userName);

    List<Users> findAllByAvailableTrue();

    Users findUserByIdAndAvailableTrue(Integer id);

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsernameIgnoreCase(String userName);

}
