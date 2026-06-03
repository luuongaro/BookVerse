package com.grupo3.BookVerse.features.groups.groupMember.repository;

import com.grupo3.BookVerse.features.groups.groupMember.domain.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {


    Optional<GroupMemberEntity> findByIdExternal(UUID idExternal);

    List<GroupMemberEntity> findByGroupId(Long groupId);

    List<GroupMemberEntity> findByUserId(Long userId);

    boolean existsByGroupIdAndUserId(Long groupId, Long userId);


}
