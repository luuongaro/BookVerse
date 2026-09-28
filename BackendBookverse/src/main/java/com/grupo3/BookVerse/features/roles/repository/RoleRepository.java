package com.grupo3.BookVerse.features.roles.repository;

import com.grupo3.BookVerse.features.roles.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByIdExternal(UUID idExternal);

    Optional<RoleEntity> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
