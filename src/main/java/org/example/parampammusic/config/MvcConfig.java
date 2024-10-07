package org.example.parampammusic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Конфигурационный класс для настройки MVC в приложении.
 * Позволяет задавать параметры локализации и обработки переключения языка.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Определяет LocaleResolver для хранения информации о текущей локали в сессии.
     * Устанавливает локаль по умолчанию на русский ("ru").
     *
     * @return объект SessionLocaleResolver с настройками локали по умолчанию.
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(new Locale("ru"));
        return sessionLocaleResolver;
    }

    /**
     * Интерсептор для изменения локали на основе параметра запроса "lang".
     * Позволяет менять язык интерфейса путем добавления параметра lang в URL (например, ?lang=en).
     *
     * @return объект LocaleChangeInterceptor.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Добавляет интерсептор для изменения локали в реестр интерсепторов.
     * Интерсептор будет отслеживать изменения локали на основе параметра "lang" в запросе.
     *
     * @param registry реестр интерсепторов.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
