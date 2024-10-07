package org.example.parampammusic.logger;

/**
 * Интерфейс стратегии логирования.
 * Определяет метод для логирования сообщений, который должен быть реализован конкретными стратегиями.
 */
public interface LoggingStrategy {

    /**
     * Логирует сообщение.
     *
     * @param message сообщение для логирования.
     */
    void log(String message);
}
