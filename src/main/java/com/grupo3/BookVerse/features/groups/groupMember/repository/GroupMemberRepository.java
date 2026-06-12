package com.grupo3.BookVerse.features.groups.groupMember.repository;

import com.grupo3.BookVerse.features.groups.groupMember.domain.GroupMemberEntity;
import com.grupo3.BookVerse.features.groups.groupMember.domain.GroupMemberStatus;
import com.grupo3.BookVerse.features.groups.groupMember.domain.GroupMemberType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {

    Optional<GroupMemberEntity> findByIdExternal(UUID idExternal);

    Optional<GroupMemberEntity> findByGroupIdExternalAndUserIdExternal(UUID groupId, UUID userId);

    List<GroupMemberEntity> findByGroupIdExternalAndStatus(UUID groupId, GroupMemberStatus status);

    List<GroupMemberEntity> findByUserIdExternalAndStatus(UUID userId, GroupMemberStatus status);

    boolean existsByGroupIdExternalAndUserIdExternalAndStatus(
            UUID groupId,
            UUID userId,
            GroupMemberStatus status
    );

    boolean existsByGroupIdExternalAndUserIdExternalAndMemberTypeAndStatus(
            UUID groupId,
            UUID userId,
            GroupMemberType memberType,
            GroupMemberStatus status
    );
}