package com.meetup.university;

import com.meetup.university.twitter.TwitterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({TwitterProperties.class, MailProperties.class})
@SpringBootApplication
public class Application {

    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }
}
