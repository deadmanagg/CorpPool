package com.example.corppool.server;

import com.example.corppool.config.AppConfigurations;

/**
 * Created by deepansh on 10/26/2016.
 */
public final class ExternalURLs {

    private static final String extsnUrlRest = "corppool/rest/";

    //urls related to feeds
    public static final String URL_TO_POST_FEEDS = AppConfigurations.defaultBaseUrl+extsnUrlRest+"feeds";
    public static final String URL_TO_GET_FEEDS = AppConfigurations.defaultBaseUrl+extsnUrlRest+"feeds/";

    //urls for user managment
    public static final String URL_TO_LOGIN = AppConfigurations.defaultBaseUrl+extsnUrlRest+"user/login";
    public static final String URL_TO_REGISTER = AppConfigurations.defaultBaseUrl+extsnUrlRest+"user/register";

}
