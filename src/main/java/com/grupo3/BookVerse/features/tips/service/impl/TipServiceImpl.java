package com.grupo3.BookVerse.features.tips.service.impl;

import com.grupo3.BookVerse.common.exception.BadRequestException;
import com.grupo3.BookVerse.common.exception.ResourceNotFoundException;
import com.grupo3.BookVerse.features.tips.domain.TipEntity;
import com.grupo3.BookVerse.features.tips.dto.TipRequestDto;
import com.grupo3.BookVerse.features.tips.dto.TipResponseDto;
import com.grupo3.BookVerse.features.tips.mappers.TipMapper;
import com.grupo3.BookVerse.features.tips.repository.TipRepository;
import com.grupo3.BookVerse.features.tips.service.TipService;
import com.grupo3.BookVerse.features.users.domain.UserEntity;
import com.grupo3.BookVerse.features.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TipServiceImpl implements TipService {

    private final TipRepository tipRepository;
    private final TipMapper tipMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TipResponseDto createTip(TipRequestDto tipRequestDto) {

        if (tipRequestDto.getSenderUserId().equals(tipRequestDto.getReceiverUserId())) {
            throw new BadRequestException("Sender and receiver cannot be the same user");
        }

        UserEntity sender = findUserById(tipRequestDto.getSenderUserId());
        UserEntity receiver = findUserById(tipRequestDto.getReceiverUserId());

        TipEntity tipEntity = tipMapper.toEntity(tipRequestDto);
        tipEntity.setSender(sender);
        tipEntity.setReceiver(receiver);

        TipEntity savedTip = tipRepository.save(tipEntity);
        return tipMapper.toResponseDto(savedTip);
    }

    @Override
    public List<TipResponseDto> getAllTips() {
        List<TipEntity> tips = tipRepository.findAllByOrderByCreatedAtDesc();
        return tipMapper.toResponseDtoList(tips);
    }

    @Override
    public TipResponseDto getTipByIdExternal(UUID idExternal) {
        TipEntity tipEntity = findTipByIdExternal(idExternal);
        return tipMapper.toResponseDto(tipEntity);
    }

    @Override
    public List<TipResponseDto> getTipsBySenderId(Long senderId) {
        findUserById(senderId);
        List<TipEntity> tips = tipRepository.findBySenderIdOrderByCreatedAtDesc(senderId);
        return tipMapper.toResponseDtoList(tips);
    }

    @Override
    public List<TipResponseDto> getTipsByReceiverId(Long receiverId) {
        findUserById(receiverId);
        List<TipEntity> tips = tipRepository.findByReceiverIdOrderByCreatedAtDesc(receiverId);
        return tipMapper.toResponseDtoList(tips);
    }

    @Override
    @Transactional
    public void deleteTip(UUID idExternal) {
        TipEntity tipEntity = findTipByIdExternal(idExternal);
        tipRepository.delete(tipEntity);
    }

    private TipEntity findTipByIdExternal(UUID idExternal) {
        return tipRepository.findByIdExternal(idExternal)
                .orElseThrow(() -> new ResourceNotFoundException("Tip not found with idExternal: " + idExternal));
    }

    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
}
