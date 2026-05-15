package com.grupo3.BookVerse.features.users;

import com.grupo3.BookVerse.features.users.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByIdExternal(UUID idExternal);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByIdExternal(UUID idExternal);

    void deleteByIdExternal(UUID idExternal);

}
