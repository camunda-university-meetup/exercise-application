package com.meetup.university.twitter;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;


@Component("tweetDelegate")
@RequiredArgsConstructor
public class TweetDelegate implements JavaDelegate {

    private final TwitterService twitterService;
    private final VariableFactory<String> twitterContent = stringVariable("twitterContent");

    public void execute(final DelegateExecution execution) throws Exception {

        //input
        final String content = this.twitterContent.from(execution).getLocal();

        //processing
        this.twitterService.tweet(content);
    }
}
