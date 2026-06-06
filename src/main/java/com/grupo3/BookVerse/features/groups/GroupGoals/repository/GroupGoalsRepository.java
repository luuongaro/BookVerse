package com.grupo3.BookVerse.features.groups.GroupGoals.repository;

import com.grupo3.BookVerse.features.groups.GroupGoals.domain.GroupGoalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupGoalsRepository extends JpaRepository<GroupGoalsEntity, Long> {

    Optional<GroupGoalsEntity> findByIdExternal(UUID idExternal);

    List<GroupGoalsEntity> findByGroupIdExternal(UUID groupId);
}
