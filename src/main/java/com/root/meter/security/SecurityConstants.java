package com.root.meter.security;
/**
 * adapted from the supporting material
 * can be found at https://classroom.udacity.com/nanodegrees/nd035/parts/ef7ed085-afaf-4e13-924d-94c9746ed954/modules/c48ee8c7-bbce-4e82-8607-650c5acac401/lessons/6937a8f4-aeb7-4aec-ac7b-37840ecf1765/concepts/f4a66010-25d7-41a2-9266-6b96e5da2646
 * date:16/9/2021
 */
public class SecurityConstants {

	public static final String SECRET = "oursecretkey";
    public static final long EXPIRATION_TIME = 864_000_000; // JWT expiration time 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/create";
    public static final String POST_READINGS_FROM_METER = "/reading/post";
    public static final String POST_EVENT = "/event/post";
    public static final String LOGIN_URL = "/login";

}
