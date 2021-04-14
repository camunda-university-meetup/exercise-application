package com.meetup.university.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final MailProperties mailProperties;

    public void sendMail(final String recipient, final String subject, final String text) {

        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.mailProperties.getUsername());
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);

    }
}

