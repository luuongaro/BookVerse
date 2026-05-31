package com.grupo3.BookVerse.features.tips.controller;
import com.grupo3.BookVerse.features.tips.dto.TipRequestDto;
import com.grupo3.BookVerse.features.tips.dto.TipResponseDto;
import com.grupo3.BookVerse.features.tips.service.TipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tips")
@RequiredArgsConstructor
public class TipController {

    private final TipService tipService;

    @PostMapping
    public ResponseEntity<TipResponseDto> createTip(@Valid @RequestBody TipRequestDto tipRequestDto) {
        TipResponseDto createdTip = tipService.createTip(tipRequestDto);
        return new ResponseEntity<>(createdTip, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TipResponseDto>> getAllTips() {
        List<TipResponseDto> tips = tipService.getAllTips();
        return ResponseEntity.ok(tips);
    }

    @GetMapping("/{idExternal}")
    public ResponseEntity<TipResponseDto> getTipByIdExternal(@PathVariable UUID idExternal) {
        TipResponseDto tip = tipService.getTipByIdExternal(idExternal);
        return ResponseEntity.ok(tip);
    }

    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<TipResponseDto>> getTipsBySenderId(@PathVariable Long senderId) {
        List<TipResponseDto> tips = tipService.getTipsBySenderId(senderId);
        return ResponseEntity.ok(tips);
    }

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<TipResponseDto>> getTipsByReceiverId(@PathVariable Long receiverId) {
        List<TipResponseDto> tips = tipService.getTipsByReceiverId(receiverId);
        return ResponseEntity.ok(tips);
    }

    @DeleteMapping("/{idExternal}")
    public ResponseEntity<Void> deleteTip(@PathVariable UUID idExternal) {
        tipService.deleteTip(idExternal);
        return ResponseEntity.noContent().build();
    }
}

