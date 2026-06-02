package com.grupo3.BookVerse.features.groups.GroupGoals.controller;

import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsResponseDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.service.IGroupGoalsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/group-goals")
public class GroupGoalsController {

    private final IGroupGoalsService groupGoalsService;

    @GetMapping
    public ResponseEntity<List<GroupGoalsResponseDto>> findAll() {
        return ResponseEntity.ok(groupGoalsService.findAll());
    }

    @GetMapping("/{groupGoalsId}")
    public ResponseEntity<GroupGoalsResponseDto> findById(@PathVariable Long groupGoalsId) {
        return ResponseEntity.ok(groupGoalsService.findById(groupGoalsId));
    }

    @PostMapping
    public ResponseEntity<GroupGoalsResponseDto> create(
            @Valid @RequestBody GroupGoalsRequestDto groupGoalsRequestDto
    ) {
        return new ResponseEntity<>(
                groupGoalsService.save(groupGoalsRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{groupGoalsId}")
    public ResponseEntity<GroupGoalsResponseDto> update(
            @PathVariable Long groupGoalsId,
            @Valid @RequestBody GroupGoalsRequestDto groupGoalsRequestDto
    ) {
        return ResponseEntity.ok(
                groupGoalsService.update(groupGoalsId, groupGoalsRequestDto)
        );
    }

    @DeleteMapping("/{groupGoalsId}")
    public ResponseEntity<Void> delete(@PathVariable Long groupGoalsId) {
        groupGoalsService.delete(groupGoalsId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupGoalsResponseDto>> findByGroupId(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupGoalsService.findByGroupId(groupId));
    }
}