package com.grupo3.BookVerse.features.groups.groupComment.repository;


import com.grupo3.BookVerse.features.groups.groupComment.domain.GroupCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupCommentRepository extends JpaRepository<GroupCommentEntity, Long> {

    Optional<GroupCommentEntity> findByIdExternal(UUID idExternal);


    Optional<GroupCommentEntity> findByIdExternalAndIsHiddenFalse(UUID idExternal);

    List<GroupCommentEntity> findByIsHiddenFalseOrderByCreatedAtDesc();

    List<GroupCommentEntity> findByGroupIdExternalAndIsHiddenFalseOrderByCreatedAtAsc(UUID groupId);

    List<GroupCommentEntity> findByUserIdExternalAndIsHiddenFalseOrderByCreatedAtDesc(UUID userId);
}