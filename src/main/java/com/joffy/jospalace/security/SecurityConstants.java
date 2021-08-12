package com.joffy.jospalace.security;

import com.joffy.jospalace.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 14400000;
    public static final String TOKEN_PREFIX = "BearerToken ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";

    public static String getTokenSecret(){
        AppProperties appProperties =(AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
