package com.grupo3.BookVerse.features.groups.groupComment.service.impl;

import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;

import com.grupo3.BookVerse.features.groups.groupComment.domain.GroupCommentEntity;
import com.grupo3.BookVerse.features.groups.groupComment.dto.GroupCommentRequestDto;
import com.grupo3.BookVerse.features.groups.groupComment.dto.GroupCommentResponseDto;
import com.grupo3.BookVerse.features.groups.groupComment.mappers.GroupCommentMapper;
import com.grupo3.BookVerse.features.groups.groupComment.repository.GroupCommentRepository;
import com.grupo3.BookVerse.features.groups.groupComment.service.GroupCommentService;
import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import com.grupo3.BookVerse.features.groups.readingGroups.repository.ReadingGroupRepository;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupCommentServiceImpl implements GroupCommentService {

    private final GroupCommentRepository groupCommentRepository;
    private final GroupCommentMapper groupCommentMapper;
    private final ReadingGroupRepository readingGroupRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GroupCommentResponseDto save(
            GroupCommentRequestDto groupCommentRequestDto
    ) {

        ReadingGroupEntity group =
                findGroupByIdExternal(groupCommentRequestDto.getGroupId());

        UserEntity user =
                findUserByIdExternal(groupCommentRequestDto.getUserId());

        GroupCommentEntity comment =
                groupCommentMapper.toEntity(groupCommentRequestDto);

        comment.setGroup(group);
        comment.setUser(user);

        GroupCommentEntity savedComment =
                groupCommentRepository.save(comment);

        return groupCommentMapper.toResponseDto(savedComment);
    }

    @Override
    public List<GroupCommentResponseDto> findAll() {

        List<GroupCommentEntity> comments =
                groupCommentRepository.findByIsHiddenFalseOrderByCreatedAtDesc();

        return groupCommentMapper.toResponseDtoList(comments);
    }

    @Override
    public GroupCommentResponseDto findById(UUID commentId) {

        GroupCommentEntity comment =
                findActiveCommentByIdExternal(commentId);

        return groupCommentMapper.toResponseDto(comment);
    }

    @Override
    @Transactional
    public GroupCommentResponseDto update(
            UUID commentId,
            GroupCommentRequestDto groupCommentRequestDto
    ) {

        GroupCommentEntity comment =
                findActiveCommentByIdExternal(commentId);

        ReadingGroupEntity group =
                findGroupByIdExternal(groupCommentRequestDto.getGroupId());

        UserEntity user =
                findUserByIdExternal(groupCommentRequestDto.getUserId());

        comment.setContent(groupCommentRequestDto.getContent());
        comment.setProgressMilestone(
                groupCommentRequestDto.getProgressMilestone()
        );
        comment.setGroup(group);
        comment.setUser(user);

        GroupCommentEntity updatedComment =
                groupCommentRepository.save(comment);

        return groupCommentMapper.toResponseDto(updatedComment);
    }

    @Override
    @Transactional
    public void delete(UUID commentId) {

        GroupCommentEntity comment =
                findActiveCommentByIdExternal(commentId);

        comment.setHidden(true);

        groupCommentRepository.save(comment);
    }

    @Override
    public List<GroupCommentResponseDto> findByGroupId(UUID groupId) {

        findGroupByIdExternal(groupId);

        List<GroupCommentEntity> comments =
                groupCommentRepository
                        .findByGroupIdExternalAndIsHiddenFalseOrderByCreatedAtAsc(groupId);

        return groupCommentMapper.toResponseDtoList(comments);
    }

    @Override
    public List<GroupCommentResponseDto> findByUserId(UUID userId) {

        findUserByIdExternal(userId);

        List<GroupCommentEntity> comments =
                groupCommentRepository
                        .findByUserIdExternalAndIsHiddenFalseOrderByCreatedAtDesc(userId);

        return groupCommentMapper.toResponseDtoList(comments);
    }

    private GroupCommentEntity findActiveCommentByIdExternal(UUID idExternal) {

        return groupCommentRepository
                .findByIdExternalAndIsHiddenFalse(idExternal)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Group comment not found with idExternal: " + idExternal
                        ));
    }

    private ReadingGroupEntity findGroupByIdExternal(UUID groupId) {

        return readingGroupRepository
                .findByIdExternal(groupId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reading group not found with idExternal: " + groupId
                        ));
    }

    private UserEntity findUserByIdExternal(UUID userId) {

        return userRepository
                .findByIdExternal(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with idExternal: " + userId
                        ));
    }
}