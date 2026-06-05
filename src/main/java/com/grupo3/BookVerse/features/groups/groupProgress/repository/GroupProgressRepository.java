package com.grupo3.BookVerse.features.groups.groupProgress.repository;

import com.grupo3.BookVerse.features.groups.groupProgress.domain.GroupProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupProgressRepository extends JpaRepository<GroupProgressEntity, Long> {

    Optional<GroupProgressEntity> findByIdExternal(UUID idExternal);

    List<GroupProgressEntity> findAllByOrderByUpdatedAtDesc();

    List<GroupProgressEntity> findByGroupIdOrderByUpdatedAtDesc(Long groupId);

    List<GroupProgressEntity> findByUserIdOrderByUpdatedAtDesc(Long userId);

    Optional<GroupProgressEntity> findByGroupIdAndUserId(Long groupId, Long userId);
}

