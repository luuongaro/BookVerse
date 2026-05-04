package com.grupo3.BookVerse.features.users;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "group_progress")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GroupProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @Column(name = "id_external", unique = true, nullable = false)
    private UUID idExternal = UUID.randomUUID();

    @Column(name = "group_id", nullable = false)
    private Long group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; //FK

    @Column(name = "current_progress", nullable = false)
    private int currentProgress;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

}
