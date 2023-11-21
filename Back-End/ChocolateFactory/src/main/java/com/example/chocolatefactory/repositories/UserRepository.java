package com.example.chocolatefactory.repositories;

import com.example.chocolatefactory.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByEmailNot(String mail);
}
