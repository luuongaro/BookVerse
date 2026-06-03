package com.grupo3.BookVerse.features.groups.groupMember.service.impl;

import com.grupo3.BookVerse.common.exception.DuplicateResourceException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.groups.groupMember.domain.GroupMemberEntity;
import com.grupo3.BookVerse.features.groups.groupMember.dto.GroupMemberRequestDto;
import com.grupo3.BookVerse.features.groups.groupMember.dto.GroupMemberResponseDto;
import com.grupo3.BookVerse.features.groups.groupMember.mappers.GroupMemberMapper;
import com.grupo3.BookVerse.features.groups.groupMember.repository.GroupMemberRepository;
import com.grupo3.BookVerse.features.groups.groupMember.service.GroupMemberService;
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
public class GroupMemberServiceImpl implements GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final GroupMemberMapper groupMemberMapper;
    private final ReadingGroupRepository readingGroupRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GroupMemberResponseDto createGroupMember(GroupMemberRequestDto groupMemberRequestDto) {

        Long groupId = groupMemberRequestDto.getGroupId();
        Long userId = groupMemberRequestDto.getUserId();

        if (groupMemberRepository.existsByGroupIdAndUserId(groupId, userId)) {
            throw new DuplicateResourceException(
                    "User with id " + userId + " is already a member of group with id " + groupId
            );
        }

        ReadingGroupEntity readingGroupEntity = readingGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        GroupMemberEntity groupMemberEntity = groupMemberMapper.toEntity(groupMemberRequestDto);
        groupMemberEntity.setGroup(readingGroupEntity);
        groupMemberEntity.setUser(userEntity);

        GroupMemberEntity savedGroupMember = groupMemberRepository.save(groupMemberEntity);

        return groupMemberMapper.toResponseDto(savedGroupMember);
    }

    @Override
    public List<GroupMemberResponseDto> getAllGroupMembers() {
        List<GroupMemberEntity> groupMembers = groupMemberRepository.findAll();
        return groupMembers.stream()
                .map(groupMemberMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public GroupMemberResponseDto getGroupMemberByIdExternal(UUID idExternal) {
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Group member not found with id: " + idExternal));

        return groupMemberMapper.toResponseDto(groupMemberEntity);
    }

    @Override
    @Transactional
    public List<GroupMemberResponseDto> getGroupMembersByGroupId(Long groupId) {

        if (!readingGroupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id: " + groupId);
        }

        List<GroupMemberEntity> groupMembers = groupMemberRepository.findByGroupId(groupId);

        return groupMembers.stream()
                .map(groupMemberMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public List<GroupMemberResponseDto> getGroupMembersByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        List<GroupMemberEntity> groupMembers = groupMemberRepository.findByUserId(userId);

        return groupMembers.stream()
                .map(groupMemberMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteGroupMember(UUID idExternal) {
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Group member not found with id: " + idExternal));

        groupMemberRepository.delete(groupMemberEntity);
    }
}