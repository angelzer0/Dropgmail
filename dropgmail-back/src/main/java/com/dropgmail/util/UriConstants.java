package com.dropgmail.util;

public class UriConstants {
    public static final String HOST = "http://localhost";
    public static final String PORT = "8080";

    public static final String CONTEXT_PATH = "/dropgmail/api";
    public static final String VERSION = "/v1";

    public static final String BASE_URL = CONTEXT_PATH + VERSION;

    public static final String USERS = BASE_URL + "/users";
    public static final String USER_BY_ID = "/{id}";
    public static final String USERS_SEARCH = "/search";
    public static final String USERS_LOGIN = "/login";
    public static final String USERS_AUTH = "/authenticate";
    public static final String USERS_INFO = "/information";


}
