package com.api.schedlr.schedlr_api_integration.Constants;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class APIConstants {

    @Value("${app.service-url}")
    private static String serviceUrl;
    public static final String CLIENT_ID = "862ar2q201lf2i";
    public static final String CLIENT_SECRET ="WPL_AP1.xXWyOLBXLlP9NFfF.Fi6kBg==";
    public static final String LINKEDIN_REDIRECT_URL = serviceUrl+ "/myprofile";
    public static final String LINKEDIN_GET_ACCESS_TOKEN ="https://www.linkedin.com/oauth/v2/accessToken";
    public static final String LINKEDIN_GET_PERSON_ID ="https://api.linkedin.com/v2/userinfo";
    public static final String LINKEDIN_REGISTER_UPLOAD_IMAGE = "https://api.linkedin.com/v2/assets?action=registerUpload";
    public static final String LINKEDIN_POST_IMAGE ="https://api.linkedin.com/v2/ugcPosts";
    public static final String appId = "1232937608014970";
    //public static final String ClIENT_ACCESS_TOKEN = "AQWhAX3yK7PDAy-rbKpW0hKthfLaqE_WpxGy2PqoR6cRx3IqE-hxWq8aVTx45JrgHApZbYVYFdEFOimpPmfd4CSNvzWyhoLaps_Q3wSWZ371rFdj4CEZLvlhIWT2UfeGDFjqCJB4Zcxyp8kAsVV_SVQ45xi4Ek58l4nD1JkMok_rYDF83dX0PMseX71iUWWCtieN4BsPtBoVeJI3X6FnQYwJjv456JABTSb2DAoqyPM9WRUizUU_ayl5z_-mM7OzsfPLN1qhrDN97OWNCszLTKDzuaSvajbeynzvgKJR_MAS78xCibvG0_baz2dWeb6ovzKhhV9sZjipeSbXtWMHtwd9GYIEvw";
    //-------------------------------------  PInterest  ----------------------------------------------

    public static final String PINTEREST_OAUTH_URL = "https://api.pinterest.com/v5/oauth/token";
    public static final String PINTEREST_CLIENT_ID = "1506052";
    public static final String PINTEREST_CLIENT_SECRET = "1486a9ea2593e42736ad3a819dd0ab8d9c25aed7";
    public static final String PINTEREST_REDIRECT_URL = serviceUrl + "/pinterestStatus";

    public static final String PINTEREST_SANDBOX_POSTURL = "https://api-sandbox.pinterest.com/v5/pins";
}