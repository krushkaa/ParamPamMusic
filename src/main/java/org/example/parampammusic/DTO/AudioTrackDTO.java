package org.example.parampammusic.DTO;

import lombok.Getter;
import lombok.Setter;
/**
 * Data Transfer Object (DTO) для аудиотрека.
 * Используется для передачи информации о треке между слоями приложения.
 */
@Getter
@Setter
public class AudioTrackDTO {

    /**
     * Уникальный идентификатор аудиотрека.
     */
    private Integer id;

    /**
     * Название аудиотрека.
     */
    private String title;

    /**
     * Название жанра, к которому относится аудиотрек.
     */
    private String genreName;

    /**
     * Имя исполнителя трека.
     */
    private String name;

    /**
     * Название альбома, в котором содержится аудиотрек.
     */
    private String albumTitle;

    /**
     * Цена аудиотрека.
     */
    private double price;
}
