package com.meetup.university.twitter;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

@Component
public class TwitterFactory {


    @Bean
    public Twitter twitter(final TwitterProperties properties) {
        final AccessToken accessToken = new AccessToken(properties.getToken(), properties.getTokenSecret());
        final Twitter twitter = new twitter4j.TwitterFactory().getInstance();
        twitter.setOAuthConsumer(properties.getApiKey(), properties.getApiKeySecret());
        twitter.setOAuthAccessToken(accessToken);
        return twitter;
    }


}
