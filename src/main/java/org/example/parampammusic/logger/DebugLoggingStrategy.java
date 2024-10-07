package org.example.parampammusic.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Реализация стратегии логирования, использующая уровень DEBUG.
 * Логи, записанные с использованием этой стратегии, предназначены для детализированного отладки.
 */
public class DebugLoggingStrategy implements LoggingStrategy {

    /**
     * Логгер, используемый для записи сообщений на уровне DEBUG.
     */
    private static final Logger logger = LogManager.getLogger(DebugLoggingStrategy.class);

    /**
     * Логирует сообщение на уровне DEBUG.
     *
     * @param message сообщение для логирования.
     */
    @Override
    public void log(String message) {
        logger.debug(message);
    }
}
