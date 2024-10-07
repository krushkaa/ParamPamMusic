package org.example.parampammusic.logger;

import lombok.Setter;

/**
 * Контекст для выбора и использования стратегии логирования.
 * Позволяет динамически изменять стратегию логирования в зависимости от требований.
 */
@Setter
public class LoggerContext {

    /**
     * Текущая стратегия логирования, используемая контекстом.
     */
    private LoggingStrategy strategy;

    /**
     * Создает новый контекст логирования с указанной стратегией.
     *
     * @param strategy начальная стратегия логирования.
     */
    public LoggerContext(LoggingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Логирует сообщение с использованием текущей стратегии логирования.
     *
     * @param message сообщение для логирования.
     */
    public void log(String message) {
        strategy.log(message);
    }
}

