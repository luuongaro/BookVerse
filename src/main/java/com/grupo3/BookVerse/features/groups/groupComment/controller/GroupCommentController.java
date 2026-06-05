package com.grupo3.BookVerse.features.groups.groupComment.controller;

import com.grupo3.BookVerse.features.groups.groupComment.dto.GroupCommentRequestDto;
import com.grupo3.BookVerse.features.groups.groupComment.dto.GroupCommentResponseDto;
import com.grupo3.BookVerse.features.groups.groupComment.service.GroupCommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/group-comments")
public class GroupCommentController {

    private final GroupCommentService groupCommentService;

    @GetMapping
    public ResponseEntity<List<GroupCommentResponseDto>> findAll() {
        return ResponseEntity.ok(groupCommentService.findAll());
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<GroupCommentResponseDto> findById(
            @PathVariable UUID commentId
    ) {
        return ResponseEntity.ok(groupCommentService.findById(commentId));
    }

    @PostMapping
    public ResponseEntity<GroupCommentResponseDto> create(
            @Valid @RequestBody GroupCommentRequestDto groupCommentRequestDto
    ) {
        return new ResponseEntity<>(
                groupCommentService.save(groupCommentRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<GroupCommentResponseDto> update(
            @PathVariable UUID commentId,
            @Valid @RequestBody GroupCommentRequestDto groupCommentRequestDto
    ) {
        return ResponseEntity.ok(
                groupCommentService.update(
                        commentId,
                        groupCommentRequestDto
                )
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID commentId
    ) {
        groupCommentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupCommentResponseDto>> findByGroupId(
            @PathVariable UUID groupId
    ) {
        return ResponseEntity.ok(
                groupCommentService.findByGroupId(groupId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GroupCommentResponseDto>> findByUserId(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(
                groupCommentService.findByUserId(userId)
        );
    }
}