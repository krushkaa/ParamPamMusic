package org.example.parampammusic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс для запуска приложения ParamPamMusic.
 * Содержит метод main, который инициализирует и запускает приложение.
 */
@SpringBootApplication
public class ParamPamMusicApplication {

    private static final Logger logger = LogManager.getLogger(ParamPamMusicApplication.class);

    /**
     * Точка входа в приложение Spring Boot.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(ParamPamMusicApplication.class, args);
        logger.info("Приложение успешно запущено");
    }
}