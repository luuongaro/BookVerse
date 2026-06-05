package com.grupo3.BookVerse.features.groups.groupProgress;

import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressRequestDto;
import com.grupo3.BookVerse.features.groups.groupProgress.dto.GroupProgressResponseDto;
import com.grupo3.BookVerse.features.groups.groupProgress.service.GroupProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/group-progress")
@RequiredArgsConstructor
public class GroupProgressController {

    private final GroupProgressService groupProgressService;

    @PostMapping
    public ResponseEntity<GroupProgressResponseDto> createOrUpdateProgress(
            @Valid @RequestBody GroupProgressRequestDto requestDto) {

        return new ResponseEntity<>(
                groupProgressService.createOrUpdateProgress(requestDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<GroupProgressResponseDto>> getAllProgress() {
        return ResponseEntity.ok(groupProgressService.getAllProgress());
    }

    @GetMapping("/{idExternal}")
    public ResponseEntity<GroupProgressResponseDto> getProgressByIdExternal(
            @PathVariable UUID idExternal) {

        return ResponseEntity.ok(
                groupProgressService.getProgressByIdExternal(idExternal)
        );
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupProgressResponseDto>> getProgressByGroupId(
            @PathVariable Long groupId) {

        return ResponseEntity.ok(
                groupProgressService.getProgressByGroupId(groupId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GroupProgressResponseDto>> getProgressByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                groupProgressService.getProgressByUserId(userId)
        );
    }

    @DeleteMapping("/{idExternal}")
    public ResponseEntity<Void> deleteProgress(@PathVariable UUID idExternal) {
        groupProgressService.deleteProgress(idExternal);
        return ResponseEntity.noContent().build();
    }
}
