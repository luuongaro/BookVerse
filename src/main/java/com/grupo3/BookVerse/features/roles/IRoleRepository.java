package com.grupo3.BookVerse.features.roles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByIdExternal(UUID idExternal);
    Optional<RoleEntity> findByName(String name);
    boolean existsByName(String name);

}
