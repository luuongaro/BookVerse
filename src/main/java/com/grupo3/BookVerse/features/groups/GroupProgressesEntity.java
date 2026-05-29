package com.grupo3.BookVerse.features.groups;


import com.grupo3.BookVerse.features.users.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "group_progresses")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GroupProgressesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @Column(name = "id_external", unique = true, nullable = false)
    private UUID idExternal = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private ReadingGroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; //FK

    @Column(name = "current_progress", nullable = false)
    private int currentProgress;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
