package com.smahama.api.game.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Configuration class
 * @author Salomon Mahama
 *
 */
@Configuration
public class GameApiConfig {

    public GameApiConfig() {

        super();
    }

    /**
     * Bean created to log (in debug mode) all requests sent to the application
     * @return CommonsRequestLoggingFilter configured
     */
    @Bean
    public CommonsRequestLoggingFilter logFilter() {

        final CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        return filter;
    }
}
