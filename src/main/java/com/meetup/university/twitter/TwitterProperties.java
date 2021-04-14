package com.meetup.university.twitter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "twitter")
public class TwitterProperties {

    public String token;

    public String tokenSecret;

    public String apiKey;
    
    public String apiKeySecret;
}
