package com.meetup.university.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class MailSenderFactory {

    private final MailProperties mailProperties;

    @Bean
    public JavaMailSender getJavaMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.mailProperties.getHost());
        mailSender.setPort(587);

        mailSender.setUsername(this.mailProperties.getUsername());
        mailSender.setPassword(this.mailProperties.getPassword());

        final Properties props = mailSender.getJavaMailProperties();
        props.putAll(this.mailProperties.getProperties());

        return mailSender;
    }


}
