package com.grupo3.BookVerse.features.groups.groupComment.service;

import com.grupo3.BookVerse.features.groups.groupComment.dto.GroupCommentRequestDto;
import com.grupo3.BookVerse.features.groups.groupComment.dto.GroupCommentResponseDto;

import java.util.List;
import java.util.UUID;

public interface GroupCommentService {

    GroupCommentResponseDto save(GroupCommentRequestDto groupCommentRequestDto);

    List<GroupCommentResponseDto> findAll();

    GroupCommentResponseDto findById(UUID commentId);

    GroupCommentResponseDto update(
            UUID commentId,
            GroupCommentRequestDto groupCommentRequestDto
    );

    void delete(UUID commentId);

    List<GroupCommentResponseDto> findByGroupId(UUID groupId);

    List<GroupCommentResponseDto> findByUserId(UUID userId);
}