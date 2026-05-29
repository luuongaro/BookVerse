package com.grupo3.BookVerse.features.groups.GroupGoals;

import com.grupo3.BookVerse.common.exceptions.EntityNotFoundException;

import com.grupo3.BookVerse.common.model.IMapper;
import com.grupo3.BookVerse.features.groups.GroupGoals.domain.GroupGoalsEntity;

import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsRequestDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.dto.GroupGoalsResponseDto;
import com.grupo3.BookVerse.features.groups.GroupGoals.repository.GroupGoalsRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupGoalsService implements IGroupGoalsService {

    private final GroupGoalsRepository groupGoalsRepository;

    private final IMapper<GroupGoalsEntity, GroupGoalsRequestDto> newGroupGoalsMapper;
    private final IMapper<GroupGoalsEntity, GroupGoalsResponseDto> groupGoalsMapper;

    @Override
    public GroupGoalsResponseDto save(GroupGoalsRequestDto groupGoalsRequestDto) {

        GroupGoalsEntity saved = groupGoalsRepository.save(
                newGroupGoalsMapper.toEntity(groupGoalsRequestDto)
        );

        return groupGoalsMapper.toDTO(saved);
    }

    @Override
    public void delete(Long groupGoalsId) {

        GroupGoalsEntity groupGoals = groupGoalsRepository.findById(groupGoalsId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "GroupGoals",
                        "Group goal was not found",
                        "groupGoalsId",
                        groupGoalsId.toString()
                ));

        groupGoalsRepository.delete(groupGoals);
    }

    @Override
    public GroupGoalsResponseDto update(Long groupGoalsId,
                                        GroupGoalsRequestDto groupGoalsRequestDto) {

        GroupGoalsEntity groupGoals = groupGoalsRepository.findById(groupGoalsId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "GroupGoals",
                        "Group goal was not found",
                        "groupGoalsId",
                        groupGoalsId.toString()
                ));

        groupGoals.setGroupId(groupGoalsRequestDto.groupId());
        groupGoals.setTargetProgress(groupGoalsRequestDto.targetProgress());
        groupGoals.setTargetDate(groupGoalsRequestDto.targetDate());
        groupGoals.setAverageProgress(groupGoalsRequestDto.averageProgress());
        groupGoals.setAchieved(groupGoalsRequestDto.achieved());

        GroupGoalsEntity saved = groupGoalsRepository.save(groupGoals);

        return groupGoalsMapper.toDTO(saved);
    }

    @Override
    public GroupGoalsResponseDto findById(Long groupGoalsId) {

        return groupGoalsRepository.findById(groupGoalsId)
                .map(groupGoalsMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "GroupGoals",
                        "Group goal was not found",
                        "groupGoalsId",
                        groupGoalsId.toString()
                ));
    }

    @Override
    public List<GroupGoalsResponseDto> findAll() {

        return groupGoalsRepository.findAll()
                .stream()
                .map(groupGoalsMapper::toDTO)
                .toList();
    }
}