package com.grupo3.BookVerse.features.groups.GroupGoals.controller;

import com.grupo3.BookVerse.features.groups.GroupGoals.service.IGroupGoalsService;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsResponseDto;
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
    ResponseEntity<List<GroupGoalsResponseDto>> findAll() {
        return ResponseEntity.ok(groupGoalsService.findAll());
    }

    @GetMapping("/{groupGoalsId}")
    ResponseEntity<GroupGoalsResponseDto> findById(@PathVariable Long groupGoalsId) {
        return ResponseEntity.ok(groupGoalsService.findById(groupGoalsId));
    }

    @PostMapping
    ResponseEntity<GroupGoalsResponseDto> create(
            @RequestBody GroupGoalsRequestDto groupGoalsRequestDto
    ) {
        return new ResponseEntity<>(
                groupGoalsService.save(groupGoalsRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{groupGoalsId}")
    ResponseEntity<GroupGoalsResponseDto> update(
            @PathVariable Long groupGoalsId,
            @RequestBody GroupGoalsRequestDto groupGoalsRequestDto
    ) {
        return ResponseEntity.ok(
                groupGoalsService.update(groupGoalsId, groupGoalsRequestDto)
        );
    }

    @DeleteMapping("/{groupGoalsId}")
    ResponseEntity<Void> delete(@PathVariable Long groupGoalsId) {
        groupGoalsService.delete(groupGoalsId);
        return ResponseEntity.noContent().build();
    }
}