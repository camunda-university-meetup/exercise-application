package com.meetup.university.twitter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
@RequiredArgsConstructor
public class TwitterService {

    private final Twitter twitter;

    public void tweet(final String content) throws TwitterException {
        this.twitter.updateStatus(content);
    }

}
