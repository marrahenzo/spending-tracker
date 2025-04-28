package io.github.marrahenzo.spending_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsGlobalConfig {

    @Bean
    public CorsFilter corsFilter() {
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // TODO: replace with proper list of origins, or allow all
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Angular dev server
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        config.setExposedHeaders(List.of("Set-Cookie"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
