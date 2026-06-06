package com.grupo3.BookVerse.features.groups.groupComment.repository;


import com.grupo3.BookVerse.features.groups.groupComment.domain.GroupCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupCommentRepository extends JpaRepository<GroupCommentEntity, Long> {

    Optional<GroupCommentEntity> findByIdExternalAndHiddenFalse(UUID idExternal);

    List<GroupCommentEntity> findByHiddenFalseOrderByCreatedAtDesc();

    List<GroupCommentEntity> findByGroupIdExternalAndHiddenFalseOrderByCreatedAtAsc(UUID groupId);

    List<GroupCommentEntity> findByUserIdExternalAndHiddenFalseOrderByCreatedAtDesc(UUID userId);
}