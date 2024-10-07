package org.example.parampammusic.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Представляет сущность исполнителя.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "artist")
public class Artist {
    /**
     * Уникальный идентификатор исполнителя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Имя исполнителя.
     * Обязательное поле.
     */
    @Column (name = "artist_name", nullable = false)
    private String name;
}
