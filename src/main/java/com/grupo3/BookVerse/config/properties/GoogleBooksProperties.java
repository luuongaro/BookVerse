package com.grupo3.BookVerse.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.books.api")
@Getter
@Setter
public class GoogleBooksProperties {

    private String url;
    private String key;

    // Loads Google Books API configuration values from application.yml.

}
