package com.grupo3.BookVerse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BookVerseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookVerseApplication.class, args);
	}

}
