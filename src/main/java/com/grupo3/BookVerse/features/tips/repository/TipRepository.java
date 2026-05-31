package com.grupo3.BookVerse.features.tips.repository;

import com.grupo3.BookVerse.features.tips.domain.TipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipRepository extends JpaRepository<TipEntity, Long> {

    Optional<TipEntity> findByIdExternal(UUID idExternal);

    List<TipEntity> findAllByOrderByCreatedAtDesc();

    List<TipEntity> findBySenderIdOrderByCreatedAtDesc(Long senderId);

    List<TipEntity> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);
}
