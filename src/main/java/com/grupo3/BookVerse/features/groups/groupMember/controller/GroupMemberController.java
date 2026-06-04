package com.grupo3.BookVerse.features.groups.groupMember.controller;

import com.grupo3.BookVerse.features.groups.groupMember.dto.GroupMemberRequestDto;
import com.grupo3.BookVerse.features.groups.groupMember.dto.GroupMemberResponseDto;
import com.grupo3.BookVerse.features.groups.groupMember.service.GroupMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/group-members")
@RequiredArgsConstructor
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    @PostMapping

    public ResponseEntity<GroupMemberResponseDto> createGroupMember(
            @Valid @RequestBody GroupMemberRequestDto groupMemberRequestDto) {

        GroupMemberResponseDto createdGroupMember =
                groupMemberService.createGroupMember(groupMemberRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroupMember);
    }

    @GetMapping
    public ResponseEntity<List<GroupMemberResponseDto>> getAllGroupMembers() {
        List<GroupMemberResponseDto> groupMembers = groupMemberService.getAllGroupMembers();
        return ResponseEntity.ok(groupMembers);
    }

    @GetMapping("/{idExternal}")
    public ResponseEntity<GroupMemberResponseDto> getGroupMemberByIdExternal(
            @PathVariable UUID idExternal) {
        GroupMemberResponseDto groupMember =
                groupMemberService.getGroupMemberByIdExternal(idExternal);

        return ResponseEntity.ok(groupMember);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupMemberResponseDto>> getGroupMembersByGroupId(
            @PathVariable Long groupId) {
        List<GroupMemberResponseDto> groupMembers =
                groupMemberService.getGroupMembersByGroupId(groupId);

        return ResponseEntity.ok(groupMembers);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GroupMemberResponseDto>> getGroupMembersByUserId(
            @PathVariable Long userId) {
        List<GroupMemberResponseDto> groupMembers =
                groupMemberService.getGroupMembersByUserId(userId);

        return ResponseEntity.ok(groupMembers);
    }

    @DeleteMapping("/{idExternal}")
    public ResponseEntity<Void> deleteGroupMember(@PathVariable UUID idExternal) {
        groupMemberService.deleteGroupMember(idExternal);
        return ResponseEntity.noContent().build();
    }

}
