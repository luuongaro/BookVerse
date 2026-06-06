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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        ReadingGroupEntity group = findGroupByIdExternal(groupMemberRequestDto.getGroupId());
        UserEntity user = findUserByIdExternal(groupMemberRequestDto.getUserId());

        if (groupMemberRepository.existsByGroupIdAndUserId(group.getId(), user.getId())) {
            throw new DuplicateResourceException(
                    "User with idExternal " + user.getIdExternal()
                            + " is already a member of group with idExternal " + group.getIdExternal()
            );
        }

        GroupMemberEntity groupMemberEntity = groupMemberMapper.toEntity(groupMemberRequestDto);
        groupMemberEntity.setGroup(group);
        groupMemberEntity.setUser(user);

        GroupMemberEntity savedGroupMember = groupMemberRepository.save(groupMemberEntity);

        return groupMemberMapper.toResponseDto(savedGroupMember);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupMemberResponseDto> getAllGroupMembers() {
        List<GroupMemberEntity> groupMembers = groupMemberRepository.findAll();
        return groupMemberMapper.toResponseDtoList(groupMembers);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupMemberResponseDto getGroupMemberByIdExternal(UUID idExternal) {
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group member not found with idExternal: " + idExternal
                ));

        return groupMemberMapper.toResponseDto(groupMemberEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupMemberResponseDto> getGroupMembersByGroupId(UUID groupId) {

        findGroupByIdExternal(groupId);

        List<GroupMemberEntity> groupMembers = groupMemberRepository.findByGroupIdExternal(groupId);

        return groupMemberMapper.toResponseDtoList(groupMembers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupMemberResponseDto> getGroupMembersByUserId(UUID userId) {

        findUserByIdExternal(userId);

        List<GroupMemberEntity> groupMembers = groupMemberRepository.findByUserIdExternal(userId);

        return groupMemberMapper.toResponseDtoList(groupMembers);
    }

    @Override
    @Transactional
    public void deleteGroupMember(UUID idExternal) {
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group member not found with idExternal: " + idExternal
                ));

        groupMemberRepository.delete(groupMemberEntity);
    }

    private ReadingGroupEntity findGroupByIdExternal(UUID groupId) {
        return readingGroupRepository.findByIdExternal(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group not found with idExternal: " + groupId
                ));
    }

    private UserEntity findUserByIdExternal(UUID userId) {
        return userRepository.findByIdExternal(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with idExternal: " + userId
                ));
    }
}