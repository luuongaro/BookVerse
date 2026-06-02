package com.grupo3.BookVerse.features.groups.GroupGoals.repository;

import com.grupo3.BookVerse.features.groups.GroupGoals.domain.GroupGoalsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupGoalsRepository extends JpaRepository<GroupGoalsEntity, Long> {

    List<GroupGoalsEntity> findByGroupId(Long groupId);

}


