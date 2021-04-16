package com.meetup.university.mail;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

@Slf4j
@Component("mailDelegate")
@RequiredArgsConstructor
public class MailDelegate implements JavaDelegate {

    private final MailService mailService;

    private final VariableFactory<String> recipient = stringVariable("recipient");
    private final VariableFactory<String> content = stringVariable("content");
    private final VariableFactory<String> subject = stringVariable("subject");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //input
        final String recipient = this.recipient.from(execution).getLocal();
        final String content = this.content.from(execution).getLocal();
        final String subject = this.subject.from(execution).getLocal();

        //processing
        this.mailService.sendMail(recipient, subject, content);
    }
}
