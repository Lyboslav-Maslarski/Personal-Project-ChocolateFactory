package com.example.chocolatefactory.repositories;

import com.example.chocolatefactory.domain.entities.RoleEntity;
import com.example.chocolatefactory.domain.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(RoleEnum role);
}
