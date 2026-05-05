package com.grupo3.BookVerse.features.groups;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "reading_group")
public class ReadingGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //PK

    @Column(name = "id_external", unique = true, nullable = false)
    private UUID idExternal = UUID.randomUUID();



}
