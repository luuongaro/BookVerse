package com.grupo3.BookVerse.features.groups.groupGoals.repository;

import com.grupo3.BookVerse.features.groups.groupGoals.domain.GoalStatus;
import com.grupo3.BookVerse.features.groups.groupGoals.domain.GroupGoalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupGoalsRepository extends JpaRepository<GroupGoalsEntity, Long> {


    Optional<GroupGoalsEntity> findByIdExternal(UUID idExternal);

    List<GroupGoalsEntity> findByGroup_IdExternalOrderByUpdatedAtDesc(UUID groupId);

    Optional<GroupGoalsEntity> findByGroup_IdExternalAndStatus(UUID groupId, GoalStatus status);

    boolean existsByGroup_IdExternalAndStatus(UUID groupId, GoalStatus status);

    Optional<GroupGoalsEntity> findTopByGroup_IdExternalOrderByUpdatedAtDesc(UUID groupId);
}
