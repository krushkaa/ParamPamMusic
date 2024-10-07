package org.example.parampammusic.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Реализация стратегии логирования, использующая уровень INFO.
 * Логи, записанные с использованием этой стратегии, предназначены для общего информирования.
 */
public class InfoLoggingStrategy implements LoggingStrategy {

    /**
     * Логгер, используемый для записи сообщений на уровне INFO.
     */
    private static final Logger logger = LogManager.getLogger(InfoLoggingStrategy.class);

    /**
     * Логирует сообщение на уровне INFO.
     *
     * @param message сообщение для логирования.
     */
    @Override
    public void log(String message) {
        logger.info(message);
    }
}


