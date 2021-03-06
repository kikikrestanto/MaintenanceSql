package com.example.maintenancesql.Activities;

public class Constant {

    public static final String URL = "http://192.168.100.8/";
    public static final String HOME = URL+"api";
    public static final String LOGIN = HOME+"/login";
    public static final String LOGOUT = HOME+"/logout";
    public static final String SAVE_USER_INFO = HOME+"/save_user_info";

    public static final String POSTS = HOME+"/posts";
    public static final String ADD_POST = POSTS+"/create";
    public static final String DELETE_POST = POSTS+"/delete";

    public static final String UPDATES = POSTS+"/updates";
    public static final String ADD_UPDATE = HOME+"/updates/create";

    public static final String MY_POST = POSTS+"/my_posts";
}
