package org.example.parampammusic.util;

/**
 * Исключение, используемое для обработки случая, когда аудиотрек не найден.
 */
public class TrackNotFoundException extends Exception {

    /**
     * Конструктор исключения для аудиотрека с заданным идентификатором.
     *
     * @param audioTrackId идентификатор аудиотрека, который не был найден.
     */
    public TrackNotFoundException(int audioTrackId) {
        super("Аудиотрек с идентификатором " + audioTrackId + " не найден.");
    }
}
