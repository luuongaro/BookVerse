package com.grupo3.BookVerse.features.groups.readingGroups.repository;

import com.grupo3.BookVerse.features.groups.readingGroups.domain.ReadingGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingGroupRepository extends JpaRepository<ReadingGroupEntity, Long> {
}
